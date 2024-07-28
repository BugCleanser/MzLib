package mz.mzlib.example;

import mz.mzlib.asm.ClassReader;
import mz.mzlib.asm.ClassWriter;
import mz.mzlib.asm.tree.ClassNode;
import mz.mzlib.util.ClassUtil;
import mz.mzlib.util.asm.AsmUtil;

public class Test
{
	private static class Foo
	{
	}
	
	public static void main(String[] args) throws Throwable
	{
		try
		{
			ClassNode cn=new ClassNode();
			new ClassReader(ClassUtil.getByteCode(Foo.class)).accept(cn,0);
			cn.visitAnnotation(AsmUtil.getDesc(FunctionalInterface.class),true).visitEnd();
			ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_FRAMES|ClassWriter.COMPUTE_MAXS);
			cn.accept(cw);
			ClassUtil.defineClass(Foo.class.getClassLoader(),cn.name,cw.toByteArray());
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
	}
}
