package mz.lib.minecraft.bukkitlegacy;

import mz.lib.*;
import mz.lib.minecraft.*;
import mz.lib.minecraft.bukkit.nms.*;
import mz.lib.minecraft.bukkitlegacy.event.SetItemEvent;
import mz.lib.minecraft.bukkitlegacy.event.ShowItemEvent;
import mz.lib.minecraft.bukkitlegacy.itemstack.EnchantedBookBuilder;
import mz.lib.minecraft.bukkit.obc.ObcEnchantment;
import mz.mzlib.*;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.Map.Entry;

public final class EnchantUtil
{
	public static Map<Integer,Enchantment> byId;
	public static Map<String,Enchantment> byName;
	public static Map<NamespacedKey,Enchantment> byKey;
	public static Map<?,Integer> IdMap;
	public static Map<?,?> keyMap;
	public static Map<?,?> resourceKeyMap;
	public static Map<?,?> lifecycleMap;
	public static Object regId;
	public static Map<Map.Entry<Plugin,String>,Enchantment> regEnchants=new HashMap<>();
	public static Map<Integer,String> romanNums=new LinkedHashMap<>();
	static
	{
		try
		{
			byName=WrappedEnchantment.getByName();
			if(Server.instance.v13)
				byKey=WrappedEnchantment.getByKeyV13();
			else
				byId=WrappedEnchantment.getByIdV_13();
			if(Server.instance.v13)
			{
				try
				{
					keyMap=NmsIRegistry.getEnchantsV13().cast(NmsRegistryMaterials.class).getKeyMapV13();
					resourceKeyMap=NmsIRegistry.getEnchantsV13().cast(NmsRegistryMaterials.class).getResourceKeyMapV16();
					lifecycleMap=NmsIRegistry.getEnchantsV13().cast(NmsRegistryMaterials.class).getLifecycleMapV16();
					IdMap=NmsIRegistry.getEnchantsV13().cast(NmsRegistryMaterials.class).getIdMapV16();
				}
				catch(Throwable e)
				{
					try
					{
						regId=NmsIRegistry.getEnchantsV13().cast(NmsRegistryMaterials.class).getRegIDV_15();
					}
					catch(Throwable e1)
					{
						throw e;
					}
				}
			}
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	static
	{
		romanNums.put(1000,"M");
		romanNums.put(900,"CM");
		romanNums.put(500,"D");
		romanNums.put(400,"CD");
		romanNums.put(100,"C");
		romanNums.put(90,"XC");
		romanNums.put(50,"L");
		romanNums.put(40,"XL");
		romanNums.put(10,"X");
		romanNums.put(9,"IX");
		romanNums.put(5,"V");
		romanNums.put(4,"IV");
		romanNums.put(1,"I");
	}
	public @Deprecated
	EnchantUtil()
	{
	}
//	public static void regEnchant(Plugin plugin,int id,String name,int maxLevel,EnchantmentTarget target,boolean isTreasure,boolean isCursed)
//	{
//		Enchantment enchant;
//		if(BukkitWrapper.v13)
//		{
//			try
//			{
//				String className=EnchantUtil.class.getName()+"$"+UUID.randomUUID().toString().replace("-","");
//				CtClass ct=ClassPool.getDefault().makeClass(className);
//				ct.setSuperclass(ct.getClassPool().get(WrappedObject.getRawClass(NmsEnchantment.class).getName()));
//				CtConstructor ctc=new CtConstructor(new CtClass[0],ct);
//				ctc.setBody(("super(null,EnchantmentSlotType."+TypeUtil.<Enum<?>,Object>cast((target==EnchantmentTarget.ARMOR?NmsEnchantmentSlotType.getArmor():target==EnchantmentTarget.ARMOR_FEET?NmsEnchantmentSlotType.getArmorFeet():target==EnchantmentTarget.ARMOR_LEGS?NmsEnchantmentSlotType.getArmorLegs():target==EnchantmentTarget.ARMOR_TORSO?NmsEnchantmentSlotType.getArmorChest():target==EnchantmentTarget.ARMOR_HEAD?NmsEnchantmentSlotType.getArmorHead():target==EnchantmentTarget.BOW?NmsEnchantmentSlotType.getBow():target==EnchantmentTarget.FISHING_ROD?NmsEnchantmentSlotType.getFishingRod():target==EnchantmentTarget.WEAPON?NmsEnchantmentSlotType.getWeapon():NmsEnchantmentSlotType.getBreakable()).getRaw()).name()+");").replace("EnchantmentSlotType",WrappedObject.getRawClass(NmsEnchantmentSlotType.class).getName()));
//				ct.addConstructor(ctc);
//				CtMethod cm=new CtMethod(ClassPool.getDefault().get("I"),"getMaxLevel",new CtClass[0],ct);
//				cm.setBody("return "+maxLevel+";");
//				ct.addMethod(cm);
//				ClassUtil.loadClass(className,ct.toBytecode(),EnchantUtil.class.getClassLoader());
//				Object nms=Class.forName(className).newInstance();
//				NmsIRegistry.getEnchantsV13().cast(NmsIRegistryWritableV13.class).add(id,NmsResourceKey.fromKeyV13(NmsMinecraftKey.newInstance(new NamespacedKey(plugin,name))),WrappedObject.wrap(WrappedObject.class,nms),Lifecycle.stable());
//				className=EnchantUtil.class.getName()+"$"+UUID.randomUUID().toString().replace("-","");
//				ct=ClassPool.getDefault().makeClass(className);
//				ct.setSuperclass(ClassPool.getDefault().get(WrappedObject.getRawClass(ObcEnchantment.class).getName()));
//				ct.getDeclaredMethod("getMaxLevel").setBody("return "+maxLevel+";");
//				ct.getDeclaredMethod("getStartLevel").setBody("return 1;");
//				ct.getDeclaredMethod("getItemTarget").setBody("return EnchantmentTarget."+target.name()+";".replace("EnchantmentTarget",EnchantmentTarget.class.getName()));
//				ct.getDeclaredMethod("isTreasure").setBody("return "+isTreasure+";");
//				ct.getDeclaredMethod("isCursed").setBody("return "+isCursed+";");
//				ct.getDeclaredMethod("canEnchantItem").setBody("return $0.getItemTarget().includes($1);");
//				ct.getDeclaredMethod("getName").setBody("return "+name+";");
//				ct.getDeclaredMethod("conflictsWith").setBody("return false;");
//				ClassUtil.loadClass(className,ct.toBytecode(),EnchantUtil.class.getClassLoader());
//				enchant=(Enchantment) Class.forName(className).getDeclaredConstructor(WrappedObject.getRawClass(NmsEnchantment.class)).newInstance(nms);
//			}
//			catch(Throwable e)
//			{
//				throw TypeUtil.throwException(e);
//			}
//		}
//		else
//			enchant=new Enchantment(id)
//			{
//				@Override
//				public int getMaxLevel()
//				{
//					return maxLevel;
//				}
//				@Override
//				public int getStartLevel()
//				{
//					return 1;
//				}
//				@Override
//				public EnchantmentTarget getItemTarget()
//				{
//					return target;
//				}
//				@Override
//				public boolean isTreasure()
//				{
//					return isTreasure;
//				}
//				@Override
//				public boolean isCursed()
//				{
//					return isCursed;
//				}
//				@Override
//				public boolean canEnchantItem(ItemStack item)
//				{
//					return target.includes(item);
//				}
//				@Override
//				public String getName()
//				{
//					return name;
//				}
//				@Override
//				public boolean conflictsWith(Enchantment paramEnchantment)
//				{
//					return false;
//				}
//			};
//		regEnchants.put(new MapEntry<>(plugin,name),enchant);
//		if(byId!=null)
//			byId.put(id,enchant);
//		if(byName!=null)
//			byName.put(name,enchant);
//		if(byKey!=null)
//			byKey.put(new NamespacedKey(plugin,name),enchant);
//	}
	public static String getEnchantId(Enchantment enchant)
	{
		return getEnchantKey(enchant).toString();
	}
	public static NamespacedKey getEnchantKey(Enchantment enchant)
	{
		if(enchant instanceof EnchantmentWrapper)
			enchant=((EnchantmentWrapper) enchant).getEnchantment();
		return getEnchantKey0(enchant);
	}
	public static NamespacedKey getEnchantKey0(Enchantment enchant)
	{
		if(WrappedObject.getRawClass(ObcEnchantment.class).isAssignableFrom(enchant.getClass()))
		{
			NmsEnchantment nms=WrappedObject.wrap(ObcEnchantment.class,enchant).getHandle();
			if(nms.getRaw()!=null)
			{
				if(Server.instance.v13)
					return NmsIRegistry.getEnchantsV13().getKey(nms).toBukkit();
				else
					return NmsEnchantment.getEnchantsV_13().getKey(nms.cast(NmsEnchantment.class)).toBukkit();
			}
		}
		return NmsMinecraftKey.newInstance((enchant.getName().trim().isEmpty()?enchant.getClass().getSimpleName():enchant.getName()).toLowerCase()).toBukkit();
	}
	public static Enchantment getEnchant(NamespacedKey key)
	{
		return byKey.get(key);
	}
	public static Enchantment getEnchant(String id)
	{
		return byName.get(id);
	}
	public static Enchantment getEnchant(int id)
	{
		return byId.get(id);
	}
	@SuppressWarnings("deprecation")
	public static void unregEnchant(Plugin plugin,String name)
	{
		Enchantment enchant=regEnchants.get(new MapEntry<>(plugin,name));
		regEnchants.remove(new MapEntry<>(plugin,name));
		byName.remove(enchant.getName());
		if(byId!=null)
			byId.remove(enchant.getId());
		if(byKey!=null)
			try
			{
				byKey.remove(WrappedObject.wrap(WrappedEnchantment.class,enchant).getKeyV13());
			}
			catch(Throwable e)
			{
				throw TypeUtil.throwException(e);
			}
		if(Server.instance.v13)
		{
			Object nms=getNms(new NamespacedKey(plugin,name));
			if(IdMap!=null)
				IdMap.remove(nms);
			NmsMinecraftKey key=NmsMinecraftKey.newInstance(plugin.getName().toLowerCase(),name.toLowerCase());
			keyMap.remove(key.getRaw());
			if(resourceKeyMap!=null)
				resourceKeyMap.remove(NmsResourceKey.fromKeyV13(key).getRaw());
			if(lifecycleMap!=null)
				lifecycleMap.remove(nms);
		}
	}
	public static Object getNms(NamespacedKey key)
	{
		if(key==null)
			return null;
		if(Server.instance.v13)
			return NmsIRegistry.getEnchantsV13().cast(NmsIRegistry.class).get(NmsMinecraftKey.newInstance(key)).getRaw();
		else
			return NmsEnchantment.getEnchantsV_13().cast(NmsRegistrySimpleV_13.class).get(NmsMinecraftKey.newInstance(key)).getRaw();
	}
	public static Object getNms(short id)
	{
		if(Server.instance.v13)
			return NmsIRegistry.getEnchantsV13().cast(NmsRegistryMaterials.class).fromId(id);
		else
			return NmsEnchantment.getEnchantsV_13().fromId(id).getRaw();
	}
	public static String getTranslateKey(NamespacedKey key)
	{
		Object nms=getNms(key);
		if(nms!=null)
			return getTranslateKey(nms);
		else
			return "enchantment."+key.getNamespace()+'.'+key.getKey();
	}
	public static String getTranslateKey(Object nmsEnchant)
	{
		if(nmsEnchant==null)
			return "enchantment.null";
		return WrappedObject.wrap(NmsEnchantment.class,nmsEnchant).getTranslateKey();
	}
	public static String getRomanNum(int level)
	{
		StringBuilder sb1000=new StringBuilder();
		StringBuilder sb=new StringBuilder();
		if(level>4999)
		{
			for(Entry<Integer,String> e: romanNums.entrySet())
			{
				if(e.getKey()<5)
					break;
				while(e.getKey()*1000<=level)
				{
					level-=e.getKey()*1000;
					sb1000.append(e.getValue());
				}
			}
		}
		for(Entry<Integer,String> e: romanNums.entrySet())
		{
			while(e.getKey()<=level)
			{
				level-=e.getKey();
				sb.append(e.getValue());
			}
		}
		if(sb1000.length()>0)
			return '('+sb1000.toString()+')'+sb;
		else
			return sb.toString();
	}
	public static String getEnchantLevel(int level)
	{
		return level<1 || level>MzLib.instance.getConfig().getInt("func.showEnchant.RomanNumLimit",50)?Integer.valueOf(level).toString():getRomanNum(level);
	}
	public static boolean filterProxyEnchant(Enchantment enchant)
	{
		if(enchant==null)
			return false;
//		if(ListUtil.containsAny(ClassUtil.getSuperClasses(enchant.getClass()).stream().map(Class::getName).collect(Collectors.toList()),MzLib.instance.getConfig().getStringList("func.showEnchant.ignore")))
//			return false;
		return true;
	}
	public static void init()
	{
		Bukkit.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler(priority=EventPriority.MONITOR)
			void onPluginDisable(PluginDisableEvent event)
			{
				new HashMap<>(regEnchants).forEach((ent,enc)->
				{
					if(ent.getKey()==event.getPlugin())
						unregEnchant(ent.getKey(),ent.getValue());
				});
			}
			@SuppressWarnings("deprecation")
			@EventHandler
			void onShowItem(ShowItemEvent event)
			{
				if(event.item.hasEnchant()&&!event.item.isEnchantsHide())
				{
					event.item.setHideEnchants(true);
					if(Server.instance.v13)
					{
						List<NmsNBTTagCompound> enchants=event.item.tag().getList("Enchantments").values(NmsNBTTagCompound.class);
						int[] size={0};
						List<String> lore=new ArrayList<>(enchants.size());
						enchants.forEach(e->
						{
							String[] key=e.getString("id").split(":");
							NamespacedKey nKey=key.length>1?new NamespacedKey(key[0],key[1]):new NamespacedKey("minecraft",key[0]);
							Enchantment enchant=EnchantUtil.getEnchant(nKey);
							if(!filterProxyEnchant(enchant))
								return;
							int lvl=e.getShort("lvl");
							lore.add((enchant.isCursed()?"§c":"§7")+MinecraftLanguages.get(event.getLocale(),getTranslateKey(nKey))+(enchant.getMaxLevel()==1&&lvl==1?"":(" "+getEnchantLevel(lvl))));
							size[0]++;
						});
						event.item.tag().set("EnchantsFix",NmsNBTTagInt.newInstance(size[0]));
						lore.addAll(event.item.getLore());
						event.item.setLore(lore);
					}
					else
					{
						List<NmsNBTTagCompound> enchants=event.item.tag().getList("ench").values(NmsNBTTagCompound.class);
						List<String> lore=new ArrayList<>(enchants.size());
						int[] size={0};
						enchants.forEach(e->
						{
							short id=e.getShort("id");
							Enchantment enchant=EnchantUtil.getEnchant(id);
							if(!filterProxyEnchant(enchant))
								return;
							short lvl=e.getShort("lvl");
							lore.add((enchant.isCursed()?"§c":"§7")+MinecraftLanguages.get(event.getLocale(),getTranslateKey(getNms(id)))+(enchant.getMaxLevel()==1&&lvl==1?"":(" "+getEnchantLevel(lvl))));
							size[0]++;
						});
						event.item.tag().set("EnchantsFix",NmsNBTTagInt.newInstance(size[0]));
						lore.addAll(event.item.getLore());
						event.item.setLore(lore);
					}
				}
				if(event.item.get().getType()==Material.ENCHANTED_BOOK)
				{
					EnchantedBookBuilder eb=new EnchantedBookBuilder(event.item);
					if(eb.hasStoredEnchant()&&!eb.isStoredEnchantsHide())
					{
						List<NmsNBTTagCompound> enchants=eb.tag().getList("StoredEnchantments").values(NmsNBTTagCompound.class);
						List<String> lore=new ArrayList<>(enchants.size());
						event.item.tag().set("StoredEnchantsFix",NmsNBTTagInt.newInstance(enchants.size()));
						if(Server.instance.v13)
						{
							enchants.forEach(e->
							{
								String id=e.getString("id");
								String[] key=id.split(":");
								NamespacedKey nKey=key.length>1?new NamespacedKey(key[0],key[1]):new NamespacedKey("minecraft",key[0]);
								Enchantment enchant=EnchantUtil.getEnchant(nKey);
								if(enchant==null)
									return;
								short lvl=e.getShort("lvl");
								lore.add((enchant.isCursed()?"§c":"§7")+MinecraftLanguages.get(event.getLocale(),getTranslateKey(nKey))+(enchant.getMaxLevel()==1&&lvl==1?"":(" "+getEnchantLevel(lvl))));
							});
						}
						else
						{
							enchants.forEach(e->
							{
								short id=e.getShort("id");
								Enchantment enchant=EnchantUtil.getEnchant(id);
								if(enchant==null)
									return;
								short lvl=e.getShort("lvl");
								lore.add((enchant.isCursed()?"§c":"§7")+MinecraftLanguages.get(event.getLocale(),getTranslateKey(getNms(id)))+(enchant.getMaxLevel()==1&&lvl==1?"":(" "+getEnchantLevel(lvl))));
							});
						}
						lore.addAll(eb.getLore());
						eb.setLore(lore);
						eb.setHideStoredEnchants(true);
					}
				}
			}
			@EventHandler
			void onSetItem(SetItemEvent event)
			{
				if(event.item.tag().containsKey("EnchantsFix"))
				{
					event.item.setHideEnchants(false);
					List<String> lore=event.item.getLore();
					event.item.setLore(lore.subList(event.item.tag().getInt("EnchantsFix"),lore.size()));
					event.item.tag().remove("EnchantsFix");
				}
				if(event.item.tag().containsKey("StoredEnchantsFix"))
				{
					new EnchantedBookBuilder(event.item).setHideStoredEnchants(false);
					List<String> lore=event.item.getLore();
					event.item.setLore(lore.subList(event.item.tag().getInt("StoredEnchantsFix"),lore.size()));
					event.item.tag().remove("StoredEnchantsFix");
				}
			}
		},MzLib.instance);
	}
}
