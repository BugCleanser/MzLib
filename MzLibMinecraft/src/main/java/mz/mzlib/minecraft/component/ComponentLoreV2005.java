package mz.mzlib.minecraft.component;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.ListWrapped;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.List;

@WrapMinecraftClass(@VersionName(name="net.minecraft.component.type.LoreComponent", begin=2005))
public interface ComponentLoreV2005 extends WrapperObject
{
    @WrapperCreator
    static ComponentLoreV2005 create(Object wrapped)
    {
        return WrapperObject.create(ComponentLoreV2005.class, wrapped);
    }
    
    static ComponentLoreV2005 newInstance(List<Text> lines)
    {
        return create(null).staticNewInstance(lines);
    }
    @WrapConstructor
    ComponentLoreV2005 staticNewInstance0(List<?> lines);
    default ComponentLoreV2005 staticNewInstance(List<Text> lines)
    {
        return staticNewInstance0(new ListWrapped<>(lines, Text::create));
    }
}
