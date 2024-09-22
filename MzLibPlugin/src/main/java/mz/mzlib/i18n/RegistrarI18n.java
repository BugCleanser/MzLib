package mz.mzlib.i18n;

import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RegistrarI18n implements IRegistrar<I18n>
{
    public static RegistrarI18n instance=new RegistrarI18n();

    public List<I18n> sortedI18ns=new ArrayList<>();
    public String getTranslation(String language, String key)
    {
        String result = getTranslationOrNull(language, key);
        return result!=null?result:key;
    }
    public String getTranslationOrNull(String language, String key)
    {
        for(I18n i18n:sortedI18ns)
        {
            String translation=i18n.getTranslation(language, key);
            if(translation!=null)
                return translation;
        }
        return null;
    }

    @Override
    public Class<I18n> getType()
    {
        return I18n.class;
    }

    public Set<I18n> i18ns=new HashSet<>();
    @Override
    public void register(MzModule module, I18n object)
    {
        this.i18ns.add(object);
        this.update();
    }
    @Override
    public void unregister(MzModule module, I18n object)
    {
        this.i18ns.remove(object);
        this.update();
    }
    public void update()
    {
        this.sortedI18ns=this.i18ns.stream().sorted((a,b)->Float.compare(b.getPriority(),a.getPriority())).collect(Collectors.toList());
    }
}
