package mz.mzlib.minecraft.entity.player;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=2109)
@WrapMinecraftClass(@VersionName(name="net.minecraft.entity.player.SkinTextures"))
public interface SkinTexturesV2109 extends WrapperObject
{
    WrapperFactory<SkinTexturesV2109> FACTORY = WrapperFactory.of(SkinTexturesV2109.class);
    
    @WrapMinecraftInnerClass(outer=SkinTexturesV2109.class, name=@VersionName(name="SkinOverride"))
    interface SkinOverride extends WrapperObject
    {
        WrapperFactory<SkinOverride> FACTORY = WrapperFactory.of(SkinOverride.class);
        
        static SkinOverride empty()
        {
            return FACTORY.getStatic().staticEmpty();
        }
        @WrapMinecraftFieldAccessor(@VersionName(name="EMPTY"))
        SkinOverride staticEmpty();
    }
}
