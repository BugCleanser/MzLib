#import "./../../lib/template.typ": *

#set document(title: [文本组件])

#show: template

文本组件`Text`是MC富文本的基本单元，包括样式、颜色、`hoverEvent`和`clickEvent`，文本组件的实例可以嵌套

通过此教程方式创建出来的`Text`实例均可被修改，高版本中可能存在不可修改的`Text`

= 使用旧接口创建

使用`Text`类的静态方法创建，得到`Text`对象。例如

```java
Text t1 = Text.literal("Hello, world!");
Text t2 = Text.translatable("key.key", 1);
Text t3 = Text.score("name", "objective");
Text t4 = Text.selector("@s");
Text t5 = Text.objectAtlasV2109(Identifier.minecraft("blocks"), Identifier.minecraft("key"));
Text t6 = Text.objectPlayerV2109(GameProfile.Description.textureUrl("url"), true);
```

若要进行更多编辑，需要调用方法`as`转为子类，此时建议直接使用子类方法创建实例

未知实例若`as`转换成功也不代表一定符合类型，可以使用`Text#getType`判断类型

```java
if(text.getType()==Text.Type.LITERAL)
    l = text.as(TextLiteral.FACTORY).getLiteral();
```

= 类型

== literal

字面文本，你写什么就是什么（

创建实例

```java
TextLiteral text = TextLiteral.newInstance("Hello World");
```

获取内容

```java
String content = text.getLiteral();
```

== translatable

翻译件，根据客户端的语言文件进行显示，语言文件中可包含若干占位符

创建实例

```java
TextTranslatable text = TextTranslatable.newInstance("mine.key.1", "arg1", 2);
```

`args`可以包含数字、字符串、其它文本组件，用于替换文本中的占位符

从1.19.4开始组件可以包含`fallback`，当客户端的语言文件找不到`key`对应的文本时则使用`fallback`字段作为文本

```java
TextTranslatable text = TextTranslatable.newInstanceV1904("mine.key.1", "Hello %s World %s", 1, 2);
```

获取内容

```java
String key = text.getKey();
List<Object> args = text.getArgs();
```

== score

代表计分板中的一个值，由服务端处理

创建实例

```java
TextScore text = TextScore.newInstance(name, objective);
```

其中`name`是选择器或玩家名称或UUID，只能指向一个实体

获取内容

```java
String name = text.getName();
String objective = text.getObjective();
```

== selector

选择器指向若干个实体，显示实体信息，由服务端处理

创建实例

```java
TextSelector text = TextSelector.newInstance(selector);
```

若匹配多个实体，则在中间插入分隔符，默认为灰色逗号，从1.17开始可指定分隔符

```java
TextSelector text = TextSelector.newInstanceV1700(selector, separator);
```

获取内容

```java
String selector = text.getSelector();
Option<Text> separatorV1700 = text.getSeparatorV1700(); // V1700
```

== keybindV1200

1.12新增组件，指向客户端设定的某个快捷键

创建实例
```java
TextKeybindV1200 text = TextKeybindV1200.newInstance(key);
```
其中`key`是一个字符串表示#link("https://zh.minecraft.wiki/w/%E6%8E%A7%E5%88%B6#%E5%8F%AF%E8%AE%BE%E7%BD%AE%E7%9A%84%E9%94%AE%E4%BD%8D")[按键的本地化键名]

获取内容
```java
String key = text.getKey();
```

== nbtV1400

1.14新增组件，指向目标nbt中的某个元素，由服务端处理

暂未实现构造器和内容获取

== objectV2109

1.21.9新增组件，用于在文本中插入图标

包含两种类型：atlas、player

获取类型

```java
TextObjectV2109.Type type = text.getObjectType();
```

=== atlas

创建实例

```java
TextObjectV2109 text = TextObjectV2109.atlas(atlas, sprite);
```

其中`atlas`为图集，如`"blocks"`；`sprite`为资源id

=== player

创建实例

```java
TextObjectV2109 text = TextObjectV2109.player(gameProfile, hat);
```

其中`gameProfile`是玩家档案描述，指向玩家皮肤；`hat`代表是否渲染帽子

= 样式

TODO

== 颜色

TODO

== hoverEvent

TODO

== clickEvent

TODO

= 内部细节

- 1.16前，`TextStyle`内部可变
- 1.16起，`TextStyle`内部不可变，使用with开头的方法创建新的`TextStyle`对象
- 1.19前，`Text`使用继承关系，`Text`为基类，内部可变
- 1.19起，`Text`为接口，默认可变。`Text`包含`style`和`content`，`content`使用继承关系且内部不可变

= 以下为过时的教程

== literal

字面值组件是最常见的组件，包含一个字符串作为其显示内容

创建字面值组件：
```java
Text t1 = Text.literal("Hello, world!");
```

获取字面值组件的文本（字串）：
```java
String str = t1.getLiteral(); // 若t1不是字面值组件，得到null
```

== translatable

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

== keybindV1200

按键绑定组件包含一个按键绑定（#link("https://zh.minecraft.wiki/w/%E6%8E%A7%E5%88%B6#%E5%8F%AF%E8%AE%BE%E7%BD%AE%E7%9A%84%E9%94%AE%E4%BD%8D")[按键的本地化键名]），显示为客户端设置的对应按键

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

== score

计分板组件，显示计分板中的一个值，不常用

开发中，暂不可用

== selector

选择器组件，显示选择器选中的目标实体，不常用

= 颜色

TextColor的实例，若为`null`则使用父组件的颜色或默认颜色

设置组件颜色：
```java
Text t5 = Text.literal("Red text").setColor(TextColor.RED);
```

== fromRgbV1600

从MC1.16开始可以使用RGB颜色

设置RGB颜色：
```java
Text t6 = Text.literal("RGB text").setColor(TextColor.fromRgbV1600(0xABCDEF));
```

= 样式

每种样式用一个Boolean表示，若为`null`则使用父组件的样式或默认样式

设置组件样式：
```java
Text t7 = Text.literal("special text").setBold(true).setItalic(true).setUnderlined(true).setStrikethrough(true).setObfuscated(true);
```

= hoverEvent

TODO

文档待完善

= clickEvent

TODO

文档待完善
