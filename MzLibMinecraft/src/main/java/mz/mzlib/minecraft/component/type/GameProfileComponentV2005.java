package mz.mzlib.minecraft.component.type;

import jdk.nashorn.internal.objects.annotations.Constructor;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.authlib.GameProfile;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=2005)
@WrapMinecraftClass(@VersionName(name="net.minecraft.component.type.ProfileComponent"))
public interface GameProfileComponentV2005 extends WrapperObject
{
    @WrapperCreator
    static GameProfileComponentV2005 create(Object wrapped)
    {
        return WrapperObject.create(GameProfileComponentV2005.class, wrapped);
    }
    
    static GameProfileComponentV2005 newInstance(GameProfile gameProfile)
    {
        return create(null).staticNewInstance(gameProfile);
    }
    @Constructor
    GameProfileComponentV2005 staticNewInstance(GameProfile gameProfile);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="comp_2413"))
    GameProfile getGameProfile();
}
