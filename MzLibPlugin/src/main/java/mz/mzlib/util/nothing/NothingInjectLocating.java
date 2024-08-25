package mz.mzlib.util.nothing;

import mz.mzlib.asm.tree.AbstractInsnNode;
import mz.mzlib.util.asm.AsmUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NothingInjectLocating
{
    public AbstractInsnNode[] insns;
    public Set<Integer> locations=new HashSet<>(Collections.singleton(0));
    public NothingInjectLocating(AbstractInsnNode[] insns)
    {
        this.insns = insns;
    }

    public void offset(int offset)
    {
        this.forEach(l->
        {
            int result=l+offset;
            if(result>=0 && result<insns.length)
                return Collections.singleton(result);
            else
                return Collections.emptySet();
        });
    }

    public void next(int opcode)
    {
        this.next(opcode,Integer.MAX_VALUE);
    }
    public void next(int opcode,int limit)
    {
        this.next(l-> insns[l].getOpcode()==opcode,limit);
    }
    public void allAfter(int opcode)
    {
        this.allAfter(opcode,Integer.MAX_VALUE);
    }
    public void allAfter(int opcode,int limit)
    {
        this.allAfter(l-> insns[l].getOpcode()==opcode,limit);
    }
    public void next(AbstractInsnNode insn)
    {
        this.next(insn,Integer.MAX_VALUE);
    }
    public void next(AbstractInsnNode insn,int limit)
    {
        this.next(l->AsmUtil.equals(insns[l],insn),limit);
    }

    public void next(Predicate<Integer> predicate, int limit)
    {
        this.forEach(l->
        {
            for(long i = l+1, end = Math.min(insns.length,1+l+(long)limit); i<end; i++)
            {
                if(predicate.test((int)i))
                    return Collections.singleton((int)i);
            }
            return Collections.emptySet();
        });
    }
    public void allAfter(Predicate<Integer> predicate, int limit)
    {
        this.forEach(l->
        {
            HashSet<Integer> result = new HashSet<>();
            for(long i = l+1, end = Math.min(insns.length,1+l+(long)limit); i<end; i++)
            {
                if(predicate.test((int)i))
                    result.add((int)i);
            }
            return result;
        });
    }

    public void forEach(Function<Integer,Set<Integer>> action)
    {
        this.locations=this.locations.stream().map(action).flatMap(Set::stream).collect(Collectors.toSet());
    }
}
