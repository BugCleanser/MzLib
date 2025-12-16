package mz.mzlib.minecraft.ui.window.control;

import mz.mzlib.minecraft.ui.window.UiAbstractWindow;
import mz.mzlib.util.Option;

import java.awt.*;
import java.util.function.Function;

public class UiWindowRegion extends UiWindowControl
{
    Option<UiAbstractWindow> ui = Option.none();
    Function<Integer, Option<Point>> pointGetter;
    public UiWindowRegion(Dimension size, Function<Integer, Option<Point>> pointGetter)
    {
        super(new Rectangle(size));
        this.pointGetter = pointGetter;
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
            }
        );
    }

    public void setUi(UiAbstractWindow value)
    {
        this.ui = Option.some(value);
    }
    @Override
    public Option<? extends UiAbstractWindow> getUi()
    {
        return this.ui;
    }
    public Option<Point> getPoint(int index)
    {
        return this.pointGetter.apply(index);
    }
}
