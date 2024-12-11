package mz.mzlib.demo;

import mz.mzlib.module.MzModule;

public class Demo extends MzModule {
    public static Demo instance = new Demo();

    @Override
    public void onLoad() {
        this.register(CustomSlotBehavior.instance);
    }
}
