package mz.mzlib.util.nothing;

import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.Type;
import mz.mzlib.asm.tree.*;
import mz.mzlib.asm.tree.analysis.*;
import mz.mzlib.util.Option;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.asm.AsmUtil;
import mz.mzlib.util.wrapper.WrapperClassData;
import mz.mzlib.util.wrapper.WrapperObject;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.lang.invoke.MethodType;
import java.lang.reflect.Member;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ApiStatus.Experimental
public class NothingInjectLocating
{
    String owner;
    MethodNode method;
    List<AbstractInsnNode> instructions;
    Set<Integer> locations;
    Map<String, Integer> taggedLocalVars = new HashMap<>();
    public NothingInjectLocating(String owner, MethodNode method, List<AbstractInsnNode> instructions)
    {
        this.owner = owner;
        this.method = method;
        this.instructions = instructions;
        this.locations = new HashSet<>(Collections.singleton(0));
    }
    
    public String getOwner()
    {
        return this.owner;
    }
    
    public MethodNode getMethod()
    {
        return this.method;
    }
    
    public List<AbstractInsnNode> getInstructions()
    {
        return this.instructions;
    }
    
    public Set<Integer> getLocations()
    {
        return locations;
    }
    
    public AbstractInsnNode getInsnNode(int index)
    {
        return this.getInstructions().get(index);
    }
    
    public void offset(int offset)
    {
        this.forEach(l ->
        {
            int result = l + offset;
            if(result >= 0 && result < this.instructions.size())
                return Collections.singleton(result);
            else
                return Collections.emptySet();
        });
    }
    
    public void stay()
    {
    }
    
    public void followingReturn()
    {
        if(this.getLocations().isEmpty())
            return;
        Set<Integer> opcodes = new HashSet<>(Arrays.asList(Opcodes.IRETURN, Opcodes.LRETURN, Opcodes.FRETURN, Opcodes.DRETURN, Opcodes.ARETURN, Opcodes.RETURN));
        this.following(i -> opcodes.contains(this.getInsnNode(i).getOpcode()));
    }
    
    public void followingThrow()
    {
        this.following(Opcodes.ATHROW);
    }
    
    /**
     * When <code>&lt;init></code> calling <code>super(...)</code> <br>
     * Locate its next insn node
     */
    public void afterSuper() throws AnalyzerException
    {
        Analyzer<SourceValue> analyzer = new Analyzer<>(new SourceInterpreter());
        MethodNode mn = new MethodNode();
        mn.instructions = AsmUtil.clone(new InsnList());
        @Nullable Frame<SourceValue>[] frames = analyzer.analyze(this.getOwner(), this.getMethod());
        this.next(i ->
        {
            AbstractInsnNode insn = this.getInsnNode(i);
            if(!(insn instanceof MethodInsnNode))
                return false;
            MethodInsnNode node = (MethodInsnNode) insn;
            if(insn.getOpcode() != Opcodes.INVOKESPECIAL || !node.name.equals("<init>"))
                return false;
            @Nullable Frame<SourceValue> frame = frames[mn.instructions.indexOf(insn)];
            if(frame == null)
                return false;
            Set<AbstractInsnNode> source = frame.getStack(frame.getStackSize() - Type.getArgumentTypes(node.desc).length - 1).insns;
            return source.size() == 1 && AsmUtil.equals(source.iterator().next(), AsmUtil.insnVarLoad(Object.class, 0));
        });
        this.offset(1); // after
        if(this.locations.size() != 1)
            throw new IllegalStateException();
    }

    public void nextAccessWrapped(Class<? extends WrapperObject> ownerWrapper, String name, Class<?>... parameterTypes)
    {
        try
        {
            this.nextAccess(WrapperClassData.get(ownerWrapper).getMember(ownerWrapper.getDeclaredMethod(name, parameterTypes)).getTarget());
        }
        catch(NoSuchMethodException e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
    public void nextAccess(Class<?> owner, String name, MethodType methodType)
    {
        this.nextAccess(AsmUtil.getType(owner), name, AsmUtil.getDesc(methodType));
    }
    public void nextAccess(Member member)
    {
        this.nextAccess(AsmUtil.getType(member.getDeclaringClass()), member.getName(), AsmUtil.getDesc(member));
    }
    public void nextAccess(String owner, String name, String desc)
    {
        this.next(l ->
        {
            for(MethodInsnNode insn : Option.some(this.instructions.get(l)).filter(MethodInsnNode.class))
            {
                return insn.owner.equals(owner) && insn.name.equals(name) && insn.desc.equals(desc);
            }
            for(FieldInsnNode insn : Option.some(this.instructions.get(l)).filter(FieldInsnNode.class))
            {
                return insn.owner.equals(owner) && insn.name.equals(name) && insn.desc.equals(desc);
            }
            return false;
        });
    }

    public void tagLocalVar(String tag)
    {
        for(int l : this.locations)
        {
            if(!(this.instructions.get(l) instanceof VarInsnNode))
                throw new IllegalStateException(
                    "Try to tag local var but current insn is not var insn node: " + this.instructions.get(l));
            int index = ((VarInsnNode) this.instructions.get(l)).var;
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
        this.next(l -> instructions.get(l).getOpcode() == opcode, limit);
    }
    public void following(int opcode)
    {
        this.following(opcode, Integer.MAX_VALUE);
    }
    public void following(int opcode, int limit)
    {
        this.following(l -> instructions.get(l).getOpcode() == opcode, limit);
    }
    public void next(AbstractInsnNode insn)
    {
        this.next(insn, Integer.MAX_VALUE);
    }
    public void next(AbstractInsnNode insn, int limit)
    {
        this.next(l -> AsmUtil.equals(instructions.get(l), insn), limit);
    }

    public void next(Predicate<Integer> predicate, int limit)
    {
        this.forEach(l ->
        {
            for(long i = l + 1, end = Math.min(instructions.size(), 1 + l + (long) limit); i < end; i++)
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
    public void following(Predicate<Integer> predicate, int limit)
    {
        this.forEach(l ->
        {
            HashSet<Integer> result = new HashSet<>();
            for(long i = l + 1, end = Math.min(instructions.size(), 1 + l + (long) limit); i < end; i++)
            {
                if(predicate.test((int) i))
                    result.add((int) i);
            }
            return result;
        });
    }
    public void following(Predicate<Integer> predicate)
    {
        this.following(predicate, Integer.MAX_VALUE);
    }

    public void forEach(Function<Integer, Set<Integer>> action)
    {
        this.locations = this.locations.stream().map(action).flatMap(Set::stream).collect(Collectors.toSet());
    }
}
