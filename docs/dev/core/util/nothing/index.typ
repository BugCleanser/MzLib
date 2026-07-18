#import "/lib/lib.typ": *;
#set raw(lang: "java");
#let title = [Nothing];
#show: template.with(title: title);

Nothing是一个用于修改（注入）字节码指令的工具

要点包括：
- 目标查找（通过包装器）
- 指令定位
- 返回值
- 访问局部变量（参数）
- 访问栈（顶）
- （条件）跳转
- 异常捕获

= 开始

Nothing依赖于#link("../../module")[模块]和#link("../wrapper/index")[包装器]

要想注入一个类，首先需要这个类的包装器，同时继承`Nothing`接口

```java
class Foo // 目标类
{
    void func(String arg) // 待注入的方法
    {
        System.out.println("Foo: " + arg);
    }
}

@WrapClass(Foo.class)
interface NothingFoo extends WrapperObject, Nothing // Foo的包装器，同时注入
{
    WrapperFactory<WrapperObject> FACTORY = WrapperFactory.of(WrapperObject.class);

    @WrapMethod("func")
    void func(String arg); // 包装方法
}
```

通常来讲，我们可能会*分离*设计

让包装器专注于包装，而注入器专注于注入

```java
// 目标类 Foo

@WrapClass(Foo.class)
interface WrapperFoo extends WrapperObject // 包装器
{
    WrapperFactory<WrapperFoo> FACTORY = WrapperFactory.of(WrapperFoo.class);

    @WrapMethod("func")
    void func(String arg); // 包装方法
}

@WrapSameClass(WrapperFoo.class) // 包装的目标与包装器相同
interface NothingFoo extends WrapperObject, Nothing // 注入器，直接继承包装器
{
}
```

此时`NothingFoo`仍然是一个继承了`Nothing`的包装器，只不过包装方法丢到了父包装器，其可以专注于注入

然后这个注入器`class`需要被*注册*到模块中，注册即生效，直到被*注销*

```java
// 一个被加载的模块
class MyModule extends MzModule
{
    @Override
    public void onLoad()
    {
        this.register(NothingFoo.class); // 注册注入器class
    }
}
```

好了让我们添加对目标方法的注入，就注入到*开头*好了

```java
@WrapSameClass(WrapperFoo.class)
interface NothingFoo extends WrapperObject, Nothing
{
    @NothingInject(name = "func", locator = "stay", type = NothingInjectType.INSERT_BEFORE)
    default void func$begin()
    {
        System.out.println("Inject! " + this);
    }
}
```

这样就注入了一个*打印*，调用原方法就发现`this`被打印了出来

```java
new Foo().func("hello");
```

```output
Inject! Foo@114514
Foo: hello
```

`@NothingInject`表示这是个注入

其中`name`是*包装方法*的名称

`locator`是定位方法，位置默认在开头（首个指令），我们先使用`stay`停留于此即可。详见#link("locate")[定位]

`type = NothingInjectType.INSERT_BEFORE`表示你要注入到目标指令的*前面*

这样我们就将这个方法注入到了目标方法中（最前面）

非静态的要写*方法体*因此声明为`default`，如果你不需要使用`this`也可以直接写`static`

由于只是目标方法的一部分因此我们叫做`func$begin`，*命名无强制要求*

*参数*我们先留空

= 表示目标方法

*必须*先*包装*目标方法，就像上述示例一样

然后`@NothingInject`注解内写的`name`和`params`都是*包装方法*的，而不是目标方法

若不设置`params`，则仅根据`name`找到*唯一*的包装方法

= 返回值

刚才我们将指令注入到的方法开头，而方法*原先的指令*仍在稍后执行（除非我们丢出异常）

现在我们换个需求，让目标方法直接*返回*而不是继续执行

将返回值类型放在`Box`中，由于`void`是原始类型，我们用`@AdapterPrimitive Void`代替`void`

```java
@NothingInject(name = "func", locator = "stay", type = NothingInjectType.INSERT_BEFORE)
default @Nullable Box<@AdapterPrimitive Void> func$begin()
{
    System.out.println("Done!");
    return Box.of(null); // 返回值放在Box中
}
```

你的返回值类型为`@Nullable Box<R>`时，返回`Box<R>`则代表让目标方法*返回*`R`，返回`null`代表让目标方法*继续执行*；

若你的返回值类型为`void`，则代表始终*继续执行*

我们也可以根据*不同的条件*来决定是否结束

```java
@NothingInject(name = "func", locator = "stay", type = NothingInjectType.INSERT_BEFORE)
default Box<@AdapterPrimitive Void> func$begin()
{
    System.out.println("Inject!");
    if(new Random().nextBoolean()) // 随机进行
        return Box.of(null); // 结束
    else
        return Nothing.proceed(); // 继续执行
}
```

为明确语义，我们使用`Nothing.proceed()`来得到这个`null`，并且*无需注解*`@Nullable`

= 访问局部变量

访问方法的*局部变量*（包括*参数*），我们需要知道它的*类型*和*槽位*

#cardAttention[
    *槽位*不是通常讲的参数索引

    `long`和`double`占用2个槽位，其它类型占用1个

    对于非静态方法和构造器，`this`占用槽位0，参数从槽位1开始

    对于静态方法，参数从槽位0开始
]

不要通过这种方式访问`this`，因为尝试*修改*`this`会引发*校验错误*

若要访问的变量的类型（的适配器）为`T`，则我们就添加一个参数`Box.Mut<T>`，加上注解`@LocalVar(slot)`，其中`slot`是变量占用的（首个）*槽位*

```java
@NothingInject(name = "func", locator = "stay", type = NothingInjectType.INSERT_BEFORE)
default Box<@AdapterPrimitive Void> func$begin(@LocalVar(1) Box.Mut<String> arg)
{
    System.out.println("Inject " + arg.get());
    if(arg.get() != null) // 如果参数非空就结束
        return Box.of(null);
    arg.set("replaced"); // 修改参数
    return Nothing.proceed();
}
```

简单验证：

```java
new Foo().func("hello");
System.out.println("---");
new Foo().func(null);
```

```output
Inject hello
---
Inject null
Foo: replaced
```

只建议通过此种方式访问*参数*，因为其它变量的槽位往往是*不稳定*的（在不同版本）

详见#link("./local-variable")[局部变量]
