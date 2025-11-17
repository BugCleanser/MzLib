package mz.mzlib.util.nothing;

import mz.mzlib.asm.tree.AbstractInsnNode;
import mz.mzlib.asm.tree.MethodInsnNode;
import mz.mzlib.asm.tree.VarInsnNode;
import mz.mzlib.util.ClassUtil;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.asm.AsmUtil;
import mz.mzlib.util.wrapper.WrapperClassInfo;
import mz.mzlib.util.wrapper.WrapperObject;

import java.lang.invoke.MethodType;
import java.lang.reflect.Member;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NothingInjectLocating
{
    public AbstractInsnNode[] insns;
    public Set<Integer> locations;
    public Map<String, Integer> taggedLocalVars = new HashMap<>();
    public NothingInjectLocating(AbstractInsnNode[] insns, int begin)
    {
        this.insns = insns;
        this.locations = new HashSet<>(Collections.singleton(begin));
    }
    public NothingInjectLocating(AbstractInsnNode[] insns)
    {
        this(insns, 0);
    }

    public void offset(int offset)
    {
        this.forEach(l ->
        {
            int result = l + offset;
            if(result >= 0 && result < insns.length)
                return Collections.singleton(result);
            else
                return Collections.emptySet();
        });
    }

    public void nextInvokeWrapped(Class<? extends WrapperObject> ownerWrapper, String name, Class<?>... parameterTypes)
    {
        try
        {
            this.nextInvoke(WrapperClassInfo.get(ownerWrapper).getWrappedMembers()
                .get(ownerWrapper.getDeclaredMethod(name, parameterTypes)));
        }
        catch(NoSuchMethodException e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
    public void nextInvoke(Member member)
    {
        this.nextInvoke(member.getDeclaringClass(), member.getName(), ClassUtil.methodType(member));
    }
    public void nextInvoke(Class<?> owner, String name, MethodType methodType)
    {
        this.nextInvoke(AsmUtil.getType(owner), name, methodType.descriptorString());
    }
    public void nextInvoke(String owner, String name, String desc)
    {
        this.next(l ->
        {
            if(!(this.insns[l] instanceof MethodInsnNode))
                return false;
            MethodInsnNode insn = (MethodInsnNode) this.insns[l];
            return insn.owner.equals(owner) && insn.name.equals(name) && insn.desc.equals(desc);
        });
    }

    public void tagLocalVar(String tag)
    {
        for(int l : this.locations)
        {
            if(!(this.insns[l] instanceof VarInsnNode))
                throw new IllegalStateException(
                    "Try to tag local var but current insn is not var insn node: " + this.insns[l]);
            int index = ((VarInsnNode) this.insns[l]).var;
            if(this.taggedLocalVars.containsKey(tag) && !Objects.equals(this.taggedLocalVars.get(tag), index))
                throw new IllegalStateException(
                    "Tagging local var conflict: " + index + " and " + this.taggedLocalVars.get(tag) + ".");
            this.taggedLocalVars.put(tag, index);
        }
    }

    public void next(int opcode)
    {
        this.next(opcode, Integer.MAX_VALUE);
    }
    public void next(int opcode, int limit)
    {
        this.next(l -> insns[l].getOpcode() == opcode, limit);
    }
    public void allLater(int opcode)
    {
        this.allLater(opcode, Integer.MAX_VALUE);
    }
    public void allLater(int opcode, int limit)
    {
        this.allLater(l -> insns[l].getOpcode() == opcode, limit);
    }
    public void next(AbstractInsnNode insn)
    {
        this.next(insn, Integer.MAX_VALUE);
    }
    public void next(AbstractInsnNode insn, int limit)
    {
        this.next(l -> AsmUtil.equals(insns[l], insn), limit);
    }

    public void next(Predicate<Integer> predicate, int limit)
    {
        this.forEach(l ->
        {
            for(long i = l + 1, end = Math.min(insns.length, 1 + l + (long) limit); i < end; i++)
            {
                if(predicate.test((int) i))
                    return Collections.singleton((int) i);
            }
            return Collections.emptySet();
        });
    }
    public void next(Predicate<Integer> predicate)
    {
        this.next(predicate, Integer.MAX_VALUE);
    }
    public void allLater(Predicate<Integer> predicate, int limit)
    {
        this.forEach(l ->
        {
            HashSet<Integer> result = new HashSet<>();
            for(long i = l + 1, end = Math.min(insns.length, 1 + l + (long) limit); i < end; i++)
            {
                if(predicate.test((int) i))
                    result.add((int) i);
            }
            return result;
        });
    }
    public void allLater(Predicate<Integer> predicate)
    {
        this.allLater(predicate, Integer.MAX_VALUE);
    }

    public void forEach(Function<Integer, Set<Integer>> action)
    {
        this.locations = this.locations.stream().map(action).flatMap(Set::stream).collect(Collectors.toSet());
    }
}
