package mz.mzlib.example;

public class Test
{
	public static class Foo
	{
		public Foo()
		{
		}
		public final void f()
		{
			p();
		}
		
		private void p()
		{
		
		}
	}
	
	
	public static void main(String[] args) throws Throwable
	{
		new Foo().f();
	}
}
