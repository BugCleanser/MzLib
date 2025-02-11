package mz.mzlib.minecraft.component;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.text.RawFilteredPairV2005;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
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
    
    static ComponentWrittenBookContentV2005 def()
    {
        return create(null).staticDef();
    }
    @WrapMinecraftFieldAccessor(@VersionName(name="DEFAULT"))
    ComponentWrittenBookContentV2005 staticDef();
    
    default String getTitle()
    {
        return (String)this.getTitle0().get0(false);
    }
    @WrapMinecraftFieldAccessor(@VersionName(name="comp_2419"))
    RawFilteredPairV2005 getTitle0();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="comp_2420"))
    String getAuthor();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="comp_2421"))
    int getGeneration();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="comp_2422"))
    List<?> getPages0();
    default List<Text> getPages()
    {
        return this.getPages0().stream().map(RawFilteredPairV2005::create).map(p->p.get(false, Text::create)).collect(Collectors.toList());
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="comp_2423"))
    boolean isResolved();
    
    static ComponentWrittenBookContentV2005 newInstance(String title, String author, int generation, List<Text> pages, boolean resolved)
    {
        return newInstance0(RawFilteredPairV2005.newInstance0(title, Optional.empty()), author, generation, pages.stream().map(p->RawFilteredPairV2005.newInstance(p, null).getWrapped()).collect(Collectors.toList()), resolved);
    }
    static ComponentWrittenBookContentV2005 newInstance0(RawFilteredPairV2005 title, String author, int generation, List<?> pages, boolean resolved)
    {
        return create(null).staticNewInstance0(title, author, generation, pages, resolved);
    }
    @WrapConstructor
    ComponentWrittenBookContentV2005 staticNewInstance0(RawFilteredPairV2005 title, String author, int generation, List<?> pages, boolean resolved);
    
    default ComponentWrittenBookContentV2005 withTitle(String title)
    {
        return newInstance0(RawFilteredPairV2005.newInstance0(title, Optional.empty()), this.getAuthor(), this.getGeneration(), this.getPages0(), this.isResolved());
    }
    
    default ComponentWrittenBookContentV2005 withAuthor(String author)
    {
        return newInstance0(this.getTitle0(), author, this.getGeneration(), this.getPages0(), this.isResolved());
    }
    
    default ComponentWrittenBookContentV2005 withGeneration(int generation)
    {
        return newInstance0(this.getTitle0(), this.getAuthor(), generation, this.getPages0(), this.isResolved());
    }
    
    default ComponentWrittenBookContentV2005 withPages(List<Text> pages)
    {
        return newInstance0(this.getTitle0(), this.getAuthor(), this.getGeneration(), pages.stream().map(p->RawFilteredPairV2005.newInstance(p, null).getWrapped()).collect(Collectors.toList()), this.isResolved());
    }
    
    default ComponentWrittenBookContentV2005 withResolved(boolean resolved)
    {
        return newInstance0(this.getTitle0(), this.getAuthor(), this.getGeneration(), this.getPages0(), resolved);
    }
}
