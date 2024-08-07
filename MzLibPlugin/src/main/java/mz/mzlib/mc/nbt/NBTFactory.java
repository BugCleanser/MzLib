package mz.mzlib.mc.nbt;

import mz.mzlib.util.Instance;
import mz.mzlib.util.RuntimeUtil;

public interface NBTFactory extends Instance
{
	NBTFactory instance=RuntimeUtil.nul();
	
	NBTCompound createCompound();
	NBTByte create(byte value);
	NBTShort create(short value);
	NBTInt create(int value);
	NBTLong create(long value);
	NBTFloat create(float value);
	NBTDouble create(double value);
	NBTString create(String value);
	NBTByteArray create(byte[] value);
	NBTIntArray create(int[] value);
	NBTLongArray create(long[] value);
	NBTList create(NBTElement ...value);
}