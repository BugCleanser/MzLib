# 文本组件

文本组件`Text`是MC富文本的基本单元，包括样式、颜色、`hoverEvent`和`clickEvent`，文本组件的实例可以嵌套

通过此教程方式创建出来的`Text`实例均可被修改，高版本中可能存在不可修改的`Text`

## 使用旧接口创建

使用`Text`类的静态方法创建，得到`Text`对象。例如

```java
Text t1 = Text.literal("Hello, world!");
```

若要进行更多编辑，可能需要调用`castTo`转为其它类型

## 内部细节

- 1.16前，`TextStyle`内部可变
- 1.16起，`TextStyle`内部不可变，使用with开头的方法创建新的`TextStyle`对象
- 1.19前，`Text`使用继承关系，`Text`为基类，内部可变
- 1.19起，`Text`为接口，默认可变。`Text`包含`style`和`content`，`content`使用继承关系且内部不可变

## 以下为过时的教程

### literal

字面值组件是最常见的组件，包含一个字符串作为其显示内容

创建字面值组件：
```java
Text t1 = Text.literal("Hello, world!");
```

获取字面值组件的文本（字串）：
```java
String str = t1.getLiteral(); // 若t1不是字面值组件，得到null
```

### translatable

可翻译组件包含一个翻译键和若干个参数，一般根据客户端的语言文件显示内容

创建可翻译组件：
```java
Text t2 = Text.translatable("item.minecraft.egg");
Text t3 = Text.translatable("pack.nameAndSource", Text.literal("testName"), Text.literal("testSource"));
```

获取可翻译组件的翻译键和参数：
```java
String key = t2.getTranslatableKey();
Text[] args = t2.getTranslatableArgs();
```
（若`t2`不是可翻译组件，`key`和`args`均得到`null`）

### keybindV1200

按键绑定组件包含一个按键绑定（[按键的本地化键名](https://zh.minecraft.wiki/w/%E6%8E%A7%E5%88%B6#%E5%8F%AF%E8%AE%BE%E7%BD%AE%E7%9A%84%E9%94%AE%E4%BD%8D)），显示为客户端设置的对应按键

这种组件从MC1.12开始可用

创建按键绑定组件：
```java
Text t4 = Text.keybindV1200("key.jump");
```

得到按键绑定组件的键名：
```java
String keyKey = t4.getKeybindV1200();
```
（至少MC1.12开始才能调用，若`t4`不是按键绑定组件得到`null`）

### score

计分板组件，显示计分板中的一个值，不常用

开发中，暂不可用

### selector

选择器组件，显示选择器选中的目标实体，不常用

## 颜色

TextColor的实例，若为`null`则使用父组件的颜色或默认颜色

设置组件颜色：
```java
Text t5 = Text.literal("Red text").setColor(TextColor.RED);
```

### fromRgbV1600

从MC1.16开始可以使用RGB颜色

设置RGB颜色：
```java
Text t6 = Text.literal("RGB text").setColor(TextColor.fromRgbV1600(0xABCDEF));
```

## 样式

每种样式用一个Boolean表示，若为`null`则使用父组件的样式或默认样式

设置组件样式：
```java
Text t7 = Text.literal("special text").setBold(true).setItalic(true).setUnderlined(true).setStrikethrough(true).setObfuscated(true);
```

## hoverEvent

TODO

文档待完善

## clickEvent

TODO

文档待完善
