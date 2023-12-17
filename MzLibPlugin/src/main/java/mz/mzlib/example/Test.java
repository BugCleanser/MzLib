package mz.mzlib.example;

import mz.mzlib.asm.ClassWriter;
import mz.mzlib.asm.MethodVisitor;
import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.tree.ClassNode;
import mz.mzlib.mzlang.lexer.Lexer;
import mz.mzlib.mzlang.lexer.Token;
import mz.mzlib.util.AsmUtil;
import mz.mzlib.util.ClassUtil;

import java.io.PrintStream;
import java.util.List;

public class Test
{
	public static void main(String[] args) throws Throwable
	{
		// 输入源代码
		String sourceCode = "public class Example { int x; public static void main() { System.out.println(\"Hello, Parser!\"); } }";
		
		// 词法分析
		Lexer lexer = new Lexer(sourceCode);
		List<Token> tokens = lexer.tokenize();
		System.out.println(tokens);
		
//		ClassNode cn=new ClassNode();
//		cn.visit(Opcodes.V1_8,Opcodes.ACC_PUBLIC,"Foo",null,AsmUtil.getType(Object.class),new String[]{"$mz.CannotLoad"});
//		MethodNode mn=(MethodNode)cn.visitMethod(Opcodes.ACC_PUBLIC|Opcodes.ACC_STATIC|Opcodes.ACC_ABSTRACT,"hello",AsmUtil.getDesc(void.class,new Class[0]),null,new String[0]);
//		mn.visitEnd();
//		ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_FRAMES|ClassWriter.COMPUTE_MAXS);
//		cn.accept(cw);
//		try(FileOutputStream fos=new FileOutputStream("E:/Temp/test1/Foo.class"))
//		{
//			fos.write(cw.toByteArray());
//		}
	}
	public static void use()
	{
	}
	public static void build()
	{
		ClassNode cn=new ClassNode();
		cn.visit(Opcodes.V1_8,Opcodes.ACC_PUBLIC,"Foo",null,AsmUtil.getType(Object.class),new String[0]);
		MethodVisitor mn=cn.visitMethod(Opcodes.ACC_PUBLIC|Opcodes.ACC_STATIC,"hello",AsmUtil.getDesc(void.class,new Class[0]),null,new String[0]);
		mn.visitCode();
		mn.visitFieldInsn(Opcodes.GETSTATIC,AsmUtil.getType(System.class),"out",AsmUtil.getDesc(PrintStream.class));
		mn.visitLdcInsn("HelloWorld");
		mn.visitMethodInsn(Opcodes.INVOKEVIRTUAL,AsmUtil.getType(PrintStream.class),"println",AsmUtil.getDesc(void.class,String.class),false);
		mn.visitInsn(Opcodes.RETURN);
		mn.visitEnd();
		cn.visitEnd();
		ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_FRAMES|ClassWriter.COMPUTE_MAXS);
		cn.accept(cw);
		ClassUtil.defineClass(Test.class.getClassLoader(),cn.name,cw.toByteArray());
	}
}
