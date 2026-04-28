package mz.mzlib.minecraft.component.type;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.authlib.GameProfile;
import mz.mzlib.minecraft.authlib.properties.PropertyMap;
import mz.mzlib.minecraft.entity.player.SkinTexturesV2109;
import mz.mzlib.minecraft.util.EitherV1300;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.Option;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

@VersionRange(begin = 2005)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.component.type.ProfileComponent"))
public interface GameProfileComponentV2005 extends WrapperObject
{
    WrapperFactory<GameProfileComponentV2005> FACTORY = WrapperFactory.of(GameProfileComponentV2005.class);
    static GameProfileComponentV2005 of(GameProfile gameProfile)
    {
        return FACTORY.getStatic().static$of(gameProfile);
    }

    GameProfileComponentV2005 static$of(GameProfile gameProfile);
    @SpecificImpl("static$of")
    @VersionRange(end = 2109)
    default GameProfileComponentV2005 static$ofV_2109(GameProfile gameProfile)
    {
        return ofV_2109(gameProfile.getName(), gameProfile.getId(), gameProfile.getProperties());
    }
    static GameProfileComponentV2005 ofV_2109(@Nullable String name, @Nullable UUID id, PropertyMap properties)
    {
        return FACTORY.getStatic().static$of0V_2109(Optional.ofNullable(name), Optional.ofNullable(id), properties);
    }
    @WrapConstructor
    @VersionRange(end = 2109)
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    GameProfileComponentV2005 static$of0V_2109(
        Optional<String> name,
        Optional<UUID> id,
        PropertyMap properties);
    @SpecificImpl("static$of")
    @VersionRange(begin = 2109)
    @WrapMinecraftMethod(@VersionName(name = "ofStatic"))
    GameProfileComponentV2005 static$ofV2109(GameProfile gameProfile);

    static GameProfileComponentV2005 of(GameProfile.Description description)
    {
        return FACTORY.getStatic().static$of(description);
    }
    GameProfileComponentV2005 static$of(GameProfile.Description description);
    @SpecificImpl("static$of")
    @VersionRange(end = 2109)
    default GameProfileComponentV2005 static$ofV_2109(GameProfile.Description description)
    {
        return ofV_2109(
            description.getName(), description.getId(),
            Option.fromNullable(description.getProperties()).unwrapOrGet(PropertyMap::of)
        );
    }
    @SpecificImpl("static$of")
    @VersionRange(begin = 2109)
    default GameProfileComponentV2005 static$ofV2109(GameProfile.Description description)
    {
        return ofV2109(description, SkinTexturesV2109.SkinOverride.empty());
    }

    static GameProfileComponentV2005 ofV2109(
        GameProfile.Description description,
        SkinTexturesV2109.SkinOverride override)
    {
        if(description.getId() != null && description.getName() != null && description.getProperties() != null)
            return ofV2109(EitherV1300.first(GameProfile.fromDescription(description)), override);
        return ofV2109(EitherV1300.second(DataV2109.fromDescription(description)), override);
    }
    static GameProfileComponentV2005 ofV2109(
        EitherV1300<GameProfile, DataV2109> profileOrData,
        SkinTexturesV2109.SkinOverride override)
    {
        return FACTORY.getStatic().static$of0V2109(EitherV1300.fromWrapper(profileOrData), override);
    }
    @WrapMinecraftMethod(@VersionName(name = "ofDispatched"))
    @VersionRange(begin = 2109)
    GameProfileComponentV2005 static$of0V2109(
        EitherV1300<?, ?> profileOrData,
        SkinTexturesV2109.SkinOverride override);

    @WrapMinecraftFieldAccessor({
        @VersionName(name = "comp_2413", end = 2109),
        @VersionName(name = "profile", begin = 2109)
    })
    GameProfile getGameProfile();

    @VersionRange(begin = 2109)
    default EitherV1300<GameProfile, DataV2109> getV2109()
    {
        return this.get0V2109().toWrapper(GameProfile.FACTORY, DataV2109.FACTORY);
    }
    @WrapMinecraftMethod(@VersionName(name = "get"))
    @VersionRange(begin = 2109)
    EitherV1300<?, ?> get0V2109();

    default GameProfile.Description toDescription()
    {
        return this.getV2109().map(GameProfile::toDescription, DataV2109::toDescription);
    }

    @VersionRange(begin = 2109)
    @WrapMinecraftInnerClass(outer = GameProfileComponentV2005.class, name = @VersionName(name = "Data"))
    interface DataV2109 extends WrapperObject
    {
        WrapperFactory<DataV2109> FACTORY = WrapperFactory.of(DataV2109.class);

        static DataV2109 of(@Nullable String name, @Nullable UUID id, PropertyMap properties)
        {
            return FACTORY.getStatic().static$of0(Optional.ofNullable(name), Optional.ofNullable(id), properties);
        }
        @WrapConstructor
        @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
        DataV2109 static$of0(Optional<String> name, Optional<UUID> id, PropertyMap properties);

        @WrapMinecraftMethod(@VersionName(name = "comp_4625")) // Mojang: name
        Optional<String> getName0();
        @WrapMinecraftMethod(@VersionName(name = "comp_4626")) // Mojang: id
        Optional<UUID> getId0();
        @WrapMinecraftMethod(@VersionName(name = "comp_4627")) // Mojang: properties
        PropertyMap getProperties();

        default @Nullable String getName()
        {
            return this.getName0().orElse(null);
        }
        default @Nullable UUID getId()
        {
            return this.getId0().orElse(null);
        }

        static DataV2109 fromDescription(GameProfile.Description description)
        {
            return of(
                description.getName(), description.getId(),
                Option.fromNullable(description.getProperties()).unwrapOrGet(PropertyMap::of)
            );
        }

        default GameProfile.Description toDescription()
        {
            return new GameProfile.Description(
                this.getName(), this.getId(),
                this.getProperties().getWrapped().isEmpty() ? null : this.getProperties()
            );
        }
    }
}

