# 为Minecraft类创建包装类

众所周知，Minecraft的类和成员名称是经过混淆的，在某些服务端还被使用某些映射表反混淆了，并且在各个MC版本上也有很大差别

因此我们不能直接使用注解@WrapClass、@WrapClassForName、@WrapMethod和@WrapFieldAccessor，而要使用它们的变体

我们可以通过映射表找到它们的真正名称

在MzLib中我们使用yarn表，在1.14之前我们使用fabric legacy的yarn表

## 版本表示和名称约定

为了简单起见，我们使用一个整数表示一个MC版本，即第二位版本号乘100加上第三位版本号

若MC版本1.x.y，用整数表示为x*100+y

例如1.12.2表示为1202，1.14表示为1400

如果一个元素只在特定的版本段生效，则在标识符后加上v版本段，若有多个版本段，使用两个下划线隔开

一个版本段是一个左开右闭区间[a,b)，表示为a_b；[a,+∞)表示为a，(+∞,b)表示为_b

例如，exampleV1300表示这个元素从1.13开始有效；exampleV_1400表示在1.14之前有效；exampleV1300_1400表示从1.13开始有效，从1.14开始失效

例如，exampleV_1300__1400_1600表示在1.13之前有效，在1.14开始1.16之前也有效；exampleV_1600__1903表示1.16之前和从1.19.3开始都有效，在1.16开始和1.19.3之前无效

## 元素开关与@VersionRange

元素开关ElementSwitcher用来表示一个元素在当前环境是否应该被启用

这里的元素指的是类或者类的成员

如果元素应当被启用但无法生效，则会抛出异常，因此我们需要准确标出元素生效的环境

使用@VersionRange注解表示一个元素在某个MC版本段内生效

若使用多个@VersionRange，则在任意一个版本段内都生效

例如

```java
@WrapConstructor
@VersionRange(begin=1300, end=1400)
ExampleWrapper staticNewInstance();
```

## 包装类注解变体

使用@WrapMinecraftClass、@WrapMinecraftMethod@WrapMinecraftFieldAccessor

使用这些注解我们不再需要提供类名，而是要提供这个类在各个版本段的yarn表中的名称

注解会通过映射表关系帮我们自动找到类的实际名称

例如

```java
// 1.13之前，MC中不存在这个类
// 从1.13开始，1.14截止，它在yarn(legacy)表中的名称是net.minecraft.item.Itemable
// 从1.14开始，它在yarn表中的名称是net.minecraft.item.ItemConvertible
@WrapMinecraftClass({@VersionName(name="net.minecraft.item.Itemable", begin=1300, end=1400), @VersionName(name="net.minecraft.item.ItemConvertible", begin=1400)})
public interface ItemConvertibleV1300 extends WrapperObject
{
    @WrapperCreator
    static ItemConvertibleV1300 create(Object wrapped)
    {
        return WrapperObject.create(ItemConvertibleV1300.class, wrapped);
    }
    
    // @WrapMinecraftMethod也是同理
    @WrapMinecraftMethod({@VersionName(name="getItem",end=1400),@VersionName(name="asItem",begin=1400)})
    Item asItem();
}

```