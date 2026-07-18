package mz.mzlib.minecraft.authlib;

import com.google.common.collect.LinkedHashMultimap;
import com.google.gson.JsonObject;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.authlib.properties.Property;
import mz.mzlib.minecraft.authlib.properties.PropertyMap;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.*;
import mz.mzlib.util.wrapper.Impl;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

@WrapMinecraftClass(@VersionName(name = "com.mojang.authlib.GameProfile"))
public interface GameProfile extends WrapperObject
{
    WrapperFactory<GameProfile> FACTORY = WrapperFactory.of(GameProfile.class);

    /**
     * id and name cannot be null at the same time.
     */
    static GameProfile of(@Nullable UUID id, @Nullable String name)
    {
        return FACTORY.getStatic().static$of(id, name);
    }

    @ApiStatus.Experimental
    static GameProfile ofV2109(UUID id, String name, PropertyMap properties)
    {
        return FACTORY.getStatic().static$ofV2109(id, name, properties);
    }

    @Nullable UUID getId();
    @Nullable String getName();

    @WrapMinecraftFieldAccessor(@VersionName(name = "properties"))
    PropertyMap getProperties();
    @WrapMinecraftFieldAccessor(@VersionName(name = "properties"))
    void setProperties(PropertyMap value);

    static GameProfile fromDescription(Description description)
    {
        GameProfile result = of(description.getId(), description.getName());
        PropertyMap p = description.getProperties();
        if(p != null)
            result.setProperties(p);
        return result;
    }

    GameProfile.Description toDescription();

    /**
     * 指定 properties，或由 id 或 name 得到
     * id 比 name 优先
     * 在低版本，至少要有 name 或 id
     */
    @ApiStatus.Experimental
    class Description
    {
        @Nullable String name;
        @Nullable UUID id;
        @Nullable PropertyMap properties;

        public Description(@Nullable String name, @Nullable UUID id, @Nullable PropertyMap properties)
        {
            if(id == null && name == null && properties == null)
                throw new IllegalArgumentException();
            this.id = id;
            this.name = name;
            this.properties = properties;
        }
        public Description(PropertyMap properties)
        {
            this(null, null, properties);
        }
        public Description(UUID id)
        {
            this(null, id, null);
        }
        public Description(String name)
        {
            this(name, null, null);
        }

        public @Nullable String getName()
        {
            return this.name;
        }
        public @Nullable UUID getId()
        {
            return this.id;
        }
        public @Nullable PropertyMap getProperties()
        {
            return this.properties;
        }

        public static Description textures(@Nullable String name, @Nullable UUID uuid, String textures)
        {
            String keyTextures = "textures";
            LinkedHashMultimap<String, Property> properties = LinkedHashMultimap.create();
            properties.put(keyTextures, Property.of(keyTextures, textures));
            return new Description(name, uuid, PropertyMap.of(properties));
        }
        public static Description textures(String textures)
        {
            return textures(
                null, UUID.nameUUIDFromBytes(textures.getBytes(StandardCharsets.UTF_8)),
                textures
            );
        }

        public static Description texturesUrl(@Nullable String name, @Nullable UUID uuid, String texturesUrl)
        {
            return textures(name, uuid, urlToTextures(texturesUrl));
        }
        public static Description texturesUrl(String value)
        {
            return textures(urlToTextures(value));
        }

        public static String urlToTextures(String url)
        {
            JsonObject textures = new JsonObject();
            for(JsonObject value : JsonUtil.addChild(textures, "textures"))
            {
                for(JsonObject skin : JsonUtil.addChild(value, "SKIN"))
                {
                    skin.addProperty("url", url);
                }
            }
            return Base64.getEncoder().encodeToString(textures.toString().getBytes(StandardCharsets.UTF_8));
        }
    }


    @ApiStatus.Internal
    UUID NIL_UUID_V2002 = new UUID(0L, 0L);

    GameProfile static$of(@Nullable UUID id, @Nullable String name);
    @Impl("static$of")
    @VersionRange(end = 2002)
    default GameProfile static$of$implV_2002(@Nullable UUID id, @Nullable String name)
    {
        return this.static$of0(id, name);
    }
    @Impl("static$of")
    @VersionRange(begin = 2002)
    default GameProfile static$of$implV2002(@Nullable UUID id, @Nullable String name)
    {
        return this.static$of0(Option.fromNullable(id).unwrapOr(NIL_UUID_V2002), Option.fromNullable(name).unwrapOr(""));
    }
    @WrapConstructor
    GameProfile static$of0(@Nullable UUID id, @Nullable String name);

    @WrapConstructor
    @VersionRange(begin = 2109)
    GameProfile static$ofV2109(UUID id, String name, PropertyMap properties);

    @Impl("getId")
    @VersionRange(end = 2002)
    default @Nullable UUID getId$implV_2002()
    {
        return this.getId0();
    }
    @Impl("getId")
    @VersionRange(begin = 2002)
    default @Nullable UUID getId$implV2002()
    {
        UUID result = Objects.requireNonNull(this.getId0());
        return result.equals(NIL_UUID_V2002) ? null : result;
    }
    @WrapMinecraftFieldAccessor(@VersionName(name = "id"))
    @Nullable UUID getId0();

    @Impl("getName")
    @VersionRange(end = 2002)
    default @Nullable String getName$implV_2002()
    {
        return this.getName0();
    }
    @Impl("getName")
    @VersionRange(begin = 2002)
    default @Nullable String getName$implV2002()
    {
        String result = Objects.requireNonNull(this.getName0());
        return result.isEmpty() ? null : result;
    }
    @WrapMinecraftFieldAccessor(@VersionName(name = "name"))
    @Nullable String getName0();

    @Impl("toDescription")
    @VersionRange(end = 2002)
    @VersionRange(begin = 2005)
    default GameProfile.Description toDescription$implV_2002__2005()
    {
        return new Description(
            this.getName(), this.getId(),
            this.getProperties().getWrapped().isEmpty() ? null : this.getProperties()
        );
    }
    @Impl("toDescription")
    @VersionRange(begin = 2002, end = 2005)
    default GameProfile.Description toDescription$implV2002_2005()
    {
        return new Description(this.getName(), this.getId(), this.getProperties());
    }
}
