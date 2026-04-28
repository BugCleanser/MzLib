#import "/lib/lib.typ": *;
#set raw(lang: "java");
#let title = [空安全];
#show: template.with(title: title);

#cardTip[
    Kotlin用户*无需阅读*此文档
]

Java的类型系统并不原生区分`null`，在Java中实现空安全一般有两种方式：

+ 类似Rust：`Optional`

+ 类似Kotlin的`T?`：使用空安全注解如`@Nullable T`

我们现在更推荐第二种方式

= 为什么不使用`Optional`

- Java标准库早已大量使用`null`且将来不会更改，例如`Map#get`
  而`Optional`无法良好兼容空安全注解：例如将可空泛型`T`置于`Optional<T>`是不正确的

- 由于JVM的方法重载基于泛型擦除，这会导致你的`Optional<T1>`和`Optional<T2>`在描述符上完全相同，容易导致冲突。
  这也就是为什么当你把`Optional`作为参数类型时会得到Idea的警告

- Kotlin是非常常用的JVM语言，其与空安全注解的适配性更佳

= begin

通常认为JetBrains的注解是更好的选择：
```kotlin
dependencies {
    compileOnly("org.jetbrains:annotations:latest.release")
}
```

#cardAttention[
    如果你使用javax或Jakarta的注解，其不能使用于泛型等，限制非常大
]

首先为你的每个包（包括每个子包）创建`package-info.java`，为其加上`@NotNullByDefault`注解，这样则无需在所有地方都写`@NotNull`而默认非空

注意你的泛型声明也变成默认非空的了：`<T>`相当于`<T extends @NotNull Object>`若要允许可空泛型则需改为`<T extends @Nullable Object>`

= 数组

同样的，在类型后加`[]`表示其数组类型，如`@Nullable Object[]`表示元素可空的数组

数组自身的注解标在`[]`之前，如`Object @Nullable[]`表示自身可空的数组

同理，`@Nullable Object @Nullable[]`表示自身和元素皆可空的数组

= `Box`

对于可空泛型`<T extends @Nullable Object>`，表示其可选值则不能使用`@Nullable T`，否则无法区分不存在和存在空值

你可以包一层为`@Nullable Box<T>`，避免`@Nullable`直接应用于`T`

```
// 假设T为@Nullable Object
@Nullable Box<@Nullable Object> opt1 = null; // 不存在
@Nullable Box<@Nullable Object> opt2 = Box.of(null); // 存在null
```

但这个类型可能看着意义不明，所以我们提供了`Option`来代替`@Nullable Box`（注意不能换成`Optional`）：
```java
// 假设T为@Nullable Object
Option<@Nullable Object> opt1 = Option.none(); // 不存在
Option<@Nullable Object> opt2 = Option.some(null); // 存在null
```
详见#link("util/option")[`Option`]

对于`Map<K, V>`，其`get`返回的`null`有特殊含义，所以`V`应当非空，可空泛型也可用`Box`包一层
```
// V可以可空，也可以非空
static <K, V extends @Nullable Object> V getPresent(Map<K, Box<V>> map, K key)
{
    @Nullable Box<V> result = map.get(key);
    if(result != null) // 如果存在
        return result.get();
    else
        throw new NoSuchElementException();
}
```
当然若`T`一定是`@Nullable V`，您可以使用`Optional<V>`代替`Box<T>`（不推荐）：
```
// 这样返回值就必须可空了
static <K, V> @Nullable V getPresent(Map<K, Optional<V>> map, K key)
{
    // 非常不推荐的类型
    @Nullable Optional<V> result = map.get(key);
    if(result != null) // 如果存在
        return result.orElse(null);
    else
        throw new NoSuchElementException();
}
```
总之混用`@Nullable`和`Optional`是非常不推荐的，建议全面使用空安全注解