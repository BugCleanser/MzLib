package mz.mzlib.minecraft.incomprehensible.text;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=2109)
@WrapMinecraftClass(@VersionName(name="net.minecraft.text.StyleSpriteSource"))
public interface FontDescriptionV2109 extends WrapperObject
{
    WrapperFactory<FontDescriptionV2109> FACTORY = WrapperFactory.of(FontDescriptionV2109.class);
    
    @WrapMinecraftInnerClass(outer=FontDescriptionV2109.class, name=@VersionName(name="Font"))
    interface Resource extends FontDescriptionV2109
    {
        WrapperFactory<Resource> FACTORY = WrapperFactory.of(Resource.class);
        
        static Resource newInstance(Identifier id)
        {
            return FACTORY.getStatic().staticNewInstance(id);
        }
        @WrapConstructor
        Resource staticNewInstance(Identifier id);
        
        @WrapMinecraftFieldAccessor(@VersionName(name="id"))
        Identifier getId();
    }
}
