package mz.lib.minecraft.bukkit.nms;

import mz.lib.Optional;
import mz.lib.TypeUtil;
import mz.lib.minecraft.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.WrappedObject;
import mz.lib.*;
import mz.lib.wrapper.*;
import org.bukkit.NamespacedKey;

import java.util.*;

@VersionalWrappedClass({@VersionalName(value="nms.CraftingManager",maxVer=17),@VersionalName(value="net.minecraft.world.item.crafting.CraftingManager",minVer=17)})
public interface NmsCraftingManager extends VersionalWrappedObject
{
	static Map<Object,Object> getRecipesV_13()
	{
		return WrappedObject.wrap(NmsCraftingManager.class,null).getRecipes0V_13().cast(NmsRegistrySimpleV_13.class).getMap();
	}
	static Map<Object,Object> getCraftingRecipes()
	{
		if(Server.instance.version>=14)
			return NmsMinecraftServer.getServer().getCraftingManagerV13().getRecipesV14().get(NmsRecipesV14.crafting());
		else if(Server.instance.v13)
			return NmsMinecraftServer.getServer().getCraftingManagerV13().getRecipes0V13_14().getRaw();
		else
			return getRecipesV_13();
	}
	@VersionalWrappedMethod(@VersionalName(maxVer=13, value="#0"))
	void staticAddRecipeV_13(NmsMinecraftKey key,NmsIRecipe recipe);
	static void addRecipeV_13(NmsMinecraftKey key,NmsIRecipe recipe)
	{
		WrappedObject.wrap(NmsCraftingManager.class,null).staticAddRecipeV_13(key,recipe);
	}
	@VersionalWrappedMethod({@VersionalName(minVer=13, value="addRecipe"),@VersionalName(value="@0",minVer=13,maxVer=14)})
	void addRecipe0V13(NmsIRecipe recipe);
	static void addRecipe(NmsMinecraftKey key,NmsIRecipe recipe)
	{
		if(Server.instance.v13)
			NmsMinecraftServer.getServer().getCraftingManagerV13().addRecipe0V13(recipe);
		else
			addRecipeV_13(key,recipe);
	}
//	static List<KeyedFurnaceRecipe> getFurnaceRecipes()
//	{
//		if(BukkitWrapper.v13)
//			return getSmeltingRecipesV13().entrySet().stream().map(e->WrappedObject.wrap(NmsFurnaceRecipeV13.class,e.getValue())).map(r->new KeyedFurnaceRecipe((FurnaceRecipe) r.toBukkitRecipe())).collect(Collectors.toList());
//		else
//			return NmsRecipesFurnaceV_13.getBukkitRecipes();
//	}
//	static void removeFurnaceRecipe(NamespacedKey key)
//	{
//		if(BukkitWrapper.v13)
//			removeRecipe(getSmeltingRecipesV13(),key);
//		else
//			getFurnaceRecipes().forEach(r->
//			{
//				if(r.getKey().equals(key))
//					NmsRecipesFurnaceV_13.unregRecipe(r.recipe.getInput());
//			});
//	}
	static Map<Object,Object> getBlastingRecipesV13()
	{
		if(Server.instance.v13)
			return NmsMinecraftServer.getServer().getCraftingManagerV13().getRecipesV14().get(NmsRecipesV14.blasting());
		else
			return getRecipesV_13();
	}
	static Map<Object,Object> getCampfireCookingRecipesV13()
	{
		if(Server.instance.v13)
			return NmsMinecraftServer.getServer().getCraftingManagerV13().getRecipesV14().get(NmsRecipesV14.campfireCooking());
		else
			return getRecipesV_13();
	}
	static Map<Object,Object> getSmeltingRecipesV13()
	{
		return NmsMinecraftServer.getServer().getCraftingManagerV13().getRecipesV14().get(NmsRecipesV14.smelting());
	}
	static Map<Object,Object> getSmithingRecipesV16()
	{
		return NmsMinecraftServer.getServer().getCraftingManagerV13().getRecipesV14().get(NmsRecipesV14.smithingV16());
	}
	static Map<Object,Object> getSmokingRecipesV13()
	{
		if(Server.instance.v13)
			return NmsMinecraftServer.getServer().getCraftingManagerV13().getRecipesV14().get(NmsRecipesV14.smoking());
		else
			return getRecipesV_13();
	}
	static Map<Object,Object> getStonecuttingRecipesV13()
	{
		if(Server.instance.v13)
			return NmsMinecraftServer.getServer().getCraftingManagerV13().getRecipesV14().get(NmsRecipesV14.stonecutting());
		else
			return getRecipesV_13();
	}
	static void removeRecipe(Map<Object,Object> recipes,NamespacedKey key)
	{
		Object r=recipes.remove(NmsMinecraftKey.newInstance(key).getRaw());
		if(Server.instance.version>=18)
			NmsMinecraftServer.getServer().getCraftingManagerV13().getRecipesV18().remove(NmsMinecraftKey.newInstance(key).getRaw());
		try
		{
			NmsMinecraftServer.getServer().getCraftingManagerV13().getRecipesCache().remove(r);
		}
		catch(AbstractMethodError e)
		{
		}
		if(!Server.instance.v13)
		{
			NmsRegistryID regId=WrappedObject.wrap(NmsCraftingManager.class,null).getRecipes0V_13().getRegIDV_15();
			int i=regId.getIdV_13(r);
			regId.getBV_13()[i]=null;
			regId.getCV_13()[i]=0;
			regId.getDV_13()[regId.getIdV_13(r)]=null;
		}
	}
	@Optional
	@VersionalWrappedFieldAccessor(@VersionalName("ALL_RECIPES_CACHE"))
	Collection<Object> getRecipesCache();
	@VersionalWrappedFieldAccessor(@VersionalName(minVer=18, value="d"))
	Map<Object,Object> getRecipesV18();
	@VersionalWrappedFieldAccessor(@VersionalName(maxVer=13, value="recipes"))
	NmsRegistryMaterials getRecipes0V_13();
	@VersionalWrappedFieldAccessor(@VersionalName(minVer=13,value="recipes",maxVer=14))
	WrappedObject2ObjectLinkedOpenHashMapV13 getRecipes0V13_14();
	@VersionalWrappedFieldAccessor({@VersionalName(minVer=14,value="recipes"),@VersionalName(minVer=17, value="@0")})
	Map<Object,Map<Object,Object>> getRecipes0V14();
	
