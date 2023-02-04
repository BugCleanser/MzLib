package mz.lib.minecraft.bukkit.recipeold;

import com.google.common.collect.Lists;
import mz.lib.MapEntry;
import mz.lib.StringUtil;
import mz.lib.minecraft.bukkit.LangUtil;
import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.inventory.*;
import mz.lib.minecraft.bukkit.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkit.module.IModule;
import mz.lib.minecraft.bukkit.wrappednms.*;
import org.apache.logging.log4j.util.TriConsumer;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class RecipeEditor<T extends Recipe & Keyed>
{
	public Function<String,ItemStack> iconBuilder;
	public NmsNBTTagCompound recipes;
	public Function<Player,NmsIChatBaseComponent> typeNameGetter;
	public Supplier<List<T>> activeRecipesGetter;
	public BiConsumer<T,Menu> activeRecipeVisitorDecorator;
	public Consumer<NamespacedKey> recipeRemover;
	public Function<NmsNBTTagCompound,T> recipeReader;
	public Function<T,NmsNBTTagCompound> recipeWriter;
	public Supplier<T> recipeBuilder;
	public Consumer<T> recipeRegister;
	public TriConsumer<T,HumanEntity,NmsContainer> fixedRecipeEditorSlotSetter;
	public BiConsumer<T,Menu> fixedRecipeEditorDecorator;
	public BiConsumer<T,Menu> recipeReporter;
	public BiConsumer<T,NamespacedKey> keySetter;
	
	public RecipeEditor(Function<String,ItemStack> iconBuilder,NmsNBTTagCompound recipes,Function<Player,NmsIChatBaseComponent> typeNameGetter,Supplier<List<T>> activeRecipesGetter,BiConsumer<T,Menu> activeRecipeVisitorDecorator,Consumer<NamespacedKey> recipeRemover,Function<NmsNBTTagCompound,T> recipeReader,Function<T,NmsNBTTagCompound> recipeWriter,Supplier<T> recipeBuilder,Consumer<T> recipeRegister,TriConsumer<T,HumanEntity,NmsContainer> fixedRecipeEditorSlotSetter,BiConsumer<T,Menu> fixedRecipeEditorDecorator,BiConsumer<T,Menu> recipeReporter,BiConsumer<T,NamespacedKey> keySetter)
	{
		this.iconBuilder=iconBuilder;
		this.recipes=recipes;
		this.typeNameGetter=typeNameGetter;
		this.activeRecipesGetter=activeRecipesGetter;
		this.activeRecipeVisitorDecorator=activeRecipeVisitorDecorator;
		this.recipeRemover=recipeRemover;
		this.recipeReader=recipeReader;
		this.recipeWriter=recipeWriter;
		this.recipeBuilder=recipeBuilder;
		this.recipeRegister=recipeRegister;
		this.fixedRecipeEditorSlotSetter=fixedRecipeEditorSlotSetter;
		this.fixedRecipeEditorDecorator=fixedRecipeEditorDecorator;
		this.recipeReporter=recipeReporter;
		this.keySetter=keySetter;
	}
	public static void setErrMsg(Menu recipeEditMenu,String errMsg)
	{
		recipeEditMenu.setExtra(new ItemStackBuilder(recipeEditMenu.getInventory().getItem(16)).setLore(errMsg).get(),16);
	}
	public static void resetErrMsg(Menu recipeEditMenu)
	{
		recipeEditMenu.setExtra(new ItemStackBuilder(recipeEditMenu.getInventory().getItem(16)).setLore().get(),16);
	}
	public static boolean hasErrMsg(Menu recipeEditMenu)
	{
		return !new ItemStackBuilder(recipeEditMenu.getInventory().getItem(16)).getLore().isEmpty();
	}
	public AttributeEditor createMenu(IModule module)
	{
		AttributeEditor r=new AttributeEditor(module,"",Lists.newArrayList(new MapEntry<>(p->new ItemStackBuilder(Material.SLIME_BALL).setName(LangUtil.getTranslated(p,"mzlib.recipesEditor.types.activeRecipe")).get(),(e,p)->
		{
			e.openChild(new ListVisitor<>(module,LangUtil.getTranslated(p,"mzlib.recipesEditor.types.activeRecipe"),activeRecipesGetter.get(),re->new ItemStackBuilder(re.getResult()).addLore(StringUtil.replaceStrings(LangUtil.getTranslated(LangUtil.getLang(player),"mzlib.recipesEditor.kindRecipe.id"),new MapEntry<>("%\\{id\\}",r.getKey().toString()))).get(),(l,r,c)->
			{
				l.openChild(new Menu(p,27,StringUtil.replaceStrings(LangUtil.getTranslated(p,"mzlib.recipesEditor.recipe.title"),new MapEntry<>("%\\{id\\}",r.getKey().toString())))
				{
					public Plugin getPlugin()
					{
						return MzLib.instance;
					}
					
					public void update()
					{
						super.update();
						this.setButton(0,l.returnButtonIcon,(t,p)->this.ret(p));
						this.setExtra(r.getResult(),10);
						this.setButton(16,new ItemStackBuilder(Material.BARRIER).setName(LangUtil.getTranslated(p,"mzlib.recipesEditor.disable")).get(),(t,p)->
						{
							recipeRemover.accept(r.getKey());
							RecipesEditor.disableRecipe(recipes,r.getKey());
							c.accept(null);
							this.ret(p);
						});
						activeRecipeVisitorDecorator.accept(r,this);
					}
				});
			}));
		}),new MapEntry<>(p->new ItemStackBuilder("chest_minecart").setName(LangUtil.getTranslated(p,"mzlib.recipesEditor.types.fixedRecipe")).get(),(e,p)->
		{
			e.openChild(new ListEditor<>(MzLib.instance,p,LangUtil.getTranslated(p,"mzlib.recipesEditor.types.fixedRecipe"),RecipesEditor.fixedRecipes(recipes).stream().map(recipeReader).collect(Collectors.toList()),r->new ItemStackBuilder(r.getResult()).addLore(StringUtil.replaceStrings(LangUtil.getTranslated(player,"mzlib.recipesEditor.kindRecipe.id"),new MapEntry<>("%\\{id\\}",r.getKey().toString()))).get(),(l,r,c)->
			{
				T n=recipeReader.apply(recipeWriter.apply(r));
				l.openChild(new Menu(p,27,StringUtil.replaceStrings(LangUtil.getTranslated(p,"mzlib.recipesEditor.recipe.title"),new MapEntry<>("%\\{id\\}",r.getKey().toString())))
				{
					public Plugin getPlugin()
					{
						return MzLib.instance;
					}
					
					public void setSlots(HumanEntity player,NmsContainer container)
					{
						super.setSlots(player,container);
						container.setSlot(10,NmsSlot.readWrite(container.getSlot(10)));
						fixedRecipeEditorSlotSetter.accept(n,player,container);
					}
					
					public void update()
					{
						super.update();
						this.setButton(0,l.returnButtonIcon,(t,p)->this.ret(p));
						this.setExtra(n.getResult(),10);
						this.setButton(16,ItemStackBuilder.checkmark().setName(LangUtil.getTranslated(p,"mzlib.recipesEditor.fixedRecipe.save")).get(),(t,p)->
						{
							recipeReporter.accept(n,this);
							if(hasErrMsg(this))
								return;
							recipeRemover.accept(r.getKey());
							c.accept(n);
							this.ret(p);
						});
						if(keySetter!=null)
							this.setButton(8,new ItemStackBuilder(Material.NAME_TAG).setName(LangUtil.getTranslated(p,"mzlib.recipesEditor.fixedRecipe.setKey")).get(),(t,p)->
							{
								this.openChild(new InputBox(MzLib.instance,(Player) p,LangUtil.getTranslated(p,"mzlib.recipesEditor.fixedRecipe.setKey"),n.getKey().toString(),in->
								{
									NamespacedKey key;
									try
									{
										key=NmsMinecraftKey.newInstance(in.getName()).toBukkit();
									}
									catch(Throwable e)
									{
										in.getInventory().setItem(2,new ItemStackBuilder(in.getInventory().getItem(2)).setLore(LangUtil.getTranslated(p,"mzlib.recipesEditor.fixedRecipe.setKey.error")).get());
										return;
									}
									keySetter.accept(n,key);
									in.ret();
								}));
							});
						fixedRecipeEditorDecorator.accept(n,this);
					}
				});
			},l->
			{
				return recipeBuilder.get();
			},(i,r)->
			{
				recipeRemover.accept(r.getKey());
				RecipesEditor.fixedRecipes(recipes).remove((int) i);
				RecipesEditor.save();
			},(i,r)->
			{
				recipeRegister.accept(r);
				RecipesEditor.fixedRecipes(recipes).add(i,recipeWriter.apply(r));
				RecipesEditor.save();
			}));
		}),new MapEntry<>(p->new ItemStackBuilder(Material.BARRIER).setName(LangUtil.getTranslated(p,"mzlib.recipesEditor.types.disabledRecipe")).get(),(e,p)->
		{
			e.openChild(new ListVisitor<>(MzLib.instance,p,LangUtil.getTranslated(p,"mzlib.recipesEditor.types.disabledRecipe"),RecipesEditor.disabledRecipes(recipes),r->ItemStackBuilder.forFlattening("sign",0,"oak_sign").setName(StringUtil.replaceStrings(LangUtil.getTranslated(LangUtil.getLang(player),"mzlib.recipesEditor.kindRecipe.id"),new MapEntry<>("%\\{id\\}",r.toString()))).addLore(LangUtil.getTranslated(p,"mzlib.recipesEditor.disabledRecipe.remove")).get(),(v,r,c)->
			{
				c.accept(null);
				RecipesEditor.save();
			}));
		})));
		r.titleModifier=(p,t)->t.set(typeNameGetter.apply(p));
		return r;
	}
}
