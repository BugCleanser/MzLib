package mz.mzlib.demo;

import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.IteratorMerged;
import mz.mzlib.util.RuntimeUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class NbFixEnchantments extends MzModule
{
    void fix(NbtCompound is)
    {
        for(NbtCompound components : is.getNbtCompound("components"))
        {
            boolean il = false, sh = false;
            for(NbtCompound enchantments : IteratorMerged.iterable(
                components.getNbtCompound("minecraft:enchantments"),
                components.getNbtCompound("enchantments"),
                components.getNbtCompound("minecraft:stored_enchantment"),
                components.getNbtCompound("stored_enchantment")
            ))
            {
                for(NbtCompound levels : enchantments.getNbtCompound("levels"))
                    enchantments = levels;
                for(String k : enchantments.asMap0().keySet())
                {
                    if(enchantments.getInt(k).isSome(0))
                    {
                        il = true;
                        enchantments.remove(k);
                    }
                    else
                        sh = true;
                }
            }
            if(il && !sh)
                components.put("minecraft:enchantment_glint_override", true);
        }
    }

    @Override
    public void onLoad()
    {
        for(File file : Objects.requireNonNull(new File("./plugins/NbWorkBench/recipes").listFiles()))
        {
            System.out.println("Fixing: " + file);
            NbtCompound nbt;
            try(DataInputStream dis = new DataInputStream(Files.newInputStream(file.toPath())))
            {
                //noinspection deprecation
                nbt = NbtCompound.load(dis);
                NbtCompound raws = nbt.getNbtCompound("raws").unwrap();
                for(String key : raws.asMap0().keySet())
                {
                    fix(raws.getNbtCompound(key).unwrap());
                }
                fix(nbt.getNbtCompound("result").unwrap());
            }
            catch(IOException e)
            {
                throw RuntimeUtil.sneakilyThrow(e);
            }
            try(DataOutputStream dos = new DataOutputStream(Files.newOutputStream(file.toPath())))
            {
                //noinspection deprecation
                nbt.save(dos);
            }
            catch(IOException e)
            {
                throw RuntimeUtil.sneakilyThrow(e);
            }
        }
    }
}
