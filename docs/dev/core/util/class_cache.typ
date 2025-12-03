#import "../../../lib/lib.typ": *;

#set raw(lang: "java");

#set document(title: `ClassCache`);

#show: template;

#title();

即使`Class`对象不常被销毁，资源的回收仍然是有必要的

假设你需要缓存`Class`相关的对象，例如其中的`Method`，显然这个对象本身也持有对`Class`的引用。

```java
Class clazz = Class.forName("com.example.MyClass");
Method method = clazz.getMethod("myMethod");
WeakHashMap<Class, Method> cache = new WeakHashMap<>();
cache.put(clazz, method);

clazz = null;
method = null;
System.gc();
```

此处的`WeakHashMap`无法发挥作用，因为`WeakHashMap`强引用了value，而value又强引用了key，导致key无法被回收。

解决此问题的一个思路是使用`SoftReference`，但软引用可能导致额外的内存占用。因此我们使用`ClassCache`

`ClassCache`是一个针对key为`Class`的特殊解决方案，内部使用弱引用但仍然可以正常回收，您无需担心value对key的强引用。

```java
ClassLoader cl = ...;
ClassCache<Class<?>, Method> cache = new ClassCache<>(c -> c.getMethod("myMethod")); // 构造方法传入计算value的方法
System.out.println(cache.get(Class.forName("com.example.MyClass", false, cl))); // 第一次get时进行计算并缓存
cl = null; // 去除所有外部强引用
System.gc(); // gc后能正确释放缓存
```

详见测试`mz.mzlib.util.TestClassCache`
