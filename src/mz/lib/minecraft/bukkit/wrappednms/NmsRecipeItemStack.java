package mz.lib.minecraft.bukkit.wrappednms;

import com.google.common.collect.Lists;
import io.github.karlatemp.unsafeaccessor.Root;
import mz.asm.*;
import mz.asm.tree.*;
import mz.lib.*;
import mz.lib.minecraft.bukkit.*;
import mz.lib.minecraft.bukkit.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkit.nothing.NothingBukkitInject;
import mz.lib.minecraft.bukkit.nothing.NothingBukkit;
import mz.lib.minecraft.bukkit.wrappedobc.ObcItemStack;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.nothing.*;
import mz.lib.wrapper.WrappedArray;
import mz.lib.wrapper.WrappedArrayClass;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.*;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.Collectors;

@WrappedBukkitClass({@VersionName(value="nms.RecipeItemStack",maxVer=17),@VersionName(value="net.minecraft.world.item.crafting.RecipeItemStack",minVer=17)})
public interface NmsRecipeItemStack extends WrappedBukkitObject, NothingBukkit
{
	@WrappedBukkitFieldAccessor(@VersionName("a"))
	NmsRecipeItemStack staticAir();
	static NmsRecipeItemStack air()
	{
		return WrappedObject.getStatic(NmsRecipeItemStack.class).staticAir();
	}
	
