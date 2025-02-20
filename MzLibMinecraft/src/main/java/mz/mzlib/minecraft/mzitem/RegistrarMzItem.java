package mz.mzlib.minecraft.mzitem;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.item.Item;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.Option;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class RegistrarMzItem implements IRegistrar<Class<? extends MzItem>>
{
    public static RegistrarMzItem instance = new RegistrarMzItem();
    
    Map<Identifier, Function<Object, ? extends MzItem>> creators = new ConcurrentHashMap<>();
    
    public MzItem newMzItem(Identifier id)
    {
        Function<Object, ? extends MzItem> creator = this.creators.get(id);
        MzItem result = creator.apply(creator.apply(null).staticVanilla());
        result.init();
        return result;
    }
    
    public Option<MzItem> toMzItem(ItemStack itemStack)
    {
        for(NbtCompound mz: Item.getCustomData(itemStack).getNBTCompound("mz"))
            return Option.some(itemStack.castTo(this.creators.get(Identifier.newInstance(mz.getString("id").unwrap()))));
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
        MzItem staticOne = WrapperObject.create(object, null);
        this.creators.put(staticOne.staticGetId(), RuntimeUtil.cast((Function<Object, WrapperObject>)staticOne::staticCreate));
    }
    @Override
    public void unregister(MzModule module, Class<? extends MzItem> object)
    {
        this.creators.remove(WrapperObject.create(object, null).staticGetId());
    }
}
