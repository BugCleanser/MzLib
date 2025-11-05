package mz.mzlib.minecraft.text.object;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.authlib.GameProfile;
import mz.mzlib.minecraft.component.type.GameProfileComponentV2005;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=2109)
@WrapMinecraftClass(@VersionName(name="net.minecraft.text.object.PlayerTextObjectContents"))
public interface TextObjectContentsPlayerV2109 extends WrapperObject, TextObjectContentsV2109
{
    WrapperFactory<TextObjectContentsPlayerV2109> FACTORY = WrapperFactory.of(TextObjectContentsPlayerV2109.class);
    
    static TextObjectContentsPlayerV2109 newInstance(GameProfile.Description gameProfile, boolean hat)
    {
        return FACTORY.getStatic().static$newInstance0(GameProfileComponentV2005.newInstance(gameProfile), hat);
    }
    @WrapConstructor
    TextObjectContentsPlayerV2109 static$newInstance0(GameProfileComponentV2005 gameProfile, boolean hat);
}
