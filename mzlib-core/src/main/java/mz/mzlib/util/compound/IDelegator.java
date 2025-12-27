package mz.mzlib.util.compound;

@Deprecated
public interface IDelegator
{
    Object getDelegate();
    void setDelegate(Object delegate);
}
