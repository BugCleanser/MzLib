#import "../../lib/template.typ": *

#show: template

= 包装类

包装类是MzLib中非常常用的对象

因为MC的类在不同版本可能有所变化，所以我们必须给它包装一层，包装后你还可以越权访问它的成员

这里介绍包装类的基本用法

== 包装已知类

如果你能直接访问这个类，可以使用`@WrapClass`

这里以ClassLoader为例

```java
// 包装ClassLoader
@WrapClass(ClassLoader.class)
// 包装类必须是interface，并且是WrapperObject的子类
public interface WrapperClassLoader extends WrapperObject
{
    /**
     * 包装器的构造器，需要注解@WrapperCreator用于优化性能
     * 一般直接复制粘贴，然后替换1处和2处为自己的包装类，将3处替换为目标类或任意其已知父类（如Object)
     */
    @WrapperCreator
    static WrapperClassLoader/*1*/ create(ClassLoader/*3*/ wrapped)
    {
        return WrapperObject.create(WrapperClassLoader/*2*/.class, wrapped);
    }

    /**
     * findClass是非public方法，因此对齐包装
     * @WrapMethod("findClass")表示目标方法的名称是findClass
     * 包装方法的返回值类型必须和目标方法的一致，或者是其封装类
     * 没必要写throws
     */
    @WrapMethod("findClass")
    Class<?> findClass(String name) throws ClassNotFoundException;
}
```

若目标类的类名固定且已知，但代码中无法直接访问，可以使用`@WrapClassForName`代替`@WrapClass`
```java
@WrapClassForName("java.lang.ClassLoader")
```

== 使用包装类

如果有一个目标类的实例，可以使用create将其包装为包装类实例，从而访问其成员

```java
// 目标类实例
ClassLoader cl = this.getClass().getClassLoader();
// 创建包装类实例
WrapperClassLoader wcl = WrapperClassLoader.create(cl);
// 调用包装方法或访问字段
wcl.findClass("java.lang.String");
```

== 拓展包装类

包装类可以被继承，当你需要包装它目标类的子类，或者你单纯想要拓展包装类的功能

```java
// 要包装的类和WrapperClassLoader的相同
@WrapSameClass(WrapperClassLoader.class)
// 继承WrapperClassLoader，也可以先显式继承WrapperObject
public interface ExtendedWrapperClassLoader extends WrapperObject, WrapperClassLoader
{
    /**
     * 复制静态方法creator
     * 记得将1处和2处替换为ExtendedWrapperClassLoader
     */
    @WrapperCreator
    static ExtendedWrapperClassLoader/*1*/ create(ClassLoader/*3*/ wrapped)
    {
        return WrapperObject.create(ExtendedWrapperClassLoader/*2*/.class, wrapped);
    }

    /**
     * 这时候可以封装更多方法
     */
    @WrapMethod("resolveClass")
    void resolveClass(Class<?> c);
}
```

如果你已有父封装类的实例，你当然可以拿到目标类的实例然后使用拓展封装类重新封装，从而调用拓展封装类中的方法
```java
ClassLoader cl = this.getClass().getClassLoader();
WrapperClassLoader wcl = WrapperClassLoader.create(cl);
// getWrapped得到被包装的对象，然后使用拓展包装类的create重新封装
ExtendedWrapperClassLoader ewcl = ExtendedWrapperClassLoader.create(wcl.getWrapped());
// 调用拓展封装类中的方法
ewcl.resolveClass(String.class);
```

我们一般简化为castTo方法，参数是包装类的create方法引用，而不是包装类的Class实例

```java
ClassLoader cl = this.getClass().getClassLoader();
WrapperClassLoader wcl = WrapperClassLoader.create(cl);
// castTo将包装对象wcl转换为另一个包装类的对象，请勿使用强制转换
ExtendedWrapperClassLoader ewcl = wcl.castTo(ExtendedWrapperClassLoader::create);
ewcl.resolveClass(String.class);
```

== 包装字段访问器
显然由于我们的包装类是interface无法创建字段，所以我们将字段封装为getter和setter（也可以只封装其中一个）

使用`@WrapFieldAccessor`，若你的方法没有参数，代表这是一个getter，否则代表setter，setter的返回值应该为void


```java
@WrapSameClass(WrapperClassLoader.class)
public interface ExtendedWrapperClassLoader extends WrapperObject, WrapperClassLoader
{
    @WrapperCreator
    static ExtendedWrapperClassLoader create(ClassLoader wrapped)
    {
        return WrapperObject.create(ExtendedWrapperClassLoader.class, wrapped);
    }

    // 包装parent字段的getter和setter
    @WrapFieldAccessor("parent")
    void setParent(ClassLoader parent);
    // 返回值上的ClassLoader换成它的包装类则会自动进行包装，getter的参数也可以这样
    @WrapFieldAccessor("parent")
    ExtendedWrapperClassLoader getParent();
}
```

```java
// 设置一个ClassLoader的parent的parent
ExtendedWrapperClassLoader.create(this.getClass().getClassLoader()) // 包装ClassLoader
        .getParent() // 这样仍然得到一个包装过的ClassLoader
        .setParent(null);
```

== 包装构造器

包装构造器使用`@WrapConstructor注解`，返回值必须是当前包装类，构造的实例会自动包装

```java
// 简单包装个Object类
@WrapClass(Object.class)
public interface ExampleWrapper extends WrapperObject
{
    // 记得复制creator
    @WrapperCreator
    static ExampleWrapper create(Object wrapped)
    {
        return WrapperObject.create(ExampleWrapper.class, wrapped);
    }

    // 包装Object的无参构造器
    @WrapConstructor
    ExampleWrapper staticNewInstance();
}
```

对了，你包装的方法必须是非静态的，这样我们才能继承和实现它，一般包装的构造器叫做staticNewInstance

static开头的命名表示它的目标是静态的（构造器我们看成静态方法，这里指的不是`<init>`方法，而是返回实例的构造器）

作为包装类非静态方法，想调用它显然需要一个包装类实例，这样我们可以用create(null)直接创建一个，表示目标实例是null，因为我们调用目标类的静态方法所以不需要目标类的实例

为方便使用，我们可以再把它封装成静态方法

```java
// 然后我们自己封装成静态方法
static ExampleWrapper newInstance()
{
    // 使用create(null)调用
    return create(null).staticNewInstance();
}
// 先用注解包装成非静态方法
@WrapConstructor
ExampleWrapper staticNewInstance();
```

构造器如此，静态方法和静态字段的访问器也是同理
