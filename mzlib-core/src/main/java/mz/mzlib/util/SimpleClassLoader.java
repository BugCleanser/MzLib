package mz.mzlib.util;

public class SimpleClassLoader extends ClassLoader
{
    public SimpleClassLoader()
    {
    }

    public SimpleClassLoader(ClassLoader parent)
    {
        super(parent);
    }

    public Class<?> defineClass1(String name, byte[] b, int off, int len)
        throws ClassFormatError
    {
        return this.defineClass(name, b, off, len);
    }
    public Class<?> defineClass1(String name, byte[] b)
    {
        return this.defineClass1(name, b, 0, b.length);
    }
}
