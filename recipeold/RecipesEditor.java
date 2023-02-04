package mz.lib.minecraft.bukkit.recipeold;

import com.google.common.collect.Lists;
import mz.lib.*;
import mz.lib.fengjian.Fengjian;
import mz.lib.minecraft.bukkit.LangUtil;
import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.command.AbsLastCommandProcessor;
import mz.lib.minecraft.bukkit.command.CommandHandler;
import mz.lib.minecraft.bukkit.inventory.AttributeEditor;
import mz.lib.minecraft.bukkit.inventory.CUI;
import mz.lib.minecraft.bukkit.inventory.IUI;
import mz.lib.minecraft.bukkit.inventory.InputBox;
import mz.lib.minecraft.bukkit.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkit.module.ISimpleModule;
import mz.lib.minecraft.bukkit.module.ModuleData;
import mz.lib.minecraft.bukkit.wrappednms.*;
import mz.lib.minecraft.bukkit.wrappedobc.ObcFurnaceRecipe;
import mz.lib.minecraft.bukkit.wrappedobc.ObcItemStack;
import mz.lib.minecraft.bukkit.wrapper.BukkitWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.*;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RecipesEditor<R extends Recipe&Keyed> extends AbsLastCommandProcessor implements ISimpleModule
{
	public static class IngredientEditor extends CUI
	{
		public NmsRecipeItemStack ingredient;
		public Consumer<NmsRecipeItemStack> onSave;
		
		public IngredientEditor(HumanEntity owner,String title,NmsRecipeItemStack ingredient,Consumer<NmsRecipeItemStack> onSave)
		{
			super(owner,36,title);
			this.ingredient=ingredient;
			this.onSave=onSave;
		}
		@Override
		public void setSlots(HumanEntity player,NmsContainer container)
		{
			for(int i=0;i<9;i++)
			{
				container.setSlot(i,NmsSlot.showOnly(container.getSlot(i)));
				container.setSlot(i+27,NmsSlot.showOnly(container.getSlot(i+27)));
			}
		}
		public void update()
		{
			super.update();
			for(int i=0;i<9;i++)
			{
				getInventory().setItem(i,ItemStackBuilder.blue_stained_glass_pane().setName("§0").get());
				getInventory().setItem(i+27,ItemStackBuilder.blue_stained_glass_pane().setName("§0").get());
			}
			getInventory().setItem(0,ItemStackBuilder.returnArrow().setName(LangUtil.getTranslated(owner,"mzlib.menu.return")).get());
			getInventory().setItem(8,ItemStackBuilder.questionMark().setName(LangUtil.getTranslated(owner,"mzlib.help")).setLore(LangUtil.getTranslated(owner,"mzlib.recipesEditor.ingredientEditor.help").split("\\n")).get());
			getInventory().setItem(35,ItemStackBuilder.checkmark().setName(LangUtil.getTranslated(owner,"mzlib.inputBox.OK")).get());
			if(ingredient!=null)
			{
				List<ItemStack> choices=ingredient.getChoices();
				for(int i=0;i<choices.size();i++)
				{
					getInventory().setItem(i+9,choices.get(i));
				}
			}
		}
		@Override
		public void onClick(ClickType type,HumanEntity player,int rawSlot)
		{
			super.onClick(type,player,rawSlot);
			if(rawSlot==0)
				ret();
			else if(rawSlot==35)
			{
				List<ItemStack> l=new ArrayList<>();
				for(int i=0;i<18;i++)
				{
					if(!ItemStackBuilder.isAir(getInventory().getItem(i+9)))
						l.add(getInventory().getItem(i+9));
				}
				onSave.accept(NmsRecipeItemStack.hybrid(l.toArray(new ItemStack[0])));
				ret();
			}
		}
		public Plugin getPlugin()
		{
			return MzLib.instance;
		}
	}
	
	public static NmsNBTTagCompound storeRecipes;
	public static List<RecipeEditor<?>> recipeEditors=new ArrayList<>();
	public static BukkitTask saveTask;
	
	public static RecipesEditor instance=new RecipesEditor();
	public RecipesEditor()
	{
		super(true,null,"recipes");
	}
	public static List<NamespacedKey> disabledRecipes(NmsNBTTagCompound recipes)
	{
		if(!recipes.containsKey("disabledRecipe"))
			recipes.set("disabledRecipe",NmsNBTTagList.newInstance());
		List<NmsNBTTagCompound> r=recipes.getList("disabledRecipe").values(NmsNBTTagCompound.wrapper);
		return new AbstractList<NamespacedKey>()
		{
			@Override
			public NamespacedKey get(int index)
			{
				return getNamespacedKey(r.get(index));
			}
			
			@Override
			public int size()
			{
				return r.size();
			}
			
			@Override
			public NamespacedKey remove(int index)
			{
				return getNamespacedKey(r.remove(index));
			}
			
			@Override
			public void add(int index,NamespacedKey element)
			{
				r.add(index,saveNamespacedKey(element));
			}
			
			@Override
			public boolean remove(Object o)
			{
				return r.remove(saveNamespacedKey((NamespacedKey) o));
			}
		};
	}
	public static List<NmsNBTTagCompound> fixedRecipes(NmsNBTTagCompound recipes)
	{
		if(!recipes.containsKey("fixedRecipe"))
			recipes.set("fixedRecipe",NmsNBTTagList.newInstance());
		return recipes.getList("fixedRecipe").values(NmsNBTTagCompound.wrapper);
	}
	public static NmsNBTTagCompound shapedRecipes()
	{
		if(!RecipesEditor.storeRecipes.containsKey("shapedRecipes"))
			storeRecipes.set("shapedRecipes",NmsNBTTagCompound.newInstance());
		return storeRecipes.getCompound("shapedRecipes");
	}
	public static NmsNBTTagCompound shapelessRecipes()
	{
		if(!RecipesEditor.storeRecipes.containsKey("shapelessRecipes"))
			storeRecipes.set("shapelessRecipes",NmsNBTTagCompound.newInstance());
		return storeRecipes.getCompound("shapelessRecipes");
	}
	public static NmsNBTTagCompound furnaceRecipes()
	{
		if(!RecipesEditor.storeRecipes.containsKey("furnaceRecipes"))
			storeRecipes.set("furnaceRecipes",NmsNBTTagCompound.newInstance());
		return storeRecipes.getCompound("furnaceRecipes");
	}
	public static NmsNBTTagCompound fuelRecipes()
	{
		if(!RecipesEditor.storeRecipes.containsKey("fuelRecipes"))
			storeRecipes.set("fuelRecipes",NmsNBTTagCompound.newInstance());
		return storeRecipes.getCompound("fuelRecipes");
	}
	public static NmsNBTTagCompound anvilRecipes()
	{
		if(!RecipesEditor.storeRecipes.containsKey("anvilRecipes"))
			storeRecipes.set("anvilRecipes",NmsNBTTagCompound.newInstance());
		return storeRecipes.getCompound("anvilRecipes");
	}
	public static NmsRecipeItemStack getRecipeItemStack(NmsNBTTagList l)
	{
		if(l.size()==0)
			return NmsRecipeItemStack.air();
		return NmsRecipeItemStack.hybrid(l.values(NmsNBTTagCompound.wrapper).stream().map(n->new ItemStackBuilder(n.getCompound("raw")).get()).collect(Collectors.toList()).toArray(new ItemStack[0]));
	}
	public static NmsNBTTagList saveRecipeItemStack(NmsRecipeItemStack i)
	{
		return NmsNBTTagList.newInstance(i==null?new ArrayList<>():i.getChoices().stream().map(is->NmsNBTTagCompound.newInstance().set("raw",new ItemStackBuilder(is).nbt())).collect(Collectors.toList()));
	}
	@SuppressWarnings("deprecation")
	public static NamespacedKey getNamespacedKey(NmsNBTTagCompound nbt)
	{
		return new NamespacedKey(nbt.getString("namespace"),nbt.getString("key"));
	}
	public static NmsNBTTagCompound saveNamespacedKey(NamespacedKey key)
	{
		return NmsNBTTagCompound.newInstance().set("namespace",NmsNBTTagString.newInstance(key.getNamespace())).set("key",NmsNBTTagString.newInstance(key.getKey()));
	}
	public static ShapedRecipe getShapedRecipe(NmsNBTTagCompound nbt)
	{
		ShapedRecipe r=new ShapedRecipe(getNamespacedKey(nbt.getCompound("key")),new ItemStackBuilder(nbt.getCompound("result")).get());
		r.shape(nbt.getList("shape").values(NmsNBTTagString.wrapper).stream().map(n->n.getValue()).toArray(String[]::new));
		nbt.getCompound("ingredients").getMap().forEach((k,v)->
		{
			NmsNBTTagCompound n=NmsNBTTagCompound.wrapper.wrap(v);
			r.setIngredient(k.charAt(0),ItemStackBuilder.getData(new ItemStackBuilder(n.getString("id")).setChildId(n.getShort("childId")).get()));
		});
		return r;
	}
	public static void disableRecipe(NmsNBTTagCompound storeRecipes,NamespacedKey key)
	{
		disabledRecipes(storeRecipes).add(key);
		save();
	}
	public static File getStoreRecipeFile()
	{
		return new File(MzLib.instance.getDataFolder(),"recipes.nbt");
	}
	public static void save()
	{
		save(false);
	}
	public static void save(boolean immediately)
	{
		if(saveTask!=null&&saveTask.getTaskId()!=-1)
			saveTask.cancel();
		Runnable s=()->
		{
			if(!getStoreRecipeFile().exists())
			{
				getStoreRecipeFile().getParentFile().mkdirs();
				try
				{
					getStoreRecipeFile().createNewFile();
				}
				catch(Throwable e)
				{
					throw TypeUtil.castThrowable(e);
				}
			}
			try(DataOutputStream dos=new DataOutputStream(new FileOutputStream(getStoreRecipeFile())))
			{
				storeRecipes.write(dos);
			}
			catch(Throwable e)
			{
				throw TypeUtil.castThrowable(e);
			}
		};
		if(immediately)
			s.run();
		else
			saveTask=Bukkit.getScheduler().runTaskLater(MzLib.instance,s,200L);
	}
	@Override
	public void onEnable()
	{
		if(!BukkitWrapper.v13)
			Fengjian.decorate(MzLib.instance,NmsRecipesFurnaceV_13.class);
		Fengjian.decorate(MzLib.instance,ObcFurnaceRecipe.class);
		Fengjian.decorate(MzLib.instance,NmsTileEntityFurnace.class);
		if(getStoreRecipeFile().exists())
			try(DataInputStream dis=new DataInputStream(new FileInputStream(getStoreRecipeFile())))
			{
				storeRecipes=NmsNBTTagCompound.read(dis);
			}
			catch(Throwable e)
			{
				throw TypeUtil.castThrowable(e);
			}
		else
			storeRecipes=NmsNBTTagCompound.newInstance();
		recipeEditors.add(new RecipeEditor<NmsShapedRecipes>(p->new ItemStackBuilder("crafting_table").setName(StringUtil.replaceStrings(LangUtil.getTranslated(p,"mzlib.recipesEditor.kindRecipe.title"),new MapEntry<>("%\\{type\\}",LangUtil.getTranslated(p,"mzlib.recipesEditor.types.shapedRecipe")))).get(),shapedRecipes(),l->LangUtil.getTranslated(l,"mzlib.recipesEditor.types.shapedRecipe"),()->NmsCraftingManager.getCraftingRecipes().values().stream().filter(r->NmsShapedRecipes.wrapper.getRawClass().isAssignableFrom(r.getClass())).map(r->NmsShapedRecipes.wrapper.wrap(r)).collect(Collectors.toList()),(r,m)->
		{
			for(int i=0;i<r.getWidth();i++)
			{
				for(int j=0;j<r.getHeight();j++)
				{
					List<ItemStack> cs=r.getIngredients().get(j*r.getWidth()+i,NmsRecipeItemStack.wrapper).getChoices();
					if(cs.size()>0)
						m.setExtra(cs.get(0),j*9+i+3);
				}
			}
			m.setExtra(ItemStackBuilder.sign().setName(r.getGroup().length()==0?LangUtil.getTranslated(m.owner,"mzlib.recipesEditor.types.shapedRecipe.group.none"):LangUtil.getTranslated(m.owner,"mzlib.recipesEditor.types.shapedRecipe.group").replace("%{group}",r.getGroup())).get(),24);
		},r->
		{
			NmsCraftingManager.removeRecipe(NmsCraftingManager.getCraftingRecipes(),r);
		},nbt->
		{
			if(nbt.containsKey("group"))
			{
				NmsShapedRecipes r=NmsShapedRecipes.newInstance(getNamespacedKey(nbt.getCompound("key")),nbt.getString("group"),nbt.getInt("width"),nbt.getInt("height"),nbt.getList("ingredients").values(NmsNBTTagList.wrapper).stream().map(l->getRecipeItemStack(l)).collect(Collectors.toList()),new ItemStackBuilder(nbt.getCompound("result")).get());
				return r;
			}
			else
			{
				ShapedRecipe r=new ShapedRecipe(getNamespacedKey(nbt.getCompound("key")),new ItemStackBuilder(nbt.getCompound("result")).get());
				r.shape(nbt.getList("shape").values(NmsNBTTagString.wrapper).stream().map(n->n.getValue()).toArray(String[]::new));
				nbt.getCompound("ingredients").getMap().forEach((k,v)->
				{
					NmsNBTTagCompound n=NmsNBTTagCompound.wrapper.wrap(v);
					r.setIngredient(k.charAt(0),ItemStackBuilder.getData(new ItemStackBuilder(n.getString("id")).setChildId(n.getShort("childId")).get()));
				});
				return NmsShapedRecipes.newInstance(r.getKey(),"",1,1,Lists.newArrayList(NmsRecipeItemStack.newInstance(Material.STICK)),new ItemStack(Material.STICK));
			}
		},recipe->
		{
			return NmsNBTTagCompound.newInstance().set("key",saveNamespacedKey(recipe.getKey())).set("result",new ItemStackBuilder(recipe.getResult()).nbt()).set("group",recipe.getGroup()).set("width",recipe.getWidth()).set("height",recipe.getHeight()).set("ingredients",recipe.getIngredients().getRaw().stream().map(i->saveRecipeItemStack(NmsRecipeItemStack.wrapper.wrap(i))).collect(Collectors.toList()));
		},()->NmsShapedRecipes.newInstance(new NamespacedKey(MzLib.instance,UUID.randomUUID().toString()),"",1,1,Lists.newArrayList(NmsRecipeItemStack.newInstance(Material.STICK)),new ItemStack(Material.STICK)),r->NmsCraftingManager.addRecipe(r.getKey0(),r),(r,pl,c)->
		{
			for(int i=0;i<3;i++)
			{
				for(int j=0;j<3;j++)
				{
					c.setSlot(i*9+j+3,NmsSlot.readWrite(c.getSlot(i*9+j+3)));
				}
			}
			for(int i=27;i<c.getSlots().size();i++)
			{
				c.setReadWrite(i);
			}
		},(r,m)->
		{
			for(int i=0;i<3&&i<r.getWidth();i++)
			{
				for(int j=0;j<3&&j<r.getHeight();j++)
				{
					int index=j*3+i,index1=j*r.getWidth()+i;
					NmsRecipeItemStack is=r.getIngredients().get(index1,NmsRecipeItemStack.wrapper);
					m.setButton(j*9+i+3,is.getChoices0().length==0?ItemStackBuilder.air().get():is.getChoice(0),(t,p)->
					{
						m.openChild(new IngredientEditor(m.owner,LangUtil.getTranslated(m.owner,"mzlib.recipesEditor.types.shapedRecipe.slot.title").replace("%{slot}",index+""),is,in->
						{
							r.getIngredients().set(index1,in);
							m.update();
						}));
					});
				}
			}
		},(r,m)->
		{
			if(ItemStackBuilder.isAir(m.getInventory().getItem(10)))
			{
				RecipeEditor.setErrMsg(m,LangUtil.getTranslated(m.getInventory().getViewers().get(0),"mzlib.recipesEditor.error.noresult"));
				return;
			}
			r.setResult(ObcItemStack.asNMSCopy(m.getInventory().getItem(10)));
			RecipeEditor.resetErrMsg(m);
		},(r,k)->WrappedShapedRecipe.wrapper.wrap(r).setKey(k)));
		recipeEditors.add(new RecipeEditor<>(p->ItemStackBuilder.planks().setName(StringUtil.replaceStrings(LangUtil.getTranslated(p,"mzlib.recipesEditor.kindRecipe.title"),new MapEntry<>("%\\{type\\}",LangUtil.getTranslated(p,"mzlib.recipesEditor.types.shapelessRecipe")))).get(),shapelessRecipes(),l->LangUtil.getTranslated(l,"mzlib.recipesEditor.types.shapelessRecipe"),()->new ArrayList<>(NmsCraftingManager.getShapelessRecipes().values()),(r,m)->
		{
			for(int i=0;i<r.getIngredientList().size();i++)
			{
				m.setExtra(r.getIngredientList().get(i),i%3+3+i/3*9);
			}
		},r->
		{
			NmsCraftingManager.removeRecipe(NmsCraftingManager.getCraftingRecipes(),r);
		},nbt->
		{
			ShapelessRecipe r=new ShapelessRecipe(getNamespacedKey(nbt.getCompound("key")),new ItemStackBuilder(nbt.getCompound("result")).get());
			nbt.getList("ingredients").values(NmsNBTTagCompound.wrapper).forEach(n->
			{
				r.addIngredient(ItemStackBuilder.getData(new ItemStackBuilder(n.getString("id")).setChildId(n.getShort("childId")).get()));
			});
			return r;
		},recipe->NmsNBTTagCompound.newInstance().set("key",saveNamespacedKey(recipe.getKey())).set("result",new ItemStackBuilder(recipe.getResult()).nbt()).set("ingredients",NmsNBTTagList.newInstance().addAll(recipe.getIngredientList().stream().map(is->NmsNBTTagCompound.newInstance().set("id",NmsNBTTagString.newInstance(new ItemStackBuilder(is).getId())).set("childId",NmsNBTTagShort.newInstance(is.getDurability()))).collect(Collectors.toList()))),()->new ShapelessRecipe(new NamespacedKey(MzLib.instance,UUID.randomUUID().toString()),new ItemStack(Material.STICK)).addIngredient(Material.STICK),Bukkit::addRecipe,(r,pl,c)->
		{
			for(int i=0;i<3;i++)
			{
				for(int j=0;j<3;j++)
				{
					c.setSlot(i*9+j+3,NmsSlot.readWrite(c.getSlot(i*9+j+3)));
				}
			}
			List<Object> l=c.getSlots();
			for(int i=27;i<l.size();i++)
			{
				l.set(i,NmsSlot.readWrite(NmsSlot.wrapper.wrap(l.get(i))).getRaw());
			}
		},(r,m)->
		{
			for(int i=0;i<r.getIngredientList().size();i++)
			{
				m.setExtra(r.getIngredientList().get(i),i%3+3+i/3*9);
			}
		},(r,m)->
		{
			if(ItemStackBuilder.isAir(m.getInventory().getItem(10)))
			{
				RecipeEditor.setErrMsg(m,LangUtil.getTranslated(m.getInventory().getViewers().get(0),"mzlib.recipesEditor.error.noresult"));
				return;
			}
			ItemStack[] items=new ItemStack[9];
			items[0]=m.getInventory().getItem(3);
			items[1]=m.getInventory().getItem(4);
			items[2]=m.getInventory().getItem(5);
			items[3]=m.getInventory().getItem(12);
			items[4]=m.getInventory().getItem(13);
			items[5]=m.getInventory().getItem(14);
			items[6]=m.getInventory().getItem(21);
			items[7]=m.getInventory().getItem(22);
			items[8]=m.getInventory().getItem(23);
			int k=0;
			for(int i=0;i<9;i++)
			{
				if(ItemStackBuilder.isAir(items[i]))
					k++;
			}
			if(k==9)
			{
				RecipeEditor.setErrMsg(m,LangUtil.getTranslated(m.getInventory().getViewers().get(0),"mzlib.recipesEditor.error.noraw"));
				return;
			}
			NmsCraftingManager.removeRecipe(NmsCraftingManager.getCraftingRecipes(),r.getKey());
			WrappedShapelessRecipe.wrapper.wrap(r).setResult(m.getInventory().getItem(10));
			WrappedShapelessRecipe.wrapper.wrap(r).getIngredients().clear();
			for(int i=0;i<9;i++)
			{
				if(!ItemStackBuilder.isAir(items[i]))
				{
					r.addIngredient(ItemStackBuilder.getData(items[i]));
				}
			}
			RecipeEditor.resetErrMsg(m);
		},(r,k)->WrappedShapelessRecipe.wrapper.wrap(r).setKey(k)));
		recipeEditors.add(new RecipeEditor<KeyedFurnaceRecipe>(l->new ItemStackBuilder(Material.FURNACE).setName(StringUtil.replaceStrings(LangUtil.getTranslated(l,"mzlib.recipesEditor.kindRecipe.title"),new MapEntry<>("%\\{type\\}",LangUtil.getTranslated(l,"mzlib.recipesEditor.types.furnaceRecipe")))).get(),furnaceRecipes(),l->LangUtil.getTranslated(l,"mzlib.recipesEditor.types.furnaceRecipe"),()->NmsCraftingManager.getFurnaceRecipes(),(r,m)->
		{
			m.setExtra(r.recipe.getInput(),13);
		},r->NmsCraftingManager.removeFurnaceRecipe(r),n->
		{
			KeyedFurnaceRecipe r=new KeyedFurnaceRecipe(new ItemStackBuilder(n.getCompound("result")).get(),new ItemStackBuilder(n.getCompound("raw")).get(),n.getFloat("exp"));
			if(BukkitWrapper.v13)
				WrappedFurnaceRecipe.wrapper.wrap(r.recipe).setKeyV13(getNamespacedKey(n.getCompound("key")));
			return r;
		},r->
		{
			return NmsNBTTagCompound.newInstance().set("key",saveNamespacedKey(r.getKey())).set("result",new ItemStackBuilder(r.getResult()).nbt()).set("raw",new ItemStackBuilder(r.recipe.getInput()).nbt()).set("exp",NmsNBTTagFloat.newInstance(r.recipe.getExperience()));
		},()->new KeyedFurnaceRecipe(new ItemStack(Material.STICK),new ItemStack(Material.STICK),0.1f),r->Bukkit.addRecipe(r.recipe),(r,p,c)->
		{
			List<Object> l=c.getSlots();
			l.set(10,NmsSlot.readWrite(NmsSlot.wrapper.wrap(l.get(10))).getRaw());
			l.set(13,NmsSlot.readWrite(NmsSlot.wrapper.wrap(l.get(13))).getRaw());
			for(int i=27;i<l.size();i++)
			{
				l.set(i,NmsSlot.readWrite(NmsSlot.wrapper.wrap(l.get(i))).getRaw());
			}
		},(r,m)->
		{
			m.setExtra(r.recipe.getInput(),13);
			m.setButton(23,ItemStackBuilder.exp_bottle().setName(LangUtil.getTranslated(m.owner,"mzlib.recipesEditor.types.furnaceRecipe.setExp")).setLore(LangUtil.getTranslated(m.owner,"mzlib.recipesEditor.types.furnaceRecipe.setExp.lore").replace("%{exp}",r.recipe.getExperience()+"").split("\\n")).get(),(t,p)->
			{
				m.openChild(new InputBox(m.getPlugin(),(Player)m.owner,LangUtil.getTranslated(m.owner,"mzlib.recipesEditor.types.furnaceRecipe.setExp"),r.recipe.getExperience()+"",in->
				{
					Ref<Float> exp=new Ref<>(null);
					if(TypeUtil.hasThrowable(()->exp.set(Float.valueOf(in.getName())))||exp.get()<0)
					{
						in.getInventory().setItem(2,new ItemStackBuilder(in.getInventory().getItem(2)).setLore(LangUtil.getTranslated(in.owner,"mzlib.recipesEditor.types.furnaceRecipe.setExp.error")).get());
					}
					else
					{
						r.recipe.setExperience(exp.get());
						List<NmsNBTTagCompound> f=fixedRecipes(furnaceRecipes());
						RecipeEditor<?> thiz=getEditor(furnaceRecipes());
						for(int i=0;i<f.size();i++)
						{
							if(thiz.recipeReader.apply(f.get(i)).getKey().equals(r.getKey()))
							{
								f.set(i,thiz.recipeWriter.apply(TypeUtil.cast(r)));
							}
						}
						save();
						in.retAndUpdate();
					}
				}));
			});
		},(r,m)->
		{
			if(ItemStackBuilder.isAir(m.getInventory().getItem(10)))
			{
				RecipeEditor.setErrMsg(m,LangUtil.getTranslated(m.getInventory().getViewers().get(0),"mzlib.recipesEditor.error.noresult"));
				return;
			}
			WrappedFurnaceRecipe.wrapper.wrap(r.recipe).setResult(m.getInventory().getItem(10));
			WrappedFurnaceRecipe.wrapper.wrap(r.recipe).setIngredient(m.getInventory().getItem(13));
			RecipeEditor.resetErrMsg(m);
		},BukkitWrapper.v13?(r,k)->WrappedFurnaceRecipe.wrapper.wrap(r.recipe).setKeyV13(k):null));
		recipeEditors.add(new RecipeEditor<>(l->new ItemStackBuilder(Material.COAL).setName(StringUtil.replaceStrings(LangUtil.getTranslated(l,"mzlib.recipesEditor.kindRecipe.title"),new MapEntry<>("%\\{type\\}",LangUtil.getTranslated(l,"mzlib.recipesEditor.types.fuelRecipe")))).get(),fuelRecipes(),l->LangUtil.getTranslated(l,"mzlib.recipesEditor.types.fuelRecipe"),()->NmsTileEntityFurnace.fuelMap.entrySet().stream().map(e->new FuelRecipe(e.getKey(),e.getValue())).collect(Collectors.toList()),(r,m)->
		{
			m.setExtra(ItemStackBuilder.clock().setName(StringUtil.replaceStrings(LangUtil.getTranslated(m.owner,"mzlib.recipesEditor.types.fuelRecipe.time"),new MapEntry<>("%\\{time\\}",r.time+""))).get(),13);
		},k->
		{
			for(NmsRecipeItemStack i:NmsTileEntityFurnace.fuelMap.keySet())
			{
				if(new FuelRecipe(i,0).getKey().equals(k))
				{
					NmsTileEntityFurnace.fuelMap.remove(i);
					return;
				}
			}
		},nbt->new FuelRecipe(getRecipeItemStack(nbt.getList("ingredient")),nbt.getInt("time")),r->NmsNBTTagCompound.newInstance().set("ingredient",saveRecipeItemStack(r.i)).set("time",NmsNBTTagInt.newInstance(r.time)),()->new FuelRecipe(NmsRecipeItemStack.newInstance(Material.STICK),200),r->NmsTileEntityFurnace.fuelMap.put(r.i,r.time),(r,p,c)->{},(r,m)->
		{
			m.setButton(10,r.getResult(),(t,p)->
			{
				m.openChild(new IngredientEditor(p,LangUtil.getTranslated(p,"mzlib.recipesEditor.types.fuelRecipe.ingredient"),r.i,i->
				{
					r.i=i;
					m.update();
				}));
			});
			m.setButton(13,ItemStackBuilder.clock().setName(StringUtil.replaceStrings(LangUtil.getTranslated(m.owner,"mzlib.recipesEditor.types.fuelRecipe.timeSetter"),new MapEntry<>("%\\{time\\}",r.time+""))).get(),(t,p)->
			{
				m.openChild(new InputBox(MzLib.instance,(Player) p,LangUtil.getTranslated(p,"mzlib.recipesEditor.types.fuelRecipe.timeSetter.title"),r.time+"",i->
				{
					try
					{
						r.time=Integer.valueOf(i.getName());
						i.retAndUpdate();
					}
					catch(NumberFormatException e)
					{
					}
				}));
			});
		},(r,m)->{},null));
		AnvilRecipe.init();
		recipeEditors.add(new RecipeEditor<AnvilRecipe>(l->new ItemStackBuilder(Material.ANVIL).setName(StringUtil.replaceStrings(LangUtil.getTranslated(l,"mzlib.recipesEditor.kindRecipe.title"),new MapEntry<>("%\\{type\\}",LangUtil.getTranslated(l,"mzlib.recipesEditor.types.anvilRecipe")))).get(),anvilRecipes(),l->LangUtil.getTranslated(l,"mzlib.recipesEditor.types.anvilRecipe"),()->new ArrayList<>(AnvilRecipe.recipes.values()),(r,m)->
		{
			m.setExtra(r.ingredient1.getChoices().get(0),12);
			m.setExtra(r.ingredient2.getChoices().get(0),14);
		},k->AnvilRecipe.recipes.remove(k),n->new AnvilRecipe(getNamespacedKey(n.getCompound("key")),new ItemStackBuilder(n.getCompound("result")).get(),getRecipeItemStack(n.getList("ingredient1")),getRecipeItemStack(n.getList("ingredient2")),n.getInt("cost")),r->NmsNBTTagCompound.newInstance().set("key",saveNamespacedKey(r.getKey())).set("result",new ItemStackBuilder(r.getResult()).nbt()).set("ingredient1",saveRecipeItemStack(r.ingredient1)).set("ingredient2",saveRecipeItemStack(r.ingredient2)).set("cost",NmsNBTTagInt.newInstance(r.cost)),()->new AnvilRecipe(new NamespacedKey(MzLib.instance,UUID.randomUUID().toString()),new ItemStack(Material.STICK),NmsRecipeItemStack.newInstance(Material.STICK),NmsRecipeItemStack.newInstance(Material.STICK),1),r->AnvilRecipe.reg(r),(r,pl,c)->
		{
			List<Object> l=c.getSlots();
			l.set(10,NmsSlot.readWrite(NmsSlot.wrapper.wrap(l.get(10))).getRaw());
			for(int i=27;i<l.size();i++)
			{
				l.set(i,NmsSlot.readWrite(NmsSlot.wrapper.wrap(l.get(i))).getRaw());
			}
		},(r,m)->
		{
			m.setButton(12,r.ingredient1.getChoices().get(0),(t,p)->
			{
				m.openChild(new IngredientEditor(p,LangUtil.getTranslated(p,"mzlib.recipesEditor.types.anvilRecipe.ingredient1"),r.ingredient1,i->
				{
					r.ingredient1=i;
					m.update();
				}));
			});
			m.setButton(14,r.ingredient2.getChoices().get(0),(t,p)->
			{
				m.openChild(new IngredientEditor(p,LangUtil.getTranslated(p,"mzlib.recipesEditor.types.anvilRecipe.ingredient2"),r.ingredient2,i->
				{
					r.ingredient2=i;
					m.update();
				}));
			});
		},(r,m)->
		{
			if(ItemStackBuilder.isAir(m.getInventory().getItem(10)))
			{
				RecipeEditor.setErrMsg(m,LangUtil.getTranslated(m.getInventory().getViewers().get(0),"mzlib.recipesEditor.error.noresult"));
				return;
			}
			r.result=m.getInventory().getItem(10);
			RecipeEditor.resetErrMsg(m);
		},(r,k)->r.key=k));
		
		for(RecipeEditor<?> e: recipeEditors)
		{
			for(NamespacedKey r: disabledRecipes(e.recipes))
			{
				e.recipeRemover.accept(r);
			}
			for(NmsNBTTagCompound r: fixedRecipes(e.recipes))
			{
				R recipe=(R) e.recipeReader.apply(r);
				e.recipeRemover.accept(recipe.getKey());
				e.recipeRegister.accept(TypeUtil.cast(recipe));
			}
		}
	}
	public RecipeEditor<?> getEditor(NmsNBTTagCompound recipes)
	{
		for(RecipeEditor<?> e:recipeEditors)
		{
			if(e.recipes.getRaw()==recipes.getRaw())
				return e;
		}
		return null;
	}
	@Override
	public void onDisable()
	{
		if(saveTask!=null&&saveTask.getTaskId()!=-1)
			save(true);
	}
	
	public ModuleData moduleData=new ModuleData(MzLib.instance);
	@Override
	public ModuleData getEnabledRef()
	{
		return moduleData;
	}
	
	@Override
	public Plugin getPlugin()
	{
		return MzLib.instance;
	}
	@CommandHandler(effect="mzlib.command.recipes.effect")
	void execute(Player sender)
	{
		for(Player p: Bukkit.getOnlinePlayers())
		{
			InventoryHolder h=p.getOpenInventory().getTopInventory().getHolder();
			if(h instanceof IUI)
			{
				if(((IUI) h).getFirst() instanceof RecipeTypesMenu)
				{
					MzLib.sendPluginMessage(sender,MzLib.instance,StringUtil.replaceStrings(LangUtil.getTranslated(sender,"mzlib.recipeEditor.opened"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{player\\}",p.getName())))));
					return;
				}
			}
		}
		new RecipeTypesMenu(sender,sender.getLocale()).open();
	}
	
	static class RecipeTypesMenu extends AttributeEditor
	{
		public RecipeTypesMenu(HumanEntity owner,String locale)
		{
			super(MzLib.instance,owner,LangUtil.getTranslated(locale,"mzlib.recipesEditor.types.title"),recipeEditors.stream().map(e->new MapEntry<Function<HumanEntity,ItemStack>,BiConsumer<AttributeEditor,HumanEntity>>(p->e.iconBuilder.apply(LangUtil.getLang(p)),(e1,p)->e1.openChild(e.createMenu(p)))).collect(Collectors.toList()));
		}
		public Plugin getPlugin()
		{
			return MzLib.instance;
		}
	}
}
