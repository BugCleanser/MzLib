package mz.lib.module;

public interface Registrable
{
	void onReg(MzModule module);
	void onUnreg(MzModule module);
}
