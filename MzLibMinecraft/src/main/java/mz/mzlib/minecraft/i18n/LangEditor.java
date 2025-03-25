package mz.mzlib.minecraft.i18n;

import com.google.gson.Gson;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import mz.mzlib.i18n.I18n;
import mz.mzlib.i18n.RegistrarI18n;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.item.ItemWrittenBook;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.text.TextClickEvent;
import mz.mzlib.minecraft.text.TextHoverEvent;
import mz.mzlib.minecraft.ui.UIStack;
import mz.mzlib.minecraft.ui.book.UIWrittenBook;
import mz.mzlib.minecraft.ui.window.UIWindowAnvilInput;
import mz.mzlib.util.JsUtil;
import mz.mzlib.util.MapBuilder;
import mz.mzlib.util.RuntimeUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class LangEditor extends UIWrittenBook
{
    public String lang;
    public String node;
    public String def, custom;
    int buttonSet = -1, buttonRemove = -1;
    public List<String> childNodes = new ArrayList<>();
    
    public LangEditor(String lang)
    {
        this(lang, null);
    }
    public LangEditor(String lang, String node)
    {
        this.lang = lang;
        this.node = node;
        if(this.node!=null)
        {
            this.buttonSet = this.newButton(player->UIWindowAnvilInput.invoke(player, escape(I18n.getTranslation(this.lang, this.node, "")), Text.literal(I18nMinecraft.getTranslationWithArgs(player, "mzlib.lang.editor.homepage.custom.set.title", Collections.singletonMap("node", this.node)))).whenComplete((r, e)->
            {
                if(e!=null)
                    throw RuntimeUtil.sneakilyThrow(e);
                String u;
                try
                {
                    u = unescape(r);
                }
                catch(Throwable e1)
                {
                    player.sendMessage(Text.literal(I18nMinecraft.getTranslationWithArgs(player, "mzlib.lang.editor.homepage.custom.set.failure.unescape", Collections.singletonMap("escaped", escape(r)))));
                    return;
                }
                I18n.custom.map.computeIfAbsent(this.lang, k->new ConcurrentHashMap<>()).put(this.node, u);
                I18nMinecraft.saveCustomLanguage(this.lang);
                UIStack.get(player).back();
            }));
            this.buttonRemove = this.newButton(player->
            {
                I18n.custom.map.computeIfAbsent(this.lang, k->new ConcurrentHashMap<>()).remove(this.node);
                I18nMinecraft.saveCustomLanguage(this.lang);
                this.open(player);
            });
        }
        this.childNodes.addAll(getTranslationKeyChildNodes(node));
    }
    
    @Override
    public List<Text> getPages(EntityPlayer player)
    {
        List<Text> pages = new ArrayList<>();
        List<Text> homepage = new ArrayList<>();
        if(this.node==null)
        {
            homepage.add(Text.literal(I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.title")));
            homepage.add(Text.literal("\n"));
            homepage.add(Text.literal(I18nMinecraft.getTranslationWithArgs(player, "mzlib.lang.editor.homepage.lang", Collections.singletonMap("lang", this.lang))));
            homepage.add(Text.literal("\n"));
        }
        else
        {
            homepage.add(Text.literal(I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.back")).setHoverEvent(TextHoverEvent.showText(Text.literal(this.node.contains(".") ? I18nMinecraft.getTranslationWithArgs(player, "mzlib.lang.editor.homepage.back.lore.node", Collections.singletonMap("parent", this.node.substring(0, this.node.lastIndexOf('.')))) : I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.back.lore.root")))).setClickEvent(TextClickEvent.newInstance(TextClickEvent.Action.runCommand(), "/mzlib lang custom "+this.lang+(this.node.contains(".") ? " "+this.node.substring(0, this.node.lastIndexOf('.')) : ""))));
            homepage.add(Text.literal("\n"));
            homepage.add(Text.literal(I18nMinecraft.getTranslationWithArgs(player, "mzlib.lang.editor.homepage.node", Collections.singletonMap("node", this.node))));
            homepage.add(Text.literal("\n"));
            homepage.add(Text.literal("\n"));
            String def = I18n.getTranslationDefault(this.lang, this.node);
            homepage.add(Text.literal(I18nMinecraft.getTranslationWithArgs(player, "mzlib.lang.editor.homepage.default", Collections.singletonMap("value", def!=null ? escape(def) : I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.default.null")))).setHoverEvent(TextHoverEvent.showText(Text.literal(def!=null ? I18nMinecraft.getTranslationWithArgs(player, "mzlib.lang.editor.homepage.default.lore", Collections.singletonMap("value", def)) : I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.default.lore.null")))));
            homepage.add(Text.literal("\n"));
            String custom = I18n.custom.get(this.lang, this.node);
            homepage.add(Text.literal(I18nMinecraft.getTranslationWithArgs(player, "mzlib.lang.editor.homepage.custom", Collections.singletonMap("value", (custom!=null ? escape(custom) : I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.custom.null"))))).setHoverEvent(TextHoverEvent.showText(Text.literal(custom!=null ? I18nMinecraft.getTranslationWithArgs(player, "mzlib.lang.editor.homepage.custom.lore", Collections.singletonMap("value", custom)) : I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.custom.lore.null")))));
            homepage.add(Text.literal("\n"));
            homepage.add(Text.literal(I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.custom.set")).setHoverEvent(TextHoverEvent.showText(Text.literal(I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.custom.set.lore")))).setClickEvent(buttonClickEvent(this.buttonSet)));
            homepage.add(Text.literal(I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.custom.gap")));
            homepage.add(Text.literal(I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.custom.remove")).setHoverEvent(TextHoverEvent.showText(Text.literal(I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.custom.remove.lore")))).setClickEvent(buttonClickEvent(this.buttonRemove)));
            homepage.add(Text.literal("\n"));
        }
        homepage.add(Text.literal(I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.tips")));
        pages.add(Text.literal("").setExtra(homepage));
        pages.addAll(ItemWrittenBook.makePages(childNodes.stream().map(n->Text.literal(I18nMinecraft.getTranslationWithArgs(player, "mzlib.lang.editor.list.node", Collections.singletonMap("node", n))+'\n').setHoverEvent(TextHoverEvent.showText(Text.literal //
                ( //
                        I18nMinecraft.getTranslationWithArgs(player, "mzlib.lang.editor.list.node.lore", MapBuilder.hashMap() //
                                .put("key", this.node!=null ? this.node+"."+n : n) //
                                .put("translation", escape(I18n.getTranslation(this.lang, this.node!=null ? this.node+"."+n : n, ""))) //
                                .put("children", getTranslationKeyChildNodes(this.node!=null ? this.node+"."+n : n).stream().map(m->JsUtil.mapToObject(I18n.scope, MapBuilder.hashMap().put("node", m).put("translation", escape(I18n.getTranslation(this.lang, (this.node!=null ? this.node+"."+n : n)+"."+m, ""))).get())).toArray()) //
                                .get()) //
                ))).setClickEvent(TextClickEvent.newInstance(TextClickEvent.Action.runCommand(), "/mzlib lang custom "+this.lang+" "+(this.node!=null ? this.node+"." : "")+n))).collect(Collectors.toList())));
        return pages;
    }
    
    public static Set<String> getLanguages()
    {
        Set<String> result = new HashSet<>();
        for(I18n i18n: RegistrarI18n.instance.sortedI18ns)
        {
            result.addAll(i18n.map.keySet());
        }
        result.addAll(I18n.custom.map.keySet());
        return result;
    }
    
    public static Set<String> cacheTranslationKeys;
    public static long cacheTickTranslationKeys;
    public static Set<String> getTranslationKeys()
    {
        if(cacheTranslationKeys!=null && MinecraftServer.tickNumber.get()-cacheTickTranslationKeys<20)
            return cacheTranslationKeys;
        Set<I18n> i18ns = new HashSet<>(RegistrarI18n.instance.sortedI18ns);
        i18ns.add(I18n.custom);
        cacheTickTranslationKeys = MinecraftServer.tickNumber.get();
        return cacheTranslationKeys = i18ns.stream().flatMap(i18n->i18n.map.values().stream()).flatMap(map->map.keySet().stream()).collect(Collectors.toSet());
    }
    
    public static Set<String> getTranslationKeyChildNodes(String parent)
    {
        String prefix = parent!=null ? parent+"." : "";
        return getTranslationKeys().stream().filter(key->key.startsWith(prefix)).map(key->key.substring(prefix.length()).split("\\.", 2)[0]).collect(Collectors.toSet());
    }
    
    public static String escape(String string)
    {
        String result = new Gson().toJson(new JsonPrimitive(string));
        return result.substring(1, result.length()-1).replace("§", "\\u00a7");
    }
    
    public static String unescape(String string) throws JsonSyntaxException
    {
        return new Gson().fromJson('"'+string+'"', JsonPrimitive.class).getAsString();
    }
}
