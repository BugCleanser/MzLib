package mz.mzlib.example;

import mz.mzlib.asm.ClassWriter;
import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.tree.ClassNode;
import mz.mzlib.util.AsmUtil;
import mz.mzlib.util.ClassUtil;

@Deprecated
public class PoopMountain
{
	@Deprecated
	public static class PoopBukkit
	{
		public enum PoopEnum
		{
			A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z
		}
	}
	
	public static void main(String[] args)
	{
		System.out.println(ClassUtil.getByteCode(int[].class));
		ClassNode cn=new ClassNode();
		cn.visit(Opcodes.V1_8,Opcodes.ACC_PUBLIC,"$Test",null,AsmUtil.getType(Object.class),new String[0]);
		ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_FRAMES|ClassWriter.COMPUTE_MAXS);
		cn.accept(cw);
		ClassUtil.defineClass(ClassLoader.getSystemClassLoader(),cn.name,cw.toByteArray());
	}
}
