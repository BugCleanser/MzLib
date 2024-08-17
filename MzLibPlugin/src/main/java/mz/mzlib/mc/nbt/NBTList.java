package mz.mzlib.mc.nbt;

public interface NBTList extends NBTElement
{
    NBTElement get(int index);

    void set(int index, NBTElement element);
}
