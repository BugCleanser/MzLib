package mz.mzlib.minecraft.item;

import mz.mzlib.data.DataHandler;
import mz.mzlib.data.DataKey;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.authlib.GameProfile;
import mz.mzlib.minecraft.component.ComponentKeyV2005;
import mz.mzlib.minecraft.component.type.GameProfileComponentV2005;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.nbt.NbtUtil;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.Option;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.function.Predicate;

@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.item.SkullItem", end = 2002),
    @VersionName(name = "net.minecraft.item.PlayerHeadItem", begin = 2002)
})
public interface ItemPlayerHead extends Item
{
    WrapperFactory<ItemPlayerHead> FACTORY = WrapperFactory.of(ItemPlayerHead.class);
    @Deprecated
    @WrapperCreator
    static ItemPlayerHead create(Object wrapped)
    {
        return WrapperObject.create(ItemPlayerHead.class, wrapped);
    }

    Item PLAYER_HEAD_V1300 = MinecraftPlatform.instance.getVersion() < 1300 ? null : Item.fromId("player_head");

    DataKey<ItemStack, Option<GameProfile.Description>> OWNER = new DataKey<>("owner");

    ComponentKeyV2005.Wrapper<GameProfileComponentV2005> COMPONENT_KEY_PROFILE_V2005 =
        MinecraftPlatform.instance.getVersion() < 2005 ?
            null :
            ComponentKeyV2005.fromId("profile", GameProfileComponentV2005.FACTORY);


    class Module extends MzModule
    {
        public static Module instance = new Module();

        @Override
        public void onLoad()
        {
            Predicate<ItemStack> checker;
            if(MinecraftPlatform.instance.getVersion() < 1300)
            {
                Item skull = Item.fromId("skull");
                checker = is -> is.getItem().equals(skull) && is.getDamageV_1300() == 3;
            }
            else
                checker = is -> is.getItem().equals(PLAYER_HEAD_V1300);
            if(MinecraftPlatform.instance.getVersion() < 2005)
                DataHandler.factory(OWNER)
                    .checker(checker)
                    .getter(is ->
                    {
                        for(NbtCompound tag : is.getTagV_2005())
                        {
                            for(NbtCompound skullOwner : tag.getNbtCompound("SkullOwner"))
                            {
                                return Option.some(NbtUtil.decodeGameProfileV_2005(skullOwner).toDescription());
                            }
                        }
                        return Option.none();
                    })
                    .setter((is, value) ->
                    {
                        for(NbtCompound tag : CUSTOM_DATA.revise(is))
                        {
                            if(value.isSome())
                                tag.put("SkullOwner", NbtUtil.encodeGameProfileV_2005(GameProfile.fromDescription(value.unwrap())));
                            else
                                tag.remove("SkullOwner");
                        }
                    })
                    .register(this);
            else
                DataHandler.factory(OWNER)
                    .checker(checker)
                    .getter(is -> is.getComponentsV2005().get(COMPONENT_KEY_PROFILE_V2005)
                            .map(GameProfileComponentV2005::toDescription))
                    .setter((is, value) -> is.getComponentsV2005()
                        .set(COMPONENT_KEY_PROFILE_V2005, value.map(GameProfileComponentV2005::newInstance)))
                    .register(this);
        }
    }

    /**
     * @see #OWNER
     */
    @Deprecated
    static Option<GameProfile.Description> getOwner(ItemStack itemStack)
    {
        return OWNER.get(itemStack);
    }
    /**
     * @see #OWNER
     */
    @Deprecated
    static void setOwner(ItemStack itemStack, Option<GameProfile.Description> value)
    {
        OWNER.set(itemStack, value);
    }

    @Deprecated
    static String texturesFromUrl(String url)
    {
        return GameProfile.Description.urlToTextures(url);
    }
}
