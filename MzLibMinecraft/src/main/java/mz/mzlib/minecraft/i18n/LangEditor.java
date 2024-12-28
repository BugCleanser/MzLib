package mz.mzlib.minecraft.i18n;

import com.google.gson.Gson;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import mz.mzlib.i18n.I18n;
import mz.mzlib.i18n.RegistrarI18n;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.item.ItemWrittenBook;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.text.TextHoverEvent;
import mz.mzlib.minecraft.ui.UIStack;
import mz.mzlib.minecraft.ui.book.UIWrittenBook;
import mz.mzlib.minecraft.ui.window.UIWindowAnvilInput;
import mz.mzlib.util.RuntimeUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class LangEditor extends UIWrittenBook
{
    public String lang;
    public String node;
    public String def, custom;
    int buttonBack = -1, buttonSet = -1, buttonRemove = -1;
    public Map<String, Integer> childNodes = new HashMap<>();
    
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
            this.buttonBack = this.newButton(player->UIStack.get(player).back());
            this.buttonSet = this.newButton(player->UIWindowAnvilInput.invoke(player, escape(I18n.getTranslation(this.lang, this.node, "")), Text.literal(String.format(I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.custom.set.title"), this.node))).whenComplete((r, e)->
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
                    player.sendMessage(Text.literal(String.format(I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.custom.set.fail.unescape"), escape(r))));
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
        for(String childNode: getTranslationKeyChildNodes(node))
        {
            this.childNodes.put(childNode, this.newButton(player->UIStack.get(player).go(new LangEditor(lang, node!=null ? node+'.'+childNode : childNode))));
        }
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
            homepage.add(Text.literal(String.format(I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.lang"), this.lang)));
            homepage.add(Text.literal("\n"));
        }
        else
        {
            homepage.add(Text.literal(I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.back")).setHoverEvent(TextHoverEvent.showText(Text.literal(this.node.contains(".") ? String.format(I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.back.lore.node"), this.node.substring(0, this.node.lastIndexOf('.'))) : I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.back.lore.root")))).setClickEvent(buttonClickEvent(this.buttonBack)));
            homepage.add(Text.literal("\n"));
            homepage.add(Text.literal(String.format(I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.node"), this.node)));
            homepage.add(Text.literal("\n"));
            homepage.add(Text.literal("\n"));
            String def = I18n.getTranslationDefault(this.lang, this.node, null);
            homepage.add(Text.literal(String.format(I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.default"), (def!=null ? escape(def) : I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.default.null")))).setHoverEvent(TextHoverEvent.showText(Text.literal(def!=null ? String.format(I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.default.lore"), def) : I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.default.lore.null")))));
            homepage.add(Text.literal("\n"));
            String custom = I18n.custom.get(this.lang, this.node);
            homepage.add(Text.literal(String.format(I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.custom"), (custom!=null ? escape(custom) : I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.custom.null")))).setHoverEvent(TextHoverEvent.showText(Text.literal(custom!=null ? String.format(I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.custom.lore"), custom) : I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.custom.lore.null")))));
            homepage.add(Text.literal("\n"));
            homepage.add(Text.literal(I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.custom.set")).setHoverEvent(TextHoverEvent.showText(Text.literal(I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.custom.set.lore")))).setClickEvent(buttonClickEvent(this.buttonSet)));
            homepage.add(Text.literal(I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.custom.gap")));
            homepage.add(Text.literal(I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.custom.remove")).setHoverEvent(TextHoverEvent.showText(Text.literal(I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.custom.remove.lore")))).setClickEvent(buttonClickEvent(this.buttonRemove)));
            homepage.add(Text.literal("\n"));
        }
        homepage.add(Text.literal(I18nMinecraft.getTranslation(player, "mzlib.lang.editor.homepage.tips")));
        pages.add(Text.literal("").setExtra(homepage));
        pages.addAll(ItemWrittenBook.makePages(childNodes.entrySet().stream().map(entry->Text.literal(String.format(I18nMinecraft.getTranslation(player, "mzlib.lang.editor.list.node"), entry.getKey())+'\n').setClickEvent(buttonClickEvent(entry.getValue()))).collect(Collectors.toList())));
        return pages;
    }
    
    public static Set<String> getTranslationKeyChildNodes(String parent)
    {
        String prefix = parent!=null ? parent+"." : "";
        Set<I18n> i18ns = new HashSet<>(RegistrarI18n.instance.sortedI18ns);
        i18ns.add(I18n.custom);
        Set<String> result = new HashSet<>();
        for(I18n i18n: i18ns)
        {
            for(Map.Entry<String, Map<String, String>> entry: i18n.map.entrySet())
            {
                entry.getValue().keySet().stream().filter(key->key.startsWith(prefix)).map(key->key.substring(prefix.length()).split("\\.", 2)[0]).forEach(result::add);
            }
        }
        return result;
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
