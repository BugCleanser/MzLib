package mz.mzlib.minecraft.component.type;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.InvertibleFunction;
import mz.mzlib.util.proxy.ListProxy;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.List;

@WrapMinecraftClass(@VersionName(name="net.minecraft.component.type.LoreComponent", begin=2005))
public interface LoreComponentV2005 extends WrapperObject
{
    WrapperFactory<LoreComponentV2005> FACTORY = WrapperFactory.find(LoreComponentV2005.class);
    @Deprecated
    @WrapperCreator
    static LoreComponentV2005 create(Object wrapped)
    {
        return WrapperObject.create(LoreComponentV2005.class, wrapped);
    }
    
    static LoreComponentV2005 newInstance(List<Text> lines)
    {
        return create(null).staticNewInstance(lines);
    }
    @WrapConstructor
    LoreComponentV2005 staticNewInstance0(List<Object> lines);
    default LoreComponentV2005 staticNewInstance(List<Text> lines)
    {
        return staticNewInstance0(new ListProxy<>(lines, InvertibleFunction.wrapper(Text.FACTORY).inverse()));
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="comp_2400"))
    List<Object> getLines0();
    
    default List<Text> getLines()
    {
        return new ListProxy<>(this.getLines0(), InvertibleFunction.wrapper(Text.FACTORY));
    }
}
