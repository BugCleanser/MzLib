#import "/lib/lib.typ": *;
#set raw(lang: "java");
#let title = [Option类];
#show: template.with(title: title);



表示一个可选对象，类似Rust

#cardAttention[
    此类并不是`Optional`的直接代替品

    有*注解*和`Optional`两种方式实现空安全，若您选择前者则请优先使用`@Nullable`来表示可选

    详见#link("../null-safety")[“空安全”]
]

此类兼容*可空*，而不像`Optional<T>`只能直接代替`@Nullable T`，其中`T`非空

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

#cardAttention[
    仅当`T`非空时你才可以这样做，等效于`Optional<T>`
]

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

#cardAttention[
    仅当`T`非空时你才可以这样做
]

```java
Optional<String> opt = Optional.of("Hello, world!");
Option<String> op = Option.fromOptional(opt);
```

```java
Optional<String> opt = Option.toOptional(op);
```

= 从wrapper转换

有时wrapper包装的对象可空，使用需要isPresent()额外判断

为严谨和简便，将其包装为Option

```java
WrapperObject wrapper = ...;
Option<WrapperObject> opt = Option.fromWrapper(wrapper);
```

当包装非空时，得到`Option.some(wrapper)`；否则得到`Option.none()`