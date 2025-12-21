package mz.mzlib;

import mz.mzlib.event.RegistrarEventClass;
import mz.mzlib.event.RegistrarEventListener;
import mz.mzlib.i18n.RegistrarI18n;
import mz.mzlib.module.MzModule;
import mz.mzlib.tester.Tester;
import mz.mzlib.util.Instance;
import mz.mzlib.util.nothing.RegistrarNothingClass;

public class MzLib extends MzModule
{
    public static MzLib instance = new MzLib();

    @Override
    public void onLoad()
    {
        this.register(Instance.Registrar.instance);

        this.register(Tester.Registrar.instance);

        this.register(RegistrarI18n.instance);
        this.register(RegistrarNothingClass.instance);
        this.register(RegistrarEventClass.instance);
        this.register(RegistrarEventListener.instance);
    }
}
