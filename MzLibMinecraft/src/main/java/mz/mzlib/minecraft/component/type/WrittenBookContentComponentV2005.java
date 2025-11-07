package mz.mzlib.minecraft.component.type;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.text.RawFilteredPairV2005;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.Option;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.component.type.WrittenBookContentComponent", begin = 2005))
public interface WrittenBookContentComponentV2005 extends WrapperObject
{
    WrapperFactory<WrittenBookContentComponentV2005> FACTORY = WrapperFactory.of(
        WrittenBookContentComponentV2005.class);
    @Deprecated
    @WrapperCreator
    static WrittenBookContentComponentV2005 create(Object wrapped)
    {
        return WrapperObject.create(WrittenBookContentComponentV2005.class, wrapped);
    }

    static WrittenBookContentComponentV2005 def()
    {
        return FACTORY.getStatic().static$def();
    }
    @WrapMinecraftFieldAccessor(@VersionName(name = "DEFAULT"))
    WrittenBookContentComponentV2005 static$def();

    default String getTitle()
    {
        return (String) this.getTitle0().get0(false);
    }
    @WrapMinecraftFieldAccessor(@VersionName(name = "comp_2419"))
    RawFilteredPairV2005 getTitle0();

    @WrapMinecraftFieldAccessor(@VersionName(name = "comp_2420"))
    String getAuthor();

    @WrapMinecraftFieldAccessor(@VersionName(name = "comp_2421"))
    int getGeneration();

    @WrapMinecraftFieldAccessor(@VersionName(name = "comp_2422"))
    List<?> getPages0();
    default List<Text> getPages()
    {
        return this.getPages0().stream().map(RawFilteredPairV2005.FACTORY::create).map(p -> p.get(false, Text.FACTORY))
            .collect(Collectors.toList());
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "comp_2423"))
    boolean isResolved();

    static WrittenBookContentComponentV2005 newInstance(
        String title,
        String author,
        int generation,
        List<Text> pages,
        boolean resolved)
    {
        return newInstance0(
            RawFilteredPairV2005.newInstance0(title, Optional.empty()), author, generation,
            pages.stream().map(p -> RawFilteredPairV2005.newInstance(p, Option.none()).getWrapped())
                .collect(Collectors.toList()), resolved
        );
    }
    static WrittenBookContentComponentV2005 newInstance0(
        RawFilteredPairV2005 title,
        String author,
        int generation,
        List<?> pages,
        boolean resolved)
    {
        return FACTORY.getStatic().static$newInstance0(title, author, generation, pages, resolved);
    }
    @WrapConstructor
    WrittenBookContentComponentV2005 static$newInstance0(
        RawFilteredPairV2005 title,
        String author,
        int generation,
        List<?> pages,
        boolean resolved);

    default WrittenBookContentComponentV2005 withTitle(String title)
    {
        return newInstance0(
            RawFilteredPairV2005.newInstance0(title, Optional.empty()), this.getAuthor(), this.getGeneration(),
            this.getPages0(), this.isResolved()
        );
    }

    default WrittenBookContentComponentV2005 withAuthor(String author)
    {
        return newInstance0(this.getTitle0(), author, this.getGeneration(), this.getPages0(), this.isResolved());
    }

    default WrittenBookContentComponentV2005 withGeneration(int generation)
    {
        return newInstance0(this.getTitle0(), this.getAuthor(), generation, this.getPages0(), this.isResolved());
    }

    default WrittenBookContentComponentV2005 withPages(List<Text> pages)
    {
        return newInstance0(
            this.getTitle0(), this.getAuthor(), this.getGeneration(),
            pages.stream().map(p -> RawFilteredPairV2005.newInstance(p, Option.none()).getWrapped())
                .collect(Collectors.toList()), this.isResolved()
        );
    }

    default WrittenBookContentComponentV2005 withResolved(boolean resolved)
    {
        return newInstance0(this.getTitle0(), this.getAuthor(), this.getGeneration(), this.getPages0(), resolved);
    }
}
