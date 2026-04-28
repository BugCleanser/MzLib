#import "/lib/lib.typ": *;
#set raw(lang: "java");
#let title = [类型安全];
#show: template.with(title: title);

*类型安全*指的是利用*类型系统*进行*静态检查*而避免错误在运行时*传播*，这使你*优雅*地coding

进行类型不安全的操作时应当*严格限制使用范围*，以免污染。

什么是*类型不安全*的：

- *强制转换*

- *原始使用*泛型类，例如直接使用`List`而不指定泛型参数

- 引用数组自动*转换*为`Object[]`：这是一个历史遗留问题，并且数组不能良好*兼容*泛型，所以请尽量使用`List`代替数组

如果您的代码中大量存在以上情况，那真是*非常糟糕*的

= 空安全

Java不像Kotlin一样原生在类型上区分*可空*和*非空*，需要使用空安全注解或`Optional`，详见#link("null-safety")[“空安全”]

= 可变性

Java/Kotlin没有像Cpp/Rust中```cpp const```/```rust mut```的类型修饰符，这是个问题

例如，当被要求提供一个数据对象时，我们经常会纠结于是否应当*事先拷贝*。
或者说，我们难以知道消费者是否需要*修改*我们提供的对象，如果是，我们可能需要*提供副本*以免我们持有的源数据被修改

如果*类型*上能直接指定*可变性*就好了

现在我们需要区分*可变*数据类型和*不可变*数据类型。简便起见，建议使用*继承*的方式，即不可变数据类中创建一个*内部子类*`Mut`：
```java
public class MyData
{
    protected int value;
    public MyData(int value)
    {
        this.value = value;
    }
    public int getValue()
    {
        return this.value;
    }
    // TODO: hashCode, equals, toString

    public static class Mut extends MyData
    {
        public Mut(int value)
        {
            super(value);
        }
        public void setValue(int value)
        {
            this.value = value;
        }
    }
}
```
*继承*使你不需要将各种东西都写两遍，*转换*为不可变类型也不存在*开销*

但要注意：

- *不要*将不可变类型*强转*回其`Mut`版本

- 数据对象作为*只读*版本存在期间，不要修改它（就像Rust的*不可变借用*一样）

如果您认为它是*只读*而非*不可变*的话，忽略上面这两点

= 泛型通配符

为增加灵活性，在*类型安全*的基础上，Java提供了泛型通配符`?`，只能用于泛型

例如`List<?>`表示*任意*`List<T>`；`List<? extends S>`表示*任意*`List<T>`，其中`T`是`S`的*子类型*

这里的*任意*指的是*满足条件*的类型都是它的*子类型*，例如`List<String>`是`List<?>`的*子类型*；`List<Class<?>>`是`List<? extends Type>`

注意除此之外的泛型并不自动拥有父子关系，例如`List<String>`并*不是*`List<Object>`的子类型；
而`List<? extends String>`是`List<? extends Object>`（`List<?>`）的子类型，因为`? extends String`显然均*满足*`? extends Object`的*条件*

另外，通配符仅对所在*一层*的泛型有效，例如`List<Set<?>>`中的`?`是对于`Set`而非`List`，例如`List<Set<String>>`和`List<HashSet<?>>`均*不是*其*子类型*。
若确实想表示它们的*公共父类型*，使用`List<? extends Set<?>>`，其表示*任意*的`List<T>`，其中`T`是*任意*`Set<E>`的*子类型*

总的来说，通配符为满足条件的*一层*泛型构造公共父类型

= 只读容器

以`List`为例，你一定会认为*只读*`List<String>`自动转换为*只读*`List<Object>`是很合理的吧，但*大部分人其实不知道怎么表示*

不像Kotlin那样有专门的只读```kotlin List<out T>```，Java只有一个类来表示容器，那么究竟如何表示只读的`List<T>`

答案是`List<? extends T>`，您可能需要*稍加思考*来理解为何这样做。或者你也可以不必按Kotlin的“只读”来理解，直接按照Java泛型的字面意思即可

```java
List<String> list = new ArrayList<>(); // 一个可变列表
List<? extends String> readOnly = list; // 显然可以转换
List<? extends Object> parent = readOnly; // 也是显然可以转换的
```

虽然仍有*类型安全*的方式*修改*这个容器，但一般认为这样就够了

总之使用`List<? extends T>`表示`T`的只读列表，其它容器同理
