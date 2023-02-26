package mz.lib.minecraft.bukkitlegacy.itemstack;

import com.google.common.collect.Lists;
import com.google.gson.*;
import com.google.gson.stream.*;
import mz.lib.ListUtil;
import mz.lib.MapEntry;
import mz.lib.StringUtil;
import mz.lib.TypeUtil;
import mz.lib.minecraft.*;
import mz.lib.minecraft.bukkit.nms.*;
import mz.lib.minecraft.bukkitlegacy.EnchantUtil;
import mz.lib.minecraft.bukkitlegacy.LangUtil;
import mz.lib.minecraft.bukkitlegacy.event.ShowItemEvent;
import mz.lib.minecraft.bukkitlegacy.message.*;
import mz.lib.minecraft.message.*;
import mz.mzlib.*;
import mz.lib.minecraft.bukkit.nms.NmsIChatBaseComponent.NmsChatSerializer;
import mz.lib.minecraft.bukkit.obc.ObcChatMessage;
import mz.lib.minecraft.bukkit.obc.ObcItemStack;
import mz.lib.minecraft.bukkit.obc.ObcMagicNumbers;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ItemStackBuilder implements Supplier<ItemStack>
{
	public ObcItemStack item;
	public ItemStackBuilder(ObcItemStack obc)
	{
		item=obc;
	}
	public ItemStackBuilder(ItemStack is)
	{
		this(ObcItemStack.ensure(is));
	}
	public ItemStackBuilder(NmsNBTTagCompound nbt)
	{
		this(ObcItemStack.asCraftMirror(NmsItemStack.fromNbt(nbt)).getRaw());
	}
	public ItemStackBuilder(ItemStackBuilder isb)
	{
		this(isb.item.getRaw());
	}
	public ItemStackBuilder(String id)
	{
		this(ObcMagicNumbers.getMaterial(NmsItem.fromStringId(id)));
	}
	public ItemStackBuilder(Material m)
	{
		this(new ItemStack(m));
	}
	public static ItemStackBuilder air()
	{
		return new ItemStackBuilder(Material.AIR);
	}
	public static ItemStackBuilder whiteStainedGlassPane()
	{
		return forFlattening("stained_glass_pane",(short) 0,"white_stained_glass_pane");
	}
	public static ItemStackBuilder orangeStainedGlassPane()
	{
		return forFlattening("stained_glass_pane",(short) 1,"orange_stained_glass_pane");
	}
	public static ItemStackBuilder lightBlueStainedGlassPane()
	{
		return forFlattening("stained_glass_pane",(short) 3,"light_blue_stained_glass_pane");
	}
	public static ItemStackBuilder yellowStainedGlassPane()
	{
		return forFlattening("stained_glass_pane",(short) 4,"yellow_stained_glass_pane");
	}
	public static ItemStackBuilder limeStainedGlassPane()
	{
		return forFlattening("stained_glass_pane",(short) 5,"lime_stained_glass_pane");
	}
	public static ItemStackBuilder grayStainedGlassPane()
	{
		return forFlattening("stained_glass_pane",(short) 7,"gray_stained_glass_pane");
	}
	public static ItemStackBuilder purpleStainedGlassPane()
	{
		return forFlattening("stained_glass_pane",(short) 10,"purple_stained_glass_pane");
	}
	public static ItemStackBuilder blueStainedGlassPane()
	{
		return forFlattening("stained_glass_pane",(short) 11,"blue_stained_glass_pane");
	}
	public static ItemStackBuilder brownStainedGlassPane()
	{
		return forFlattening("stained_glass_pane",(short) 12,"brown_stained_glass_pane");
	}
	public static ItemStackBuilder redStainedGlassPane()
	{
		return forFlattening("stained_glass_pane",(short) 14,"red_stained_glass_pane");
	}
	public static ItemStackBuilder blackStainedGlassPane()
	{
		return forFlattening("stained_glass_pane",(short) 15,"black_stained_glass_pane");
	}
	public static ItemStackBuilder grass()
	{
		return forFlattening("tallgrass",(short) 1,"grass");
	}
	public static ItemStackBuilder grassBlock()
	{
		return forFlattening("grass",(short) 0,"grass_block");
	}
	public static ItemStackBuilder expBottle()
	{
		return forFlattening("exp_bottle",(short) 0,"experience_bottle");
	}
	public static ItemStackBuilder sign()
	{
		return forFlattening("sign",(short) 0,"oak_sign");
	}
	public static ItemStackBuilder planks()
	{
		return forFlattening("planks",(short) 0,"oak_planks");
	}
	public static ItemStackBuilder clock()
	{
		return new ItemStackBuilder("clock");
	}
	public static ItemStackBuilder enderEye()
	{
		return new ItemStackBuilder("ender_eye");
	}
	public static ItemStackBuilder newSkull(String name,UUID id,String value)
	{
		ItemStackBuilder is = forFlattening("skull",3,"player_head");
		is.tag().set("SkullOwner",NmsNBTTagCompound.newInstance().set("Id",Server.instance.version<16?NmsNBTTagString.newInstance(id.toString()):NmsNBTTagIntArray.newInstance(id)).set("Properties",NmsNBTTagCompound.newInstance().set("textures",NmsNBTTagList.newInstance(NmsNBTTagCompound.newInstance().set("Value",NmsNBTTagString.newInstance(value))))));
		if(name!=null)
			is.tag().getCompound("SkullOwner").set("Name",NmsNBTTagString.newInstance(name));
		return is;
	}
	public static ItemStackBuilder craftingTable()
	{
		return new ItemStackBuilder("crafting_table");
	}
	public static ItemStackBuilder newSkull(String name,String url)
	{
		return newSkull(name,UUID.nameUUIDFromBytes(url.getBytes(StringUtil.UTF8)),Base64.getEncoder().encodeToString(("{\"textures\":{\"SKIN\":{\"url\":\""+url+"\"}}}").getBytes(StringUtil.UTF8)));
	}
	public static ItemStackBuilder questionMark()
	{
		return newSkull(null,"http://textures.minecraft.net/texture/65b95da1281642daa5d022adbd3e7cb69dc0942c81cd63be9c3857d222e1c8d9");
	}
	public static ItemStackBuilder returnArrow()
	{
		return newSkull(null,"http://textures.minecraft.net/texture/d9ed8bcbafbe99787325239048b8099407a098e7077c9b4c3b478b289b9149fd");
	}
	public static ItemStackBuilder leftArrow()
	{
		return newSkull(null,"http://textures.minecraft.net/texture/3866a889e51ca79c5d200ea6b5cfd0a655f32fea38b8138598c72fb200b97b9");
	}
	public static ItemStackBuilder rightArrow()
	{
		return newSkull(null,"http://textures.minecraft.net/texture/dfbf1402a04064cebaa96b77d5455ee93b685332e264c80ca36415df992fb46c");
	}
	public static ItemStackBuilder checkmark()
	{
		return newSkull(null,"http://textures.minecraft.net/texture/ce2a530f42726fa7a31efab8e43dadee188937cf824af88ea8e4c93a49c57294");
	}
	
	public static ItemStackBuilder forFlattening(String id,int childId,String idV13)
	{
		if(Server.instance.v13)
			return new ItemStackBuilder(idV13);
		else
			return new ItemStackBuilder(id).setChildId(childId);
	}
	public static String getId(Material m)
	{
		return ObcMagicNumbers.getItem(m).getStringId();
	}
	@SuppressWarnings("deprecation")
	public static MaterialData getData(ItemStack is)
	{
		if(is==null)
			return new MaterialData(Material.AIR);
		return new MaterialData(is.getType(),(byte) is.getDurability());
	}
	public static NmsItemStack toNms(ItemStack is)
	{
		return ObcItemStack.asNMSCopy(is);
	}
	public static ItemStack fromNms(NmsItemStack nms)
	{
		return ObcItemStack.asBukkitCopy(nms);
	}
	public static String getTranslateKey(ItemStack is)
	{
		return toNms(is).getTranslateKey();
	}
	public static String getDropName0(ItemStack is,CommandSender sender)
	{
		String prefix="";
		if(is.getType()==Material.ENCHANTED_BOOK)
			prefix="§e";
		else if(is.hasItemMeta()&&is.getItemMeta().hasEnchants())
			prefix="§b";
		if(is.hasItemMeta())
		{
			ItemMeta im=is.getItemMeta();
			if(im.hasDisplayName())
			{
				String displayName;
				if(Server.instance.v13)
				{
					NmsIChatBaseComponent c;
					try
					{
						c=NmsChatSerializer.jsonToComponent(new ItemStackBuilder(is).display().getString("Name"));
						TypeUtil.<MalformedJsonException>fakeThrow();
					}
					catch(MalformedJsonException|JsonSyntaxException e)
					{
						c=new TextMessageComponent(new ItemStackBuilder(is).display().getString("Name")).toNms();
					}
					displayName=ObcChatMessage.fromComponentV13(LangUtil.translated(c,LangUtil.getLang(sender)));
				}
				else
				{
					displayName=im.getDisplayName();
				}
				return StringUtil.replaceStrings(LangUtil.getTranslated(sender,new ItemStackBuilder(is).tag().getBool("rawName",false)?"mzlib.dropName.displayName":"mzlib.dropName.rawName"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{name\\}",prefix+displayName))));
			}
		}
		if(is.getType()==Material.WRITTEN_BOOK)
		{
			BookMeta im=(BookMeta) is.getItemMeta();
			if(im.hasTitle())
				return StringUtil.replaceStrings(LangUtil.getTranslated(sender,"mzlib.dropName.bookTitle"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{name\\}",prefix+im.getTitle()))));
		}
		return StringUtil.replaceStrings(LangUtil.getTranslated(sender,"mzlib.dropName.rawName"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{name\\}",prefix+LangUtil.getTranslated(sender,getTranslateKey(is))))));
	}
	public static String getDropName(ItemStack is,CommandSender sender)
	{
		ShowItemEvent event=new ShowItemEvent(sender instanceof Player?(Player) sender:null,is);
		Bukkit.getPluginManager().callEvent(event);
		is=event.item.get();
		return getDropName0(is,sender);
	}
	public static String getDropNameWithNum(String dropName,int num,CommandSender sender)
	{
		if(num==1)
			return StringUtil.replaceStrings(LangUtil.getTranslated(sender,"mzlib.dropName.oneItem"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{name\\}",dropName))));
		else
			return StringUtil.replaceStrings(LangUtil.getTranslated(sender,"mzlib.dropName.multipleItems"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{name\\}",dropName),new MapEntry<>("%\\{num\\}",num+""))));
	}
	public static String getDropNameWithNum0(ItemStack is,CommandSender sender)
	{
		return getDropNameWithNum(getDropName0(is,sender),isAir(is)?1:is.getAmount(),sender);
	}
	public static String getDropNameWithNum(ItemStack is,CommandSender sender)
	{
		return getDropNameWithNum(getDropName(is,sender),isAir(is)?1:is.getAmount(),sender);
	}
	public static boolean isAir(ItemStack is)
	{
		return is==null||is.getType()==Material.AIR||is.getAmount()<1;
	}
	public static byte getCount(ItemStack item)
	{
		if(isAir(item))
			return 0;
		else
			return (byte) item.getAmount();
	}
	@Override
	public ItemStack get()
	{
		return item.getRaw();
	}
	public String getId()
	{
		if(WrappedObject.isNull(item.getHandle()))
			return "minecraft:air";
		return item.getHandle().getItem().getStringId();
	}
	public ItemStackBuilder setId(String id)
	{
		if(WrappedObject.isNull(this.item.getHandle()))
			this.item.setHandle(NmsItemStack.newInstance(NmsItem.fromStringId(id)));
		else
			item.getHandle().setItem(NmsItem.fromStringId(id));
		return this;
	}
	public ItemStackBuilder setId(Material m)
	{
		return setId(getId(m));
	}
	public byte getCount()
	{
		return (byte) item.getRaw().getAmount();
	}
	public ItemStackBuilder setCount(int count)
	{
		item.getRaw().setAmount(count);
		return this;
	}
	public ItemStackBuilder add(int count)
	{
		return setCount((byte) (getCount()+count));
	}
	public NmsItemStack getHandle()
	{
		if(WrappedObject.isNull(this.item.getHandle()))
			this.item.setHandle(NmsItemStack.newInstance(ObcMagicNumbers.getItem(Material.AIR)));
		return this.item.getHandle();
	}
	public boolean hasTag()
	{
		if(WrappedObject.isNull(this.item.getHandle()))
			return false;
		return !WrappedObject.isNull(item.getHandle().getTag());
	}
	public NmsNBTTagCompound tag()
	{
		if(!hasTag())
			getHandle().setTag(NmsNBTTagCompound.newInstance());
		return getHandle().getTag();
	}
	public ItemStackBuilder setTag(NmsNBTTagCompound tag)
	{
		if(WrappedObject.isNull(this.item.getHandle()))
			this.item.setHandle(NmsItemStack.newInstance(ObcMagicNumbers.getItem(Material.AIR)));
		item.getHandle().setTag(tag);
		return this;
	}
	public int getDamage()
	{
		if(Server.instance.v13)
		{
			if(tag().getMap().containsKey("Damage"))
				return WrappedObject.wrap(NmsNBTTagInt.class,tag().getMap().get("Damage")).getValue0();
			return 0;
		}
		else
			return getChildId();
	}
	public ItemStackBuilder setDamage(int damage)
	{
		if(Server.instance.v13)
			tag().set("Damage",NmsNBTTagInt.newInstance(damage));
		else
			setChildId((short) damage);
		return this;
	}
	public ItemStackBuilder removeMapId()
	{
		if(Server.instance.v13)
			tag().remove("map");
		else
			setChildId(0);
		return this;
	}
	public ItemStackBuilder setMapId(int mapId)
	{
		if(Server.instance.v13)
			tag().set("map",NmsNBTTagInt.newInstance(mapId));
		else
			setChildId(mapId);
		return this;
	}
	/**
	 * It doesn't work after The Flattening(MC 1.13)
	 *
	 * @return childId child ID
	 */
	public short getChildId()
	{
		if(Server.instance.v13)
			return 0;
		return item.getRaw().getDurability();
	}
	/**
	 * It doesn't work after The Flattening(MC 1.13)
	 *
	 * @param childId child ID
	 * @return this
	 */
	public ItemStackBuilder setChildId(int childId)
	{
		if(!Server.instance.v13)
			item.getRaw().setDurability((short) childId);
		return this;
	}
	public boolean hasEnchant()
	{
		if(Server.instance.v13)
			return hasTag()&&(tag().containsKey("Enchantments"));
		else
			return hasTag()&&(tag().containsKey("ench"));
	}
	@SuppressWarnings("deprecation")
	public Map<Enchantment,Short> getEnchants()
	{
		Map<Enchantment,Short> r=new HashMap<>();
		if(Server.instance.v13)
		{
			List<NmsNBTTagCompound> t=TypeUtil.cast(tag().getList("Enchantments").values());
			if(t==null)
				return null;
			t.forEach(n->
			{
				String[] key=n.getString("id").split(":");
				r.put(EnchantUtil.getEnchant(key.length>1?new NamespacedKey(key[0],key[1]):new NamespacedKey("minecraft",key[0])),n.getShort("lvl"));
			});
		}
		else
		{
			List<NmsNBTTagCompound> t=TypeUtil.cast(tag().getList("ench").values());
			if(t==null)
				return null;
			t.forEach(n->
			{
				r.put(Enchantment.getById(n.getShort("id")),n.getShort("lvl"));
			});
		}
		return r;
	}
	@SuppressWarnings("deprecation")
	public ItemStackBuilder setEnchants(Map<Enchantment,Short> enchants)
	{
		if(Server.instance.v13)
		{
			NmsNBTTagList t=NmsNBTTagList.newInstance();
			enchants.forEach((e,l)->
			{
				NmsNBTTagCompound tt=NmsNBTTagCompound.newInstance();
				tt.getMap().put("id",NmsNBTTagString.newInstance(EnchantUtil.getEnchantId(e)).getRaw());
				tt.getMap().put("lvl",NmsNBTTagShort.newInstance(l).getRaw());
				t.add(tt);
			});
			tag().set("Enchantments",t);
		}
		else
		{
			NmsNBTTagList t=NmsNBTTagList.newInstance();
			enchants.forEach((e,l)->
			{
				t.add(NmsNBTTagCompound.newInstance().set("id",NmsNBTTagShort.newInstance((short) e.getId())).set("lvl",NmsNBTTagShort.newInstance(l)));
			});
			tag().set("ench",t);
		}
		return this;
	}
	@SuppressWarnings("deprecation")
	public ItemStackBuilder addEnchant(Enchantment enchant,short lvl)
	{
		NmsNBTTagCompound tag=tag();
		if(Server.instance.v13)
		{
			if(!tag.containsKey("Enchantments"))
				tag.set("Enchantments",NmsNBTTagList.newInstance());
			tag.getList("Enchantments").add(NmsNBTTagCompound.newInstance().set("id",NmsNBTTagString.newInstance(EnchantUtil.getEnchantId(enchant))).set("lvl",NmsNBTTagShort.newInstance(lvl)));
		}
		else
		{
			if(!tag.containsKey("ench"))
				tag.set("ench",NmsNBTTagList.newInstance());
			tag.getList("ench").add(NmsNBTTagCompound.newInstance().set("id",NmsNBTTagShort.newInstance((short) enchant.getId())).set("lvl",NmsNBTTagShort.newInstance(lvl)));
		}
		return this;
	}
	public int getHideFlags()
	{
		if(tag().containsKey("HideFlags"))
		{
			try
			{
				return tag().getInt("HideFlags");
			}
			catch(Throwable e)
			{
				return tag().getByte("HideFlags");
			}
		}
		return 0;
	}
	public ItemStackBuilder setHideFlags(int hideFlags)
	{
		tag().set("HideFlags",NmsNBTTagInt.newInstance(hideFlags));
		return this;
	}
	public boolean isEnchantsHide()
	{
		return (getHideFlags()&1)!=0;
	}
	public ItemStackBuilder setHideEnchants(boolean hideEnchants)
	{
		if(hideEnchants)
			return setHideFlags((byte) (getHideFlags()|1));
		else
			return setHideFlags((byte) (getHideFlags()&~1));
	}
	public NmsNBTTagCompound display()
	{
		NmsNBTTagCompound tag=tag();
		if(!tag.containsKey("display"))
			tag.set("display",NmsNBTTagCompound.newInstance());
		return tag.getCompound("display");
	}
	public boolean hasDisplay()
	{
		return hasTag()&&tag().containsKey("display");
	}
	public boolean hasLocName()
	{
		return hasDisplay()&&display().containsKey("LocName");
	}
	public String getLocName()
	{
		return display().getString("LocName");
	}
	public ItemStackBuilder setLocName(String locName)
	{
		display().set("LocName",NmsNBTTagString.newInstance(locName));
		return this;
	}
	public ItemStackBuilder setTranslated(String locale,String translatedKey)
	{
		return setName(LangUtil.getTranslated(locale,translatedKey)).setLore(StringUtil.split(LangUtil.getTranslated(locale,translatedKey+".lore"),"\n"));
	}
	public static JsonObject setDefaultNonItalic(JsonObject json)
	{
		if(!json.has("italic"))
			json.addProperty("italic",false);
		if(json.has("extra"))
		{
			for(JsonElement extra:json.get("extra").getAsJsonArray())
			{
				setDefaultNonItalic(extra.getAsJsonObject());
			}
		}
		if(json.has("with"))
		{
			for(JsonElement extra:json.get("with").getAsJsonArray())
			{
				setDefaultNonItalic(extra.getAsJsonObject());
			}
		}
		return json;
	}
	public ItemStackBuilder setNameV14(NmsIChatBaseComponent name)
	{
		display().set("Name",NmsNBTTagString.newInstance(ObcChatMessage.toJson(name)));
		return this;
	}
	public NmsIChatBaseComponent getNameV14()
	{
		return NmsIChatBaseComponent.NmsChatSerializer.jsonToComponent(display().getString("Name"));
	}
	public ItemStackBuilder setName(String name)
	{
		if(Server.instance.v13)
		{
			name=ObcChatMessage.fromStringOrNullToJSONV13(name);
			if(name==null)
				name="{\"text\":\"\"}";
			name=setDefaultNonItalic(new JsonParser().parse(name).getAsJsonObject()).toString();
		}
		display().set("Name",NmsNBTTagString.newInstance(name));
		return this;
	}
	public String getName()
	{
		String r=display().getString("Name");
		if(Server.instance.v13)
			r=ObcChatMessage.fromJSONComponentV13(r);
		return r;
	}
	public boolean hasName()
	{
		return hasDisplay()&&display().containsKey("Name");
	}
	public List<String> getLore()
	{
		if(hasDisplay()&&display().containsKey("Lore"))
		{
			List<String> r=display().getList("Lore").values().stream().map(n->n.cast(NmsNBTTagString.class).getValue()).collect(Collectors.toList());
			if(Server.instance.version>=14)
				r=r.stream().map(l->ObcChatMessage.fromJSONComponentV13(l)).collect(Collectors.toList());
			return r;
		}
		return new ArrayList<>();
	}
	public ItemStackBuilder setLore(List<String> lore)
	{
		if(Server.instance.version>=14)
			lore=lore.stream().map(l->l==null||l.length()==0?"{\"text\":\"\"}":ObcChatMessage.fromStringOrNullToJSONV13(l)).map(l->setDefaultNonItalic(new JsonParser().parse(l).getAsJsonObject()).toString()).collect(Collectors.toList());
		if(lore.isEmpty())
			display().remove("Lore");
		else
			display().set("Lore",NmsNBTTagList.wrapValues(lore));
		return this;
	}
	public ItemStackBuilder setLore(String... lore)
	{
		return setLore(Lists.newArrayList(lore));
	}
	public NmsNBTTagCompound save()
	{
		if(WrappedObject.isNull(this.item.getHandle()))
			this.item.setHandle(NmsItemStack.newInstance(ObcMagicNumbers.getItem(Material.AIR)));
		return item.getHandle().save(NmsNBTTagCompound.newInstance());
	}
	public ItemStackBuilder debug()
	{
		System.out.println(this.save());
		return this;
	}
	public String getDropName0(CommandSender sender)
	{
		return getDropName0(get(),sender);
	}
	public String getDropNameWithNum(CommandSender sender)
	{
		return getDropNameWithNum(sender);
	}
	@Override
	public String toString()
	{
		return save().toString();
	}
	public ItemStackBuilder addLore(String... lore)
	{
		List<String> l=getLore();
		l.addAll(Lists.newArrayList(lore));
		setLore(l);
		return this;
	}
}
