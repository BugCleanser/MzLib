package mz.mzlib.event;

import mz.mzlib.asm.*;
import mz.mzlib.asm.tree.ClassNode;
import mz.mzlib.asm.tree.InsnList;
import mz.mzlib.asm.tree.MethodNode;
import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.ClassUtil;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.asm.AsmUtil;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class RegistrarEventClass implements IRegistrar<Class<? extends Event>>
{
    public static RegistrarEventClass instance=new RegistrarEventClass();

    public  Class<Class<? extends Event>> getType()
    {
        return RuntimeUtil.cast(Class.class);
    }

    public boolean isRegistrable(Class<? extends Event> object)
    {
        return Event.class.isAssignableFrom(object);
    }

    public void register(MzModule module, Class<? extends Event> object)
    {
        ListenerHandler.handlers.put(object,new ListenerHandler());
        ClassNode cn=new ClassNode();
        new ClassReader(ClassUtil.getByteCode(object)).accept(cn,0);
        MethodNode mn = AsmUtil.getMethodNode(cn, "call",AsmUtil.getDesc(void.class,new Class[0]));
        if(mn==null || (mn.access&Opcodes.ACC_ABSTRACT)!=0)
            throw new IllegalStateException("Registered event class must implements method call: "+object);
        mn.instructions=new InsnList();
        mn.instructions.add(AsmUtil.insnVarLoad(Event.class,0));
        mn.visitInvokeDynamicInsn("call",AsmUtil.getDesc(void.class,Event.class),new Handle(Opcodes.H_INVOKESTATIC,AsmUtil.getType(ListenerHandler.class),"getCallSite",AsmUtil.getDesc(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, Class.class), false),  Type.getType(AsmUtil.getDesc(object)));
        mn.instructions.add(AsmUtil.insnReturn(void.class));
        ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_FRAMES|ClassWriter.COMPUTE_MAXS);
        cn.accept(cw);
        ClassUtil.defineClass(object.getClassLoader(),cn.name,cw.toByteArray());
    }

    public void unregister(MzModule module, Class<? extends Event> object)
    {
        ListenerHandler.handlers.remove(object);
    }
}
