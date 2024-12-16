package mz.mzlib.module;

public interface Registrable
{
    void onRegister(MzModule module);

    void onUnregister(MzModule module);
}