	default Map<NmsRecipesV14,Map<Object,Object>> getRecipesV14()
	{
		Map<Object,Map<Object,Object>> raw=getRecipes0V14();
		return new AbstractMap<NmsRecipesV14,Map<Object,Object>>()
		{
			@Override
			public Set<Entry<NmsRecipesV14,Map<Object,Object>>> entrySet()
			{
				Set<Entry<Object,Map<Object,Object>>> r=TypeUtil.cast(raw.entrySet());
				return new AbstractSet<Map.Entry<NmsRecipesV14,Map<Object,Object>>>()
				{
					@Override
					public Iterator<Entry<NmsRecipesV14,Map<Object,Object>>> iterator()
					{
						Iterator<Entry<Object,Map<Object,Object>>> i=r.iterator();
						return new Iterator<Map.Entry<NmsRecipesV14,Map<Object,Object>>>()
						{
							@Override
							public boolean hasNext()
							{
								return i.hasNext();
							}
							@Override
							public Entry<NmsRecipesV14,Map<Object,Object>> next()
							{
								Entry<?,Map<Object,Object>> e=i.next();
								if(e==null)
									return null;
								return new Entry<NmsRecipesV14,Map<Object,Object>>()
								{
									@Override
									public NmsRecipesV14 getKey()
									{
										return WrappedObject.wrap(NmsRecipesV14.class,e.getKey());
									}
									@Override
									public Map<Object,Object> getValue()
									{
										return e.getValue();
									}
									@Override
									public Map<Object,Object> setValue(Map<Object,Object> value)
									{
										return e.setValue(value);
									}
								};
							}
						};
					}
					@Override
					public int size()
					{
						return r.size();
					}
				};
			}
			@Override
			public Map<Object,Object> put(NmsRecipesV14 key,Map<Object,Object> value)
			{
				return raw.put(key.getRaw(),value);
			}
		};
	}
}
