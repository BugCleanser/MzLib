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
import mz.mzlib.util.wrapper.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@WrapMinecraftClass(@VersionName(name="com.mojang.authlib.GameProfile"))
public interface GameProfile extends WrapperObject
{
    WrapperFactory<GameProfile> FACTORY = WrapperFactory.of(GameProfile.class);
    @Deprecated
    @WrapperCreator
    static GameProfile create(Object wrapped)
    {
        return WrapperObject.create(GameProfile.class, wrapped);
    }
    
    UUID NIL_UUID_V2002 = new UUID(0L, 0L);
    
    /**
     * id and name cannot be null at the same time.
     */
    static GameProfile newInstance(Option<UUID> id, Option<String> name)
    {
        return FACTORY.getStatic().static$newInstance(id, name);
    }
    
    @WrapConstructor
    GameProfile static$newInstance0(UUID id, String name);
    
    GameProfile static$newInstance(Option<UUID> id, Option<String> name);
    
    @SpecificImpl("static$newInstance")
    @VersionRange(end=2002)
    default GameProfile static$newInstanceV_2002(Option<UUID> id, Option<String> name)
    {
        return this.static$newInstance0(id.toNullable(), name.toNullable());
    }
    
    @SpecificImpl("static$newInstance")
    @VersionRange(begin=2002)
    default GameProfile static$newInstanceV2002(Option<UUID> id, Option<String> name)
    {
        return this.static$newInstance0(id.unwrapOr(NIL_UUID_V2002), name.unwrapOr(""));
    }
    
    static GameProfile newInstanceV2109(UUID id, String name, PropertyMap properties)
    {
        return FACTORY.getStatic().static$newInstanceV2109(id, name, properties);
    }
    @WrapConstructor
    @VersionRange(begin=2109)
    GameProfile static$newInstanceV2109(UUID id, String name, PropertyMap properties);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="id"))
    UUID getId0();
    
    Option<UUID> getId();
    
    @SpecificImpl("getId")
    @VersionRange(end=2002)
    default Option<UUID> getIdV_2002()
    {
        return Option.fromNullable(getId0());
    }
    
    @SpecificImpl("getId")
    @VersionRange(begin=2002)
    default Option<UUID> getIdV2002()
    {
        return Option.some(getId0()).then(ThrowableFunction.switcher(NIL_UUID_V2002::equals, ThrowableSupplier.<UUID>nul().ignore()).thenApply(InvertibleFunction.option()));
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="name"))
    String getName0();
    Option<String> getName();
    @SpecificImpl("getName")
    @VersionRange(end=2002)
    default Option<String> getNameV_2002()
    {
        return Option.fromNullable(getName0());
    }
    @SpecificImpl("getName")
    @VersionRange(begin=2002)
    default Option<String> getNameV2002()
    {
        return Option.some(getName0()).then(ThrowableFunction.switcher(String::isEmpty, ThrowableSupplier.<String>nul().ignore()).thenApply(InvertibleFunction.option()));
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="properties"))
    PropertyMap getProperties();
    @WrapMinecraftFieldAccessor(@VersionName(name="properties"))
    void setProperties(PropertyMap value);
    
    static GameProfile fromDescription(Description description)
    {
        GameProfile result = newInstance(description.getId(), description.getName());
        for(PropertyMap p: description.getProperties())
        {
            result.setProperties(p);
        }
        return result;
    }
    
    GameProfile.Description toDescription();
    @SpecificImpl("toDescription")
    @VersionRange(end=2002)
    @VersionRange(begin=2005)
    default GameProfile.Description toDescriptionV_2002__2005()
    {
        return new Description(this.getName(), this.getId(), this.getProperties().getWrapped().isEmpty() ? Option.none() : Option.some(this.getProperties()));
    }
    @SpecificImpl("toDescription")
    @VersionRange(begin=2002, end=2005)
    default GameProfile.Description toDescriptionV2002_2005()
    {
        return new Description(this.getName().map(name->name.isEmpty()?null:name), this.getId().map(id->id.equals(NIL_UUID_V2002)?null:id), this.getProperties());
    }
    
    /**
     * 指定properties，或由id或name得到
     * id比name优先
     * 在低版本，至少要有name或id
     */
    class Description
    {
        Option<String> name;
        Option<UUID> id;
        Option<PropertyMap> properties;
        
        public Description(Option<String> name, Option<UUID> id, Option<PropertyMap> properties)
        {
            if(id.isNone() && name.isNone() && properties.isNone())
                throw new IllegalArgumentException();
            this.id = id;
            this.name = name;
            this.properties = properties;
        }
        public Description(Option<String> name, Option<UUID> id, PropertyMap properties)
        {
            this(name, id, Option.some(properties));
        }
        public Description(PropertyMap properties)
        {
            this(Option.none(), Option.none(), properties);
        }
        public Description(UUID id)
        {
            this(Option.none(), Option.some(id), Option.none());
        }
        public Description(String name)
        {
            this(Option.some(name), Option.none(), Option.none());
        }
        
        public Option<String> getName()
        {
            return this.name;
        }
        public Option<UUID> getId()
        {
            return this.id;
        }
        public Option<PropertyMap> getProperties()
        {
            return this.properties;
        }
        
        public static Description textures(Option<String> name, Option<UUID> uuid, String textures)
        {
            String keyTextures = "textures";
            LinkedHashMultimap<String, Property> properties = LinkedHashMultimap.create();
            properties.put(keyTextures, Property.newInstance(keyTextures, textures, Option.none()));
            return new Description(name, uuid, PropertyMap.newInstance(properties));
        }
        public static Description textures(String textures)
        {
            return textures(Option.none(), Option.some(UUID.nameUUIDFromBytes(textures.getBytes(StandardCharsets.UTF_8))), textures);
        }
        
        public static Description texturesUrl(Option<String> name, Option<UUID> uuid, String texturesUrl)
        {
            return textures(name, uuid, urlToTextures(texturesUrl));
        }
        public static Description texturesUrl(String texturesUrl)
        {
            return textures(urlToTextures(texturesUrl));
        }
        
        public static String urlToTextures(String url)
        {
            JsonObject textures = new JsonObject();
            for(JsonObject value: JsonUtil.addChild(textures, "textures"))
                for(JsonObject skin: JsonUtil.addChild(value, "SKIN"))
                    skin.addProperty("url", url);
            return Base64.getEncoder().encodeToString(textures.toString().getBytes(StandardCharsets.UTF_8));
        }
    }
}
