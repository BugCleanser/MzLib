package mz.mzlib.example;

import mz.mzlib.util.compound.Compound;
import mz.mzlib.util.compound.CompoundOverride;
import mz.mzlib.util.compound.Delegator;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;
import org.junit.jupiter.api.Test;

public class ExampleCompound
{
    public static class Foo
    {
        int var;
        
        public Foo(int var)
        {
            this.var = var;
        }
        
        public void f()
        {
            System.out.println("Hello World");
        }
        
        public void g()
        {
            f();
            System.out.println(this.var);
        }
    }
    
    @WrapClass(Foo.class)
    public interface WrapperFoo extends WrapperObject
    {
        WrapperFactory<WrapperFoo> FACTORY = WrapperFactory.find(WrapperFoo.class);
        
        @WrapMethod("f")
        void f();
        
        @WrapMethod("g")
        void g();
    }
    
    @Compound
    public interface CompoundFoo extends WrapperFoo, Delegator
    {
        WrapperFactory<CompoundFoo> FACTORY = WrapperFactory.find(CompoundFoo.class);
        
        static CompoundFoo newInstance(Foo delegate)
        {
            return Delegator.newInstance(FACTORY, delegate);
        }
        
        @CompoundOverride(parent=WrapperFoo.class, method="f")
        default void f()
        {
            System.out.println("Fuck you World");
        }
    }
    
    @Test
    public void test() throws Throwable
    {
        CompoundFoo.newInstance(new Foo(114)).g();
    }
}