	static NmsRecipeItemStack newInstance()
	{
		try
		{
			NmsRecipeItemStack r=WrappedObject.wrap(NmsRecipeItemStack.class,Root.getUnsafe().allocateInstance(WrappedObject.getRawClass(NmsRecipeItemStack.class)));
			if(BukkitWrapper.v13)
				r.setProvidersV13(ProviderArrayV13.newInstance(0));
			r.setChoices(new ArrayList<>());
			return r;
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	static NmsRecipeItemStack newInstance(MaterialData...choices)
	{
		NmsRecipeItemStack r=newInstance();
		r.setChoices(Lists.newArrayList(choices).stream().map(m->m.toItemStack(1)).collect(Collectors.toList()));
		return r;
	}
	Ref<WeakHashMap<Object,Predicate<ItemStack>>> testers=new Ref<>(new WeakHashMap<>());
	static NmsRecipeItemStack custom(List<ItemStack> choices,Predicate<ItemStack> tester)
	{
		NmsRecipeItemStack r=newInstance();
		r.setChoices(choices);
		synchronized(testers)
		{
			WeakHashMap<Object,Predicate<ItemStack>> t=new WeakHashMap<>(testers.get());
			t.put(r.getRaw(),tester);
			testers.set(t);
		}
		return r;
	}
	static NmsRecipeItemStack newInstance(Material...choices)
	{
		return custom(Lists.newArrayList(choices).stream().map(ItemStack::new).collect(Collectors.toList()),i->
		{
			if(!isItemOriginal(i))
				return false;
			for(Material m:choices)
			{
				if(i.getType()==m)
					return true;
			}
			return false;
		});
	}
	static NmsRecipeItemStack hybrid(ItemStack ...choices)
	{
		if(choices.length==0)
			return air();
		return custom(Lists.newArrayList(choices),i->
		{
			boolean original=isItemOriginal(i);
			MaterialData d=ItemStackBuilder.getData(i);
			for(ItemStack c:choices)
			{
				MaterialData dc=ItemStackBuilder.getData(c);
				if(c.isSimilar(dc.toItemStack(1)))
				{
					if(original&&d.equals(dc))
						return true;
				}
				else if(c.isSimilar(i))
						return true;
			}
			return false;
		});
	}
	
	@WrappedBukkitClass({@VersionName(value="nms.RecipeItemStack$Provider",minVer=13,maxVer=17),@VersionName(value="net.minecraft.world.item.crafting.RecipeItemStack$Provider",minVer=17)})
	interface ProviderV13 extends WrappedBukkitObject
	{
	}
	@WrappedArrayClass(ProviderV13.class)
	interface ProviderArrayV13 extends WrappedArray<ProviderV13>
	{
		static ProviderArrayV13 newInstance(int length)
		{
			return (ProviderArrayV13) WrappedObject.getStatic(ProviderArrayV13.class).staticNewInstance(length);
		}
	}
	
	@WrappedBukkitFieldAccessor({@VersionName(minVer=13,maxVer=16, value="c"),@VersionName(minVer=16, value="b")})
	void setProvidersV13(ProviderArrayV13 providers);
	
	@WrappedBukkitFieldAccessor({@VersionName("choices"),@VersionName(minVer=17, value="c")})
	NmsItemStackArray getChoices0();
	default List<ItemStack> getChoices()
	{
		return Lists.newArrayList(getChoices0().getRaw()).stream().map(i->ObcItemStack.asBukkitCopy(WrappedObject.wrap(NmsItemStack.class,i))).collect(Collectors.toList());
	}
	default ItemStack getChoice(int index)
	{
		return ObcItemStack.asBukkitCopy(getChoices0().get(index));
	}
	@WrappedBukkitFieldAccessor(value={@VersionName("choices"),@VersionName(minVer=17, value="c")})
	void setChoices(NmsItemStackArray choices);
	default void setChoices(List<ItemStack> choices)
	{
		setChoices(WrappedObject.wrap(NmsItemStackArray.class,choices.stream().map(i->ObcItemStack.asNMSCopy(i).getRaw()).toArray(i->NmsItemStackArray.newInstance(i).getRaw())));
	}
	
	@WrappedBukkitMethod(value=@VersionName({"test","a"}))
	boolean test(NmsItemStack item);
	List<Predicate<ItemStack>> originalItemFilters=new CopyOnWriteArrayList<>();
	static boolean isItemOriginal(ItemStack is)
	{
		if(!is.hasItemMeta())
			return true;
		boolean r=true;
		for(Predicate<ItemStack> f:originalItemFilters)
			if(!f.test(is))
			{
				r=false;
				break;
			}
		return r;
	}
	
	class TestBeforeBridge implements BiFunction<Object,Object,Optional<Boolean>>
	{
		@Override
		public Optional<Boolean> apply(Object obj,Object item)
		{
			return WrappedObject.wrap(NmsRecipeItemStack.class,obj).testBefore(WrappedObject.wrap(NmsItemStack.class,item));
		}
	}
	@ManualByteCode
	@NothingBukkitInject(name=@VersionName({"test","a"}),args={NmsItemStack.class},location=NothingLocation.FRONT)
	static void testBeforeInject(NothingMethod nm,List<AbstractInsnNode> insns,AbstractInsnNode loc)
	{
		try
		{
			ClassNode cn=new ClassNode();
			cn.visit(Opcodes.V1_8,Opcodes.ACC_PUBLIC,AsmUtil.getType(nm.method.getDeclaringClass().getName()+"$TestBeforeBridge"),null,AsmUtil.getType(Object.class),new String[0]);
			cn.visitField(Opcodes.ACC_PUBLIC|Opcodes.ACC_STATIC,"instance",AsmUtil.getDesc(BiFunction.class),null,null);
			ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES);
			cn.accept(cw);
			Class<?> bridge=ClassUtil.loadClass(cn.name,cw.toByteArray(),nm.method.getDeclaringClass().getClassLoader());
			bridge.getDeclaredField("instance").set(null,new TestBeforeBridge());
			
			nm.node.instructions.insertBefore(loc,new FieldInsnNode(Opcodes.GETSTATIC,AsmUtil.getType(bridge),"instance",AsmUtil.getDesc(BiFunction.class)));
			nm.node.instructions.insertBefore(loc,new VarInsnNode(Opcodes.ALOAD,0));
			nm.node.instructions.insertBefore(loc,new VarInsnNode(Opcodes.ALOAD,1));
			nm.node.instructions.insertBefore(loc,new MethodInsnNode(Opcodes.INVOKEINTERFACE,AsmUtil.getType(BiFunction.class),"apply",AsmUtil.getDesc(new Class[]{Object.class,Object.class},Object.class),true));
			nm.node.instructions.insertBefore(loc,new InsnNode(Opcodes.DUP));
			LabelNode labelNode=new LabelNode();
			nm.node.instructions.insertBefore(loc,new JumpInsnNode(Opcodes.IFNULL,labelNode));
			nm.node.instructions.insertBefore(loc,AsmUtil.castNode(Optional.class,Object.class));
			nm.node.instructions.insertBefore(loc,new MethodInsnNode(Opcodes.INVOKEVIRTUAL,AsmUtil.getType(Optional.class),"get",AsmUtil.getDesc(new Class[0],Object.class)));
			nm.node.instructions.insertBefore(loc,AsmUtil.castNode(boolean.class,Object.class));
			nm.node.instructions.insertBefore(loc,AsmUtil.returnNode(boolean.class));
			nm.node.instructions.insertBefore(loc,labelNode);
			nm.node.instructions.insertBefore(loc,new InsnNode(Opcodes.POP));
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	default Optional<Boolean> testBefore(NmsItemStack item)
	{
		Predicate<ItemStack> tester=testers.get().get(this.getRaw());
		ItemStack is=ObcItemStack.asCraftMirror(item).getRaw();
		if(tester!=null)
			return Nothing.doReturn(tester.test(is));
		if(!isItemOriginal(is))
			return Nothing.doReturn(false);
		return Nothing.doContinue();
	}
	default boolean test(ItemStack item)
	{
		return test(ObcItemStack.ensure(item).getHandle());
	}
}
