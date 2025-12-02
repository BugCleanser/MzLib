#import "../../../lib/lib.typ": *;

#set document(title: "Option类")

#show: template

#title()

表示一个可空对象，类似J8+的`Optional`，并且可与`Optional`相互转换，旨在减少lambda的使用

命名和用法则更像Rust的`Option`

= 基本用法

== 实例化

```java
Option<String> s = Option.some("Hello, world!");
Option<String> n = Option.none();
```

== 匹配

```java
for(String str: s) // 若s非空，则执行该块代码，取其值str
{
    System.out.println("some: "+str);
}
```

```java
if(s.isNone()) // s为空时执行
{
    System.out.println("none");
}
```

= 与可空对象转换

```java
@Nullable String str = awa;
Option<String> opt = Option.fromNullable(str);
```

若`str`为`null`，则得到`Option.none()`，否则得到`Option.some(str)`

```java
String s1 = opt.unwrapOr("default"); // 若opt非空，则得到其值，否则得到"default"
String s2 = opt.toNullable(); // 等价于unwrapOr(null)
```

= 与Optional获得

```java
Optional<String> opt = Optional.of("Hello, world!");
Option<String> op = Option.fromOptional(opt);
```

```java
Optional<String> opt = op.toOptional();
```

= 从wrapper转换

有时wrapper包装的对象可空，使用需要isPresent()额外判断

为严谨和简便，将其包装为Option

```java
WrapperObject wrapper = ...;
Option<WrapperObject> opt = Option.fromWrapper(wrapper);
```

当包装非空时，得到`Option.some(wrapper)`；否则得到`Option.none()`