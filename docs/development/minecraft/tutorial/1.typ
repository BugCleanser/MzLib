#import "../../../lib/template.typ": *

#set document(title: [基本结构与约定])

#show: template

#title()

我们的设计哲学与Fabric类似，但我们要支持热加卸载和多版本兼容

这几乎依赖于我们的模块化和包装器

== 模块化

你的插件应该由若干个模块构成，其它东西几乎都应该被注册到模块中，模块卸载时会自动将它们注销

注册是一个耗时操作，一般只在模块加载时注册其它对象

在下一章你将学会创建和加载模块

== 包装器

包装器是指WrapperObject的子类，是对Minecraft类的基本封装

相比被它包装的类，它是一个interface，因此它没有JVM概念的构造器，取而代之的是静态方法newInstance

例如你要新建一个ItemStack实例

```java
ItemStack is = ItemStack.newInstance(Identifier.newInstance("minecraft:diamond"));
```

若要取得被包装的Minecraft的对象，使用getWrapped方法

```java
Object nms = is.getWrapped();
```

不过你一般用不到这样，只应使用包装器来操作这些Minecraft对象。

若你通过某种方法得到了Minecraft的对象，通过它创建一个包装器实例，使用包装器的create方法

```java
Entity entity = Entity.create(nmsEntity);
```

值得注意的是，wrapper不应该使用JVM概念的instanceof和强制转换，因为Wrapper.create返回的永远只是Wrapper的实例

取而代之的是wrapper.isInstanceOf和wrapper.castTo，参数是另一个包装器的create方法引用，例如

```java
if(entity.isInstanceOf(EntityPlayer::create))
{
    EntityPlayer player = entity.castTo(EntityPlayer::create);
    // do something
}
```

有关如何创建一个包装器类型，见#link("./../../core/包装类")[包装器的进阶教程]

== 版本表示和名称约定

为了简单起见，我们使用一个整数表示一个MC版本，即第二位版本号乘100加上第三位版本号

若MC版本1.x.y，用整数表示为x*100+y

例如1.12.2表示为`1202`，1.14表示为`1400`

如果一个元素只在特定的版本段生效，则在标识符后加上v版本段，若有多个版本段，使用两个下划线隔开

一个版本段是一个左开右闭区间[a,b)，表示为`a_b`；[a,+∞)表示为a，(+∞,b)表示为`_b`

例如，`exampleV1300`表示这个元素从1.13开始有效；`exampleV_1400`表示在1.14之前有效；`exampleV1300_1400`表示从1.13开始有效，从1.14开始失效

例如，`exampleV_1300__1400_1600`表示在(-∞, 1.13) ∪ [1.14, 1.16)有效；`exampleV_1600__1903`表示1.16之前和从1.19.3开始都有效，在[1.16, 1.19.3)无效
