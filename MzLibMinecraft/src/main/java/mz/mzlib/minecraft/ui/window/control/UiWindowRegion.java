package mz.mzlib.minecraft.ui.window.control;

import mz.mzlib.util.Option;

import java.awt.*;
import java.util.function.Function;

public class UiWindowRegion extends UiWindowControl
{
    Function<Integer, Option<Point>> pointGetter;
    Function<Point, Integer> indexGetter;
    public UiWindowRegion(Dimension size, Function<Integer, Option<Point>> pointGetter, Function<Point, Integer> indexGetter)
    {
        super(new Rectangle(size));
        this.pointGetter = pointGetter;
        this.indexGetter = indexGetter;
    }

    public static UiWindowRegion rect(Dimension size, int indexBegin)
    {
        return new UiWindowRegion(
            size,
            index ->
            {
                int i = index - indexBegin;
                if(i < 0 || i >= size.width * size.height)
                    return Option.none();
                return Option.some(new Point(i % size.width, i / size.width));
            },
            point -> indexBegin + point.x + point.y * size.width
        );
    }

    public Option<Point> getPoint(int index)
    {
        return this.pointGetter.apply(index);
    }
    @Override
    public int getIndex(Point point)
    {
        return this.indexGetter.apply(point);
    }
}
