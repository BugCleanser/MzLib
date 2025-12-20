#import "/lib/lib.typ": *;
#set raw(lang: "java");
#let title = [包装器];
#show: template.with(title: title);

本章将完整地教你包装一个Minecraft类并提供API，您可以不用学习#link(meta.root+"dev/core/util/wrapper")[core中的包装器教程]

Fabric、Forge等mod loader允许用户直接访问Minecraft的类，除此之外还有两种提供API的方式：

- 直接继承

  Sponge选择让Minecraft类直接实现API接口，这需要修改Minecraft类的继承结构

- 包装类

  Bukkit选择自己写一个包装类（就是那些名称以"Craft"开头的类），并将功能委托给Minecraft类，包装类实现了API的接口

作为一个轻量且高兼容性高拓展性的库，我们也选择包装类的方案，但我们使用自动实现的包装器

= 包装器的优势

我们只需声明API的接口，并加上正确的注解，包装器即可自动生成实现类，而无需像Bukkit一样手写那些"Craft"类

对方法、字段、构造器的封装也通过注解自动完成

私有成员也能"直接"访问而无需修改权限

相比直接访问Minecraft类，这样更加安全： \
因为有些Minecraft类不是每个版本都存在，将不存在的类用于你的参数、返回值类型等地方会导致你自己的类无法加载

= yarn表

对于Minecraft类的类名和成员名，我们统一使用#link("https://github.com/FabricMC/yarn/")[yarn表]，即Fabric使用的反混淆表

在下面的用法中，名称都会自动转换为所在平台和版本中的真实类或成员名称

对于Minecraft1.14之前的版本，使用#link("https://github.com/Legacy-Fabric/yarn")[Legacy Fabric的yarn表]

= 创建包装类

以`NbtCompound`类为例，在MzLib中的此包装类是`mz.mzlib.minecraft.nbt.NbtCompound`，此处我们先叫"MyNbtCompound"

```java
@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.nbt.NbtCompound", end = 1400),
    @VersionName(name = "net.minecraft.nbt.CompoundTag", begin = 1400, end = 1605),
    @VersionName(name = "net.minecraft.nbt.NbtCompound", begin = 1605)
})
public interface MyNbtCompound extends WrapperObject
{
    WrapperFactory<MyNbtCompound> FACTORY = WrapperFactory.of(MyNbtCompound.class);
}
```

其中`@WrapMinecraftClass`就是包装类的标志注解，表示包装Minecraft中的一个类，值是各版本段的类名，`name`是yarn表中的名称，我们可以查看#link("https://github.com/FabricMC/yarn/blob/1.21/mappings/net/minecraft/nbt/NbtCompound.mapping")[此类在1.21的yarn表]

#cardTip[
  在GitHub上查看yarn表时，修改地址栏中的版本号即可查看其它版本的映射表
]

包装类必须是`interface`，并直接或间接地继承我们的基类`WrapperObject`，`WrapperObject`本身也是`Object`的包装类

包装类中第一行代码是固定格式的`FACTORY`字段

#cardInfo[
  `FACTORY`包含了包装类的所有信息，例如如何构造，判断实例。至于为什么不用`Class`，当然是因为我们无法往`Class`对象中塞自己的信息，如果用`Map<Class, ?>`储存则每次`get`会造成无法内联的开销
]

#cardTip[
  每个包装类开头都有`FACTORY`字段，您每次可以直接跳转到父类中复制，*然后将其中两处修改为你自己的类名*
]

现在你就有一个可用的包装类了，不需要从外部注册和配置，一切都自动完成。

尝试用它包装一个Minecraft对象：

```java
NbtCompound nbt = NbtCompound.newInstance();
MyNbtCompound mine = nbt.as(MyNbtCompound.FACTORY);
```

先用MzLib的api创建一个实例，得到的对象当然是用MzLib的类包装的。将其转换为你自己的包装对象，*不要使用强制转换*，使用`as`方法并传递你的`FACTORY`

#cardTip[
  Kotlin用户可以使用这些方法的别名：
  - `as`: `castTo`
  - `is`: `isInstanceOf`
  - `asOption`: `tryCast`
]

当然它现在还没什么功能，当你至少包装了一个成员它才算有点用

= 包装非静态方法

让我们从非静态方法开始，在包装类中声明对应方法并注解`@WrapMinecraftMethod`

```java
// in class MyNbtCompound
@WrapMinecraftMethod(@VersionName(name = "put"))
void put(String key, NbtElement value);
```

Hey，发现目标方法中有一个参数类型也是Minecraft类，此时放心使用其包装类`NbtElement`代替，我们会自动拆装箱，返回值也同理

#cardAttention[
  请关注泛型擦除后的类型，例如别把`List<nms>`也用`List<wrapper>`代替了，我们可不会自动帮你处理泛型。

  方便起见，这种情况请使用`List<Object>`
]

注解中仍然写的是yarn表中的方法名称，而你的接口方法名称可以不必与yarn的一致（你认为合理即可）

