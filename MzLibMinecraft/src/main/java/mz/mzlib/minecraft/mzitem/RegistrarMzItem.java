package mz.mzlib.minecraft.mzitem;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.item.Item;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.Option;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapperFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RegistrarMzItem implements IRegistrar<Class<? extends MzItem>>
{
    public static RegistrarMzItem instance = new RegistrarMzItem();
    
    Map<Identifier, WrapperFactory<? extends MzItem>> factories = new ConcurrentHashMap<>();
    
    public MzItem newMzItem(Identifier id)
    {
        WrapperFactory<? extends MzItem> factory = this.factories.get(id);
        MzItem result = factory.getStatic().staticVanilla().as(factory);
        result.init();
        return result;
    }
    
    public Option<MzItem> toMzItem(ItemStack itemStack)
    {
        for(NbtCompound customData: Item.getCustomData(itemStack))
            for(NbtCompound mz: customData.getNBTCompound("mz"))
                return Option.some(itemStack.as(Option.<WrapperFactory<? extends MzItem>>fromNullable(this.factories.get(Identifier.newInstance(mz.getString("id").unwrap()))).unwrapOr(MzItemUnknown.FACTORY)));
        return Option.none();
    }
    
    @Override
    public Class<Class<? extends MzItem>> getType()
    {
        return RuntimeUtil.cast(Class.class);
    }
    
    @Override
    public boolean isRegistrable(Class<? extends MzItem> object)
    {
        return MzItem.class.isAssignableFrom(object);
    }
    
    @Override
    public void register(MzModule module, Class<? extends MzItem> object)
    {
        if(!object.isAnnotationPresent(MzItemClass.class))
            throw new IllegalStateException("Class "+object.getName()+" is not annotated with @MzItemClass");
        WrapperFactory<? extends MzItem> factory = WrapperFactory.of(object);
        this.factories.put(factory.getStatic().staticGetMzId(), factory);
    }
    @Override
    public void unregister(MzModule module, Class<? extends MzItem> object)
    {
        this.factories.remove(WrapperFactory.of(object).getStatic().staticGetMzId());
    }
}
