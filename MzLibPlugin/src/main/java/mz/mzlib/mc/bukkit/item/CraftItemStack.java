package mz.mzlib.mc.bukkit.item;

import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.bukkit.delegator.DelegatorOBCClass;
import mz.mzlib.mc.item.ItemStack;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorConstructor;
import mz.mzlib.util.delegator.DelegatorCreator;
import mz.mzlib.util.delegator.DelegatorFieldAccessor;

@DelegatorOBCClass(@VersionName(name="OBC.inventory.CraftItemStack"))
public interface CraftItemStack extends Delegator
{
    @Override
    org.bukkit.inventory.ItemStack getDelegate();
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static CraftItemStack create(org.bukkit.inventory.ItemStack delegate)
    {
        return Delegator.create(CraftItemStack.class, delegate);
    }

    @DelegatorConstructor
    CraftItemStack staticNewInstance(ItemStack handle);
    static CraftItemStack newInstance(ItemStack handle)
    {
        return create(null).staticNewInstance(handle);
    }
    @DelegatorConstructor
    CraftItemStack staticNewInstance(org.bukkit.inventory.ItemStack is);
    static CraftItemStack newInstance(org.bukkit.inventory.ItemStack is)
    {
        return create(null).staticNewInstance(is);
    }

    @DelegatorFieldAccessor("handle")
    ItemStack getHandle();
}
