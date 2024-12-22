package mz.mzlib.minecraft.item.component;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.text.RawFilteredPairV2005;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WrapMinecraftClass(@VersionName(name="net.minecraft.component.type.WrittenBookContentComponent", begin=2005))
public interface ComponentWrittenBookContentV2005 extends WrapperObject
{
    @WrapperCreator
    static ComponentWrittenBookContentV2005 create(Object wrapped)
    {
        return WrapperObject.create(ComponentWrittenBookContentV2005.class, wrapped);
    }
    
    static ComponentWrittenBookContentV2005 empty()
    {
        return create(null).staticEmpty();
    }
    @WrapMinecraftFieldAccessor(@VersionName(name="DEFAULT"))
    ComponentWrittenBookContentV2005 staticEmpty();
    
    static ComponentWrittenBookContentV2005 newInstance(String title, String author, int generation, List<Text> pages, boolean resolved)
    {
        return newInstance0(RawFilteredPairV2005.newInstance0(title, Optional.empty()), author, generation, pages.stream().map(p->RawFilteredPairV2005.newInstance(p, null)).collect(Collectors.toList()), resolved);
    }
    static ComponentWrittenBookContentV2005 newInstance0(RawFilteredPairV2005 title, String author, int generation, List<?> pages, boolean resolved)
    {
        return create(null).staticNewInstance0(title, author, generation, pages, resolved);
    }
    @WrapConstructor
    ComponentWrittenBookContentV2005 staticNewInstance0(RawFilteredPairV2005 title, String author, int generation, List<?> pages, boolean resolved);
    
    static ComponentWrittenBookContentV2005 newInstance(String title, String author, int generation, List<Text> pages)
    {
        return newInstance(title, author, generation, pages, false);
    }
    
    default ComponentWrittenBookContentV2005 withPagesReplaced(List<Text> pages)
    {
        return this.withPagesReplaced0(pages.stream().map(p->RawFilteredPairV2005.newInstance(p, null).getWrapped()).collect(Collectors.toList()));
    }
    @WrapMinecraftMethod(@VersionName(name="method_58188"))
    ComponentWrittenBookContentV2005 withPagesReplaced0(List<?> rawFilteredPairPages);
}
