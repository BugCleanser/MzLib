package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.authlib.GameProfile;
import mz.mzlib.minecraft.text.object.TextObjectContentsAtlasV2109;
import mz.mzlib.minecraft.text.object.TextObjectContentsPlayerV2109;
import mz.mzlib.minecraft.text.object.TextObjectContentsV2109;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=2109)
@WrapSameClass(Text.class)
public interface TextObjectV2109 extends WrapperObject, Text
{
    WrapperFactory<TextObjectV2109> FACTORY = WrapperFactory.of(TextObjectV2109.class);
    
    static TextObjectV2109 atlas(Identifier atlas, Identifier sprite)
    {
        return newInstance(TextObjectContentsAtlasV2109.newInstance(atlas, sprite));
    }
    static TextObjectV2109 player(GameProfile.Description gameProfile, boolean hat)
    {
        return newInstance(TextObjectContentsPlayerV2109.newInstance(gameProfile, hat));
    }
    
    static TextObjectV2109 newInstance(TextObjectContentsV2109 contents)
    {
        return TextMutableV1600.newInstanceV1900(TextContentObjectV2109.newInstance(contents)).as(FACTORY);
    }
    default TextObjectContentsV2109 getObjectContents()
    {
        return this.getContentV1900().as(TextContentObjectV2109.FACTORY).getContents();
    }
    default Type getObjectType()
    {
        TextObjectContentsV2109 contents = this.getObjectContents();
        if(contents.is(TextObjectContentsAtlasV2109.FACTORY))
            return Type.ATLAS;
        if(contents.is(TextObjectContentsPlayerV2109.FACTORY))
            return Type.PLAYER;
        return Type.UNKNOWN;
    }
    enum Type
    {
        UNKNOWN,
        ATLAS,
        PLAYER
    }
}
