package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.Instance;

import java.util.HashSet;
import java.util.Set;

public abstract class RegistrarRecipe implements IRegistrar<RecipeRegistration>, Instance
{
    public static RegistrarRecipe instance;

    Set<RecipeRegistration> recipes = new HashSet<>();

    @Override
    public Class<RecipeRegistration> getType()
    {
        return RecipeRegistration.class;
    }

    public abstract void flush();

    boolean isDirty = false;
    public synchronized void markDirty()
    {
        if(this.isDirty)
            return;
        this.isDirty = true;
        MinecraftServer.instance.schedule(() ->
        {
            synchronized(RegistrarRecipe.this)
            {
                this.isDirty = false;
                this.flush();
            }
        });
    }

    protected abstract void setRaw();

    public void onReloadEnd()
    {
        this.setRaw();
        this.flush();
    }

    @Override
    public synchronized void register(MzModule module, RecipeRegistration object)
    {
        this.recipes.add(object);
        this.markDirty();
    }
    @Override
    public synchronized void unregister(MzModule module, RecipeRegistration object)
    {
        this.recipes.remove(object);
        this.markDirty();
    }
}
