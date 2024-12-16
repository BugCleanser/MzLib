package mz.mzlib.minecraft.text;

import io.github.karlatemp.unsafeaccessor.Root;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.text.Style", end=1400), @VersionName(name="net.minecraft.network.chat.Style", begin=1400, end=1403), @VersionName(name="net.minecraft.text.Style", begin=1403)})
public interface TextStyle extends WrapperObject
{
    @WrapperCreator
    static TextStyle create(Object wrapped)
    {
        return WrapperObject.create(TextStyle.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor({@VersionName(name="ROOT", end=1600), @VersionName(name="EMPTY", begin=1600)})
    TextStyle staticEmpty();
    static TextStyle empty()
    {
        return create(null).staticEmpty();
    }
    
    TextStyle staticNewInstance();
    @SpecificImpl("staticNewInstance")
    @VersionRange(end=1600)
    @WrapConstructor
    TextStyle staticNewInstanceV_1600();
    @SpecificImpl("staticNewInstance")
    @VersionRange(begin=1600)
    default TextStyle staticNewInstanceV1600()
    {
        try
        {
            return create(Root.getUnsafe().allocateInstance(TextStyle.create(null).staticGetWrappedClass()));
        }
        catch(InstantiationException e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
    static TextStyle newInstance()
    {
        return create(null).staticNewInstance();
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="bold"))
    Boolean getBold();
    
    @Deprecated
    @WrapMinecraftFieldAccessor(@VersionName(name="bold"))
    void setBold(Boolean value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="italic"))
    Boolean getItalic();
    
    @Deprecated
    @WrapMinecraftFieldAccessor(@VersionName(name="italic"))
    void setItalic(Boolean value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="underlined"))
    Boolean getUnderlined();
    
    @Deprecated
    @WrapMinecraftFieldAccessor(@VersionName(name="underlined"))
    void setUnderlined(Boolean value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="strikethrough"))
    Boolean getStrikethrough();
    
    @Deprecated
    @WrapMinecraftFieldAccessor(@VersionName(name="strikethrough"))
    void setStrikethrough(Boolean value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="obfuscated"))
    Boolean getObfuscated();
    
    @Deprecated
    @WrapMinecraftFieldAccessor(@VersionName(name="obfuscated"))
    void setObfuscated(Boolean value);
    
    // TODO
    @WrapMinecraftFieldAccessor({@VersionName(name="formatting", end=1400), @VersionName(name="color", begin=1400, end=1600)})
    TextFormat getColorV_1600();
    
    @Deprecated
    @WrapMinecraftFieldAccessor({@VersionName(name="formatting", end=1400), @VersionName(name="color", begin=1400, end=1600)})
    void setColorV_1600(TextFormat value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="color", begin=1600))
    TextFormat getColorV1600();
    
    @Deprecated
    @WrapMinecraftFieldAccessor(@VersionName(name="color", begin=1600))
    void setColorV1600(TextFormat value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="insertion"))
    String getInsertion();
    
    @Deprecated
    @WrapMinecraftFieldAccessor(@VersionName(name="insertion"))
    void setInsertion(String value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="hoverEvent"))
    TextHoverEvent getHoverEvent();
    
    @Deprecated
    @WrapMinecraftFieldAccessor(@VersionName(name="hoverEvent"))
    void setHoverEvent(TextHoverEvent value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="clickEvent"))
    TextClickEvent getClickEvent();
    
    @Deprecated
    @WrapMinecraftFieldAccessor(@VersionName(name="clickEvent"))
    void setClickEvent(TextClickEvent value);
}
