package mz.mzlib.javautil;

import java.util.Arrays;

public class ArrayUtil
{
	private ArrayUtil() {}
	
	public static Integer[] box(int[] array)
	{
		return Arrays.stream(array).boxed().toArray(Integer[]::new);
	}
	public static Long[] box(long[] array)
	{
		return Arrays.stream(array).boxed().toArray(Long[]::new);
	}
	public static Double[] box(double[] array)
	{
		return Arrays.stream(array).boxed().toArray(Double[]::new);
	}
	public static Boolean[] box(boolean[] array)
	{
		Boolean[] result=new Boolean[array.length];
		for(int i=0;i<result.length;i++)
			result[i]=array[i];
		return result;
	}
	public static Character[] box(char[] array)
	{
		Character[] result=new Character[array.length];
		for(int i=0;i<result.length;i++)
			result[i]=array[i];
		return result;
	}
	public static Byte[] box(byte[] array)
	{
		Byte[] result=new Byte[array.length];
		for(int i=0;i<result.length;i++)
			result[i]=array[i];
		return result;
	}
	public static Short[] box(short[] array)
	{
		Short[] result=new Short[array.length];
		for(int i=0;i<result.length;i++)
			result[i]=array[i];
		return result;
	}
	public static Float[] box(float[] array)
	{
		Float[] result=new Float[array.length];
		for(int i=0;i<result.length;i++)
			result[i]=array[i];
		return result;
	}
	public static Object[] box(Object array)
	{
		if(array instanceof int[])
			return box((int[])array);
		else if(array instanceof long[])
			return box((long[])array);
		else if(array instanceof double[])
			return box((double[])array);
		else if(array instanceof boolean[])
			return box((boolean[])array);
		else if(array instanceof char[])
			return box((char[])array);
		else if(array instanceof byte[])
			return box((byte[])array);
		else if(array instanceof short[])
			return box((short[])array);
		else if(array instanceof float[])
			return box((float[])array);
		else
			throw new ClassCastException("It's not a primitive array: "+array+".");
	}
}
