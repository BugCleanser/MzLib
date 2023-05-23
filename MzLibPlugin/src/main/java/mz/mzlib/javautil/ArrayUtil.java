package mz.mzlib.javautil;

import java.util.Arrays;

public class ArrayUtil
{
	private ArrayUtil() {}
	
	public static Object[] box(Object array)
	{
		if(array instanceof int[])
			return Arrays.stream((int[])array).boxed().toArray(Integer[]::new);
		else if(array instanceof long[])
			return Arrays.stream((long[])array).boxed().toArray(Long[]::new);
		else if(array instanceof double[])
			return Arrays.stream((double[])array).boxed().toArray(Double[]::new);
		else if(array instanceof boolean[])
		{
			Boolean[] result=new Boolean[((boolean[])array).length];
			for(int i=0;i<result.length;i++)
				result[i]=((boolean[])array)[i];
			return result;
		}
		else if(array instanceof char[])
		{
			Character[] result=new Character[((char[])array).length];
			for(int i=0;i<result.length;i++)
				result[i]=((char[])array)[i];
			return result;
		}
		else if(array instanceof byte[])
		{
			Byte[] result=new Byte[((byte[])array).length];
			for(int i=0;i<result.length;i++)
				result[i]=((byte[])array)[i];
			return result;
		}
		else if(array instanceof short[])
		{
			Short[] result=new Short[((short[])array).length];
			for(int i=0;i<result.length;i++)
				result[i]=((short[])array)[i];
			return result;
		}
		else if(array instanceof float[])
		{
			Float[] result=new Float[((float[])array).length];
			for(int i=0;i<result.length;i++)
				result[i]=((float[])array)[i];
			return result;
		}
		else
			throw new ClassCastException("It's not a primitive array: "+array);
	}
}