现在我们可以使用它

```java
MyNbtCompound mine = NbtCompound.newInstance().as(MyNbtCompound.FACTORY);
mine.put("key1", NbtInt.newInstance(114514)); // 调用包装方法
```

= 包装非静态字段访问器

在接口中我们只能写方法，所以字段的访问也分成getter和setter

注解均使用`@WrapMinecraftFieldAccessor`，我们通过参数数量自动区分getter和setter

```java
// in class MyNbtCompound
@WrapMinecraftFieldAccessor({
    @VersionName(name = "data", end = 1400), // legacy yarn中的名称
    @VersionName(name = "field_11515", begin = 1400) // yarn中的名称不稳定，直接用中间表
})
Map<String, Object> getData0();
@WrapMinecraftFieldAccessor({ // 同上
    @VersionName(name = "data", end = 1400),
    @VersionName(name = "field_11515", begin = 1400)
})
void setData0(Map<String, Object> value);
```

getter的返回值、setter的参数类型即为字段的类型或其包装类

此处泛型中的Minecraft类我们先用Object代替

```java
MyNbtCompound mine = NbtCompound.newInstance().as(MyNbtCompound.FACTORY);
mine.put("key1", NbtInt.newInstance(114514));
System.out.println(
    mine.getData0() // 调用包装的getter
);
```

= 包装静态成员

静态成员其实也使用相同的注解，但是，我们可不能在接口里声明抽象的静态方法吧

因此先将其包装为非静态方法，但名称以"static\$"开头

```java
// in class MyNbtCompound
@VersionRange(begin = 1602) // 仅1.16.2+
@WrapMinecraftFieldAccessor(@VersionName(name = "CODEC"))
CodecV1600<?> static$codecV1602(); // field CODEC getter
```

那么调用它需要一个包装类的实例，这个实例通过`FACTORY.getStatic()`获得

```java
CodecV1600<?> codec = MyNbtCompound.FACTORY.getStatic().static$codecV1602();
```

#cardInfo[
  实际上`FACTORY.getStatic()`得到的是一个对`null`的包装对象

  就像你反射访问静态方法/字段时`obj`填的是`null`或任意对象对吧
]

然后你再手动地将其封装为静态方法

```java
// in class MyNbtCompound
static CodecV1600<?> codecV1602() // 手动封装
{
    return FACTORY.getStatic().static$codecV1602(); // 通过FACTORY.getStatic()调用
}
@VersionRange(begin = 1602)
@WrapMinecraftFieldAccessor(@VersionName(name = "CODEC"))
CodecV1600<?> static$codecV1602();
```

== 包装构造器

此处的构造器指的是生产对象的完整函数，而不是返回`void`的`<init>`方法，当然它们是一一对应的

构造器也认为是一个静态方法，包装成`static$of`再手动封装为`of`静态方法，包装构造器使用注解`@WrapConstructor`

```java
// in class MyNbtCompound
static NbtCompound of() // 手动封装为static方法
{
    return FACTORY.getStatic().static$of(); // 通过FACTORY.getStatic()调用
}
@WrapConstructor
NbtCompound static$of(); // 包装为非静态方法
```

#cardInfo[
  之前MzLib使用`newInstance`作为包装构造器的名称而不是`of`

  但这太冗长了，现在更推荐`of`
]

= 拓展包装类

MzLib已有的包装类可能暂未包装你所需的成员，你大可以补充它并给我们交pr。可能是嫌麻烦吧，也可能是用于临时测试，现在你可能暂时不想这样做，而希望直接拓展它。

其实你可以直接继承，但记得加上`@WrapSameClass`，不然我们怎么知道你要包装的是同一个类而不是它的子类呢

```java
@WrapSameClass(Entity.class) // 与已有类包装同一个Minecraft类
public interface MyEntity extends Entity // 继承已有包装类
{
    WrapperFactory<MyEntity> FACTORY = WrapperFactory.of(MyEntity.class);
    // sth.
}
```

现在你可以在自己的包装类里包装更多Minecraft的成员了

== 转换

原有继承结构没有改变，显然你*不能直接强转*到自己的包装类上，仍然使用`as`进行重新包装

```java
Entity entity = ...;
MyEntity mine = entity.as(MyEntity.FACTORY);
Entity e = mine; // 当然向上转型是可以的，如果你继承了
```

= `@VersionName`

此注解表示一个MC版本段内的名称（标识符）

版本段是一个左闭右开区间，详见#link("../index#版本表示和名称约定")[版本表示和名称约定]

属性定义：

- name 名称

- begin 版本区间的下界（左端点，*包括*）

- end 版本区间的上界（右端点，*不包括*）

- remap 是否进行映射，默认`true`

使用数组以表示一个类或成员在不同版本段的名称

```java
@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.entity.player.ServerPlayerEntity", end = 1400), // mc 1.14前
    @VersionName(name = "net.minecraft.server.network.ServerPlayerEntity", begin = 1400) // mc 1.14起
})
```
