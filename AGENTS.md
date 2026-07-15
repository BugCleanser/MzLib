# AGENTS — 快速指南（中文）

完整的中文指南用于引导AI代理在此存储库快速提高效率。下面每一项都已从源码查证。

## 1. 项目概览

- 多模块 Gradle/Kotlin 项目（根目录 `build.gradle.kts`）。4 个模块。
- 同时使用 Java 和 Kotlin：源码大部分为 Java，Kotlin 文件在 `mzlib-core/src/main/kotlin/`、`mzlib-minecraft/src/main/kotlin/`、`mzlib-demo-kotlin/`。Kotlin 插件 2.2.20，JVM 目标 1.8。
- 架构：`mzlib-demo`（和 `mzlib-demo-kotlin`）→ `mzlib-minecraft` → `mzlib-core`。`mzlib-core` 完全独立，不依赖任何 Minecraft API。
- 运行时使用模块/注册器系统（`mz.mzlib.module`）和平台抽象（`mz.mzlib.minecraft.MinecraftPlatform`），通过平台标签（`fabric`、`bukkit`、`paper`、`folia`、`neoforge`）和版本感知的 Wrapper 系统处理版本和平台差异。
- 版本号转换：`1.x.y → x*100 + y`（`MinecraftPlatform.parseVersion()`）。非 1.x 前缀则按 `x*100 + y*10 + z`。
- 当前版本：`10.0.1-beta.18`（`build.gradle.kts`）。group：`org.mzverse`。
- 平台：Bukkit/Spigot/Paper/Folia（完整）、Fabric（完整，含客户端）、NeoForge（实验性）、Vanilla（实验性）。

## 2. 环境与构建

- **JDK**：开发使用 JDK 21（`D:/jdk/zulu21`）。`gradle.properties` 中 `org.gradle.java.home = D:/jdk/zulu21`。Java 源码目标 Java 8（`sourceCompatibility = VERSION_1_8`）。Kotlin 2.2.20 不兼容 JDK 25。
- **Gradle**：8.10（Wrapper 已包含）。
- **构建**：`./gradlew.bat build publishToMavenLocal`（构建全模块并本地发布，`build` 已自动依赖 `shadowJar` 和 `publishToMavenLocal`）。离线构建：`--offline`。
- **Shadow JAR**：输出到 `out/` 目录。`mzlib-minecraft` 的 Shadow JAR 含 Main-Class `mz.mzlib.minecraft.vanilla.MzLibMinecraftInitializer`。
- **测试**：JUnit 5 / Jupiter 5.9.2。`@RetryOnFailure` 扩展（mzlib-core 的 `test.RetryOnFailure`）支持 GC 相关测试的重试（maxRetries=10, delay=100ms）。`./gradlew.bat :mzlib-core:test` 运行全部单测。
- **文档**：`./gradlew.bat buildDocs`（Typst → HTML）、`./gradlew.bat serveDocs`（端口 8080 预览）。需 `typst.exe` 在 PATH。
- **测试文件**（共 32 个测试类，240+ 用例）：`test/java/mz/mzlib/util/`（OptionTest、TestEither、TestResult、TestPair、TestBox、TestUnit、TestLazyConstant、TestRef、TestStringParser、TestIndexAllocator、TestCollectionUtil、TestArrayUtil、TestMapBuilder、TestTaskQueue、TestFunctionInvertible、TestRuntimeUtil、TestInstance、TestClassUtil、TestClassCache、TestJsUtil、TestTypedMap）、`util/math/`（TestMonoid、TestGroup）、`util/proxy/`（TestProxy）、`util/wrapper/`（TestWrapper）、`util/compound/`（TestCompound）、`util/async/`（TestGeneratorFunction、TestAsyncFunction）、`module/`（TestModule）、`event/`（TestEvent）、`data/`（TestData）。

## 3. 必读文件

| 文件 | 内容 |
|------|------|
| `build.gradle.kts`（根） | 构建、文档流程、发布配置 |
| `gradle/utils.gradle.kts` | Gradle 工具函数 |
| `mzlib-core/.../MzLib.java` | 核心模块入口 |
| `mzlib-core/.../module/MzModule.java` | 模块生命周期 |
| `mzlib-core/.../util/ElementSwitcher.java` | 开关系统核心 |
| `mzlib-core/.../util/RuntimeUtil.java` | 基础工具（cast、sneakilyThrow、nul 等） |
| `mzlib-core/.../util/ClassUtil.java` | 反射、MethodHandles、字节码操作 |
| `mzlib-core/.../util/Instance.java` | 实例模式（服务定位器） |
| `mzlib-core/.../util/Option.java` | Maybe 类型 |
| `mzlib-core/.../util/Either.java` | 分散合 |
| `mzlib-core/.../util/Result.java` | 结果类型 |
| `mzlib-core/.../util/FunctionInvertible.java` | 可逆函数 |
| `mzlib-core/.../util/Pair.java` | Pair / Pair.Mut 二元组 |
| `mzlib-core/.../util/Box.java` | Box / Box.Mut 单体容器 |
| `mzlib-core/.../util/Ref.java` | Ref / RefStrong / RefWeak 引用抽象 |
| `mzlib-core/.../util/TaskQueue.java` | 任务队列 |
| `mzlib-core/.../util/LazyConstant.java` | 惰性常量 |
| `mzlib-core/.../util/StringParser.java` | 字符串解析器 |
| `mzlib-core/.../util/IndexAllocator.java` | Free list 索引分配器 |
| `mzlib-core/.../util/CollectionUtil.java` | 集合工具（反转、切分、替换、迭代器代理等） |
| `mzlib-core/.../util/proxy/CollectionProxy.java` | 集合代理（透明类型转换） |
| `mzlib-core/.../util/proxy/MapProxy.java` | Map 代理（双向类型转换） |
| `mzlib-core/.../util/ModifyMonitor.java` | 修改监视器 |
| `mzlib-core/.../util/math/Monoid.java` | 幺半群代数结构 |
| `mzlib-core/.../util/math/Group.java` | 群代数结构 |
| `mzlib-core/.../util/SimpleProxy.java` | 运行时代理 |
| `mzlib-core/.../util/ThrowableSupplier.java` | 受检 Supplier |
| `mzlib-core/.../util/wrapper/WrapperObject.java` | Wrapper 基接口 |
| `mzlib-core/.../util/wrapper/WrapperFactory.java` | Wrapper 工厂 |
| `mzlib-core/.../util/nothing/Nothing.java` | Nothing 注入基接口 |
| `mzlib-core/.../util/nothing/NothingRegistration.java` | Nothing 字节码转换引擎 |
| `mzlib-core/.../util/compound/Compound.java` | 复合模式（运行时类生成） |
| `mzlib-core/.../event/Event.java` | 事件基类 |
| `mzlib-core/.../event/ListenerHandler.java` | 事件分发引擎 |
| `mzlib-core/.../module/IRegistrar.java` | 注册器接口 |
| `mzlib-core/.../plugin/PluginManager.java` | 插件管理 |
| `mzlib-minecraft/.../MinecraftPlatform.java` | 平台抽象、@Enabled/@Disabled、parseVersion |
| `mzlib-minecraft/.../VersionRange.java` | 版本范围注解 |
| `mzlib-minecraft/.../VersionName.java` | 版本特定名称注解 |
| `mzlib-minecraft/.../version/DataVersionV1800.java` | Wrapper 最小示例 |
| `mzlib-minecraft/.../command/RconConsole.java` | 多版本 @VersionName 示例 |
| `mzlib-minecraft/.../authlib/GameProfile.java` | @SpecificImpl 完整示例 |
| `mzlib-minecraft/.../MzLibMinecraft.java` | Minecraft 模块主入口 |
| `mzlib-minecraft/.../event/player/EventPlayerJoin.java` | Nothing 注入 + Event 实战示例 |
| `mzlib-demo/.../SimpleDocsServer.java` | 文档预览服务器 |
| `mappings/` | 映射文件（Mojang/Yarn/Spigot/YarnIntermediary） |

## 4. 基础设施设计与用法

### 4.1 ElementSwitcher 开关系统

**设计**：注解驱动的运行时启用/禁用系统。自定义条件注解用 `@ElementSwitcherClass` 指定处理器。`ElementSwitcher.isEnabled(AnnotatedElement)` 遍历所有注解——任一返回 false 则整体禁用（AND 逻辑）。通过 `@Repeatable` 多次使用的同一注解，OR 逻辑由容器注解的处理器实现。

**核心文件**：`ElementSwitcher.java`、`ElementSwitcherClass.java`（mzlib-core）；`VersionRange.java`、`VersionRanges.java`（mzlib-minecraft）；`MinecraftPlatform.java` 内部注解。

**用法**：
```java
// 自定义条件注解
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@ElementSwitcherClass(MyCondition.Handler.class)
@interface MyCondition {
    String value();
    class Handler implements ElementSwitcher<MyCondition> {
        public boolean isEnabled(MyCondition a, AnnotatedElement e) {
            return someRuntimeCheck(a.value());
        }
    }
}

// 使用
@VersionRange(begin = 1300)              // AND: >= 1.13
@MinecraftPlatform.Enabled("bukkit")     // AND: Bukkit平台
public class MyFeature { }

@VersionRange(begin = 1300, end = 1600)  // OR: [1.13, 1.16) 或 [1.19, ∞)
@VersionRange(begin = 1900)
public class MultiVersionFeature { }
```

**@MinecraftPlatform.Enabled/Disabled**：内部注解，接受 `String[]` 标签。`Enabled` 要求平台包含所有指定标签。`Disabled` 要求平台包含所有指定标签时禁用。支持 `@Repeatable`。注意旧式 `@FabricEnabled`、`@BukkitEnabled` 已标记 `@Deprecated`。

### 4.2 Instance 实例模式（服务定位器）

**设计**：通过 MethodHandle 动态修改接口的 `static` 字段值，使得 `MinecraftPlatform.instance` 可以被替换为平台特定的实现，无需依赖注入框架。

**原理**：
1. 接口声明 `SomeInterface instance = RuntimeUtil.nul()`（`nul()` 不是编译期常量，JVM 不会内联字段，可被 MethodHandles 修改）。
2. `Instance.Registrar` 注册 Instance 时，遍历类型层次，用 `ClassUtil.findFieldSetter` 获取 `instance` 字段的 MethodHandle 并写入新值。
3. 反注册时恢复为前一个实例（栈式管理，add 到队尾，unregister 时恢复队尾的前一个，队空则置 null）。

**用法**：
```java
// 定义
public interface MinecraftPlatform extends Instance {
    MinecraftPlatform instance = RuntimeUtil.nul();
    // ...
}

// 注册平台实现（模块 onLoad 中）
this.register(new MinecraftPlatformBukkit());

// 之后任何代码都能直接使用
String version = MinecraftPlatform.instance.getVersionString();
Set<String> tags = MinecraftPlatform.instance.getTags();

// 多实例栈模式：最后注册的激活，反注册时回退到前一个
module1.register(implA);  // instance = implA
module2.register(implB);  // instance = implB（最新激活）
module2.unregister(implB); // instance = implA（回退）
module1.unregister(implA); // instance = null（队空）
```

### 4.3 RuntimeUtil 基础工具

**设计**：Java 类型系统技巧的集合，绕开编译期检查。

| 方法 | 用途 |
|------|------|
| `cast(Object)` | 无条件转型，避免 `@SuppressWarnings` |
| `nul()` | 返回泛型 null，关键作用：不是编译期常量，使字段可被 MethodHandles 修改 |
| `sneakilyThrow(Throwable)` | 绕过受检异常，将任何异常作为未受检抛出 |
| `valueThrow(Throwable)` | 让 throw 作为三元表达式的一支 |
| `sneakilyRun(ThrowableRunnable)` | 执行带受检异常的 lambda |
| `castBooleanToByte/castByteToBoolean` | bool↔byte (0/1) |
| `orNull(Optional)` | 解包 Optional |
| `declaredlyThrow(Class)` | 空方法只为了在 throws 子句中声明异常类型 |
| `nop()` | 无操作 |
| `array(T...)` | 内联数组创建 |
| `second(Object, T)` | 返回第二个参数 |

**用法**：
```java
// 接口字段占位（关键用法）
MinecraftPlatform instance = RuntimeUtil.nul();

// 抛受检异常无需声明
throw RuntimeUtil.sneakilyThrow(new IOException());

// 三元表达式抛异常
Object o = condition ? new Object() : RuntimeUtil.valueThrow(new Exception());
```

### 4.4 ClassUtil 反射与字节码工具

**设计**：通过 `moe.karla.unsafe` 库获取 Trusted MethodHandles（绕过 Java 访问控制），支持运行时字节码定义/重定义。

**关键方法**：
- `findMethod/findConstructor/findFieldGetter/findFieldSetter`：通过 `RootAccess.getTrustedLookupIn()` 获取可访问私有成员的 MethodHandle。
- `forEachSuperUnique(Class, Consumer)`：遍历类层次（超类+接口），去重。
- `forEachSuperTopology(Class, Consumer)`：拓扑序（超类先于子类）。
- `getByteCode(Class)`：通过 Instrumentation retransform 捕获类的字节码。
- `defineClass(ClassLoader, name, byte[])`：运行时定义/重定义类（先尝试 redefineClasses，回退 Unsafe.defineClass）。
- `classForName(name, ClassLoader)`：含原始类型名称处理。
- `toWrappedClass/getWrappedType`：Wrapper 类型 ↔ 原始类型转换。
- `makeReference(ClassLoader, Object)`：创建强引用防止 GC。

**用法**：
```java
// 获取可信 MethodHandle（可访问私有成员）
MethodHandle getter = ClassUtil.findFieldGetter(MyClass.class, false, "privateField", int.class);
int value = (int) getter.invoke(myInstance);

// 遍历所有超类和接口（去重）
ClassUtil.forEachSuperUnique(obj.getClass(), c -> {
    if (RegistrarRegistrar.instance.registrars.containsKey(c)) { /* ... */ }
});
```

### 4.5 Either<F,S> 分散合

**设计**：标准分散合类型。`First<T>(value)` / `Second<T>(value)`。`fromNullable(first, second)` 要求恰好一个非 null（否则抛异常）。`inverse()` 交换两侧类型。`fold(actionFirst, actionSecond)` 双射操作。

**用法**：
```java
Either<String, Integer> e1 = Either.first("hello");
Either<String, Integer> e2 = Either.second(42);

// 从可空值创建（恰好一个非 null）
Either<String, Integer> e3 = Either.fromNullable("hello", null);

// fold：无论哪一侧都映射为同一类型
int result = e3.fold(String::length, i -> i * 2);  // e1.fold → 5, e2.fold → 84

// 链式操作
String result2 = e1.mapFirst(String::toUpperCase).fold(s -> s, i -> i.toString());

// 反转
Either<Integer, String> inv = e1.inverse();  // second("hello")
```

### 4.6 Result<V,E> 结果类型

**设计**：类似 Rust 的 Result。`Success(value)` / `Failure(value, error)`。Failure 可附带部分 value（部分成功场景）。`getOrThrow(exceptionSupplier)` 成功返回 value，失败用 error 构造异常。`of(Option<V>, Option<E>)` 构造——双 None 抛异常。

**用法**：
```java
Result<String, Integer> r1 = Result.success("hello");
Result<String, Integer> r2 = Result.failure(404);
Result<String, Integer> r3 = Result.failure(Option.some("partial"), 500);

// 安全的错误处理
String value = r2.getOrThrow(code -> new RuntimeException("Error: " + code));

// 转换
Either<String, Integer> e = r1.toEither();  // Success → first
Result<byte[], Integer> mapped = r1.mapValue(String::getBytes);
```

### 4.7 Pair / Box / Unit 基础类型 + 类型系统可变性

**设计**：`Pair<T1,T2>` 不可变，`Pair.Mut<T1,T2>` 可变——通过**类型系统编码可变性**。实际对象总是 `Mut` 子类，对外暴露 `Pair` 即禁止修改。`Box<T>` 同样模式：`Box.Mut<T>`。`Unit` 单例代替 void（equals 用 `instanceof` 而非同实例比较）。

**Comparators**：`Pair.comparing()` / `comparingByFirst()` / `comparingBySecond()` 及接受自定义 Comparator 的重载。

**用法**：
```java
// Pair 类型系统可变性
Pair.Mut<String, Integer> internal = Pair.Mut.of("hello", 42);
internal.setFirst("world");                      // 内部可写
Pair<String, Integer> external = internal;        // 对外只读

// Box 同样模式
Box.Mut<String> mb = Box.of("hello");
mb.set("world");
Box<String> rb = mb;                              // readonly view

// Unit singleton
TypedMap<Unit, Object> map = new TypedMap<>();    // Unit 作为 key 表示无意义的 holder
```

### 4.8 LazyConstant / Ref 引用系统

**设计**：`LazyConstant<T>` DCL + synchronized 实现的惰性值（支持 null）。`Ref<T>` 引用抽象：`RefStrong`（强引用，identity == 等值）、`RefWeak`（WeakReference，预捕获 hashCode防止 GC 后 hashCode 变化）。`Ref.getOrSet(ref, supplier)` 惰性初始化。

**用法**：
```java
// 惰性初始化
LazyConstant<String> cache = LazyConstant.of(() -> expensiveCompute());
String val = cache.get();  // 仅初始化一次

// RefStrong 身份等值
String a = new String("hello");
String b = new String("hello");
assertNotEquals(RefStrong.of(a), RefStrong.of(b));  // 不是 equals，是 ==

// RefWeak 稳定 hashCode
RefWeak<String> rw = new RefWeak<>(value);
int hc = rw.hashCode();  // 保持稳定即使 referent 被 GC

// 惰性设值
Ref<Option<String>> ref = RefStrong.of(Option.none());
String val2 = Ref.getOrSet(ref, () -> "default");  // 返回 "default" 并设置
```

### 4.9 FunctionInvertible 可逆函数

**设计**：可逆函数对 `(forward, backward)`，支持 `inverse()` 求逆、`thenApply()` 组合。预置 `identity()`、`cast()`、`ref()`、`option()`、`optional()`、`wrapper(factory)` 等实用实例。**类型系统可变性**：`f.inverse()` 返回对调泛型参数的 `FunctionInvertible`。

**用法**：
```java
FunctionInvertible<Integer, String> f = FunctionInvertible.of(
    i -> "n:" + i,
    s -> Integer.parseInt(s.substring(2))
);
assertEquals("n:42", f.apply(42));
assertEquals(42, f.inverse().apply("n:42"));

// 组合：f1 的输出是 f2 的输入
FunctionInvertible<Integer, String> composed = f1.thenApply(f2);
assertEquals(42, composed.inverse().apply(composed.apply(42)));  // 往返

// 预设实例
FunctionInvertible<String, Option<String>> opt = FunctionInvertible.option();
FunctionInvertible<String, T> identity = FunctionInvertible.identity();
```

### 4.10 ModifyMonitor 修改监视器

**设计**：`ModifyMonitor` 接口（`onModify()` / `markDirty()`），用于 Proxy 系列代理集合。`Empty` 单例为空操作，`Simple` 接受两个 Runnable。所有 Proxy 的修改操作通过 ModifyMonitor 通知外部。

### 4.11 CollectionProxy / MapProxy 代理系统

**设计**：通过 `FunctionInvertible` 将内部集合类型转换为外部类型，无需复制数据。`CollectionProxy<T,U>` `delegate: Collection<U>` + `function: FunctionInvertible<U,T>`。`ListProxy` 支持索引操作。`MapProxy<K,V,K1,V1>` 双向转换 key 和 value。`EntryProxy` 代理单个 entry。修改操作自动绕过 ClassCastException（contains/remove 等）。

**用法**：
```java
// 透明代理：内部 Integer → 外部 String
List<Integer> delegate = new ArrayList<>(Arrays.asList(1, 2, 3));
ListProxy<String, Integer> proxy = new ListProxy<>(
    delegate,
    FunctionInvertible.of(i -> "v" + i, s -> Integer.parseInt(s.substring(1)))
);
proxy.get(0);          // "v1"
proxy.add("v4");       // delegate 增加 4
proxy.indexOf("v3");   // 2

// Map 代理（双向转换 key 和 value）
MapProxy<K, V, K1, V1> map = new MapProxy<>(
    delegate, keyTransform, valueTransform
);

// 简易视图（identity 转换 + ModifyMonitor）
MapProxy<String, Integer, String, Integer> view = MapProxy.of(delegate, monitor);
```

### 4.12 TaskQueue 任务队列

**设计**：顺序执行 `Runnable`，实现 `Executor`。单个任务异常不影响后续（`onCatch(Throwable)` 默认 printStackTrace，可覆写）。支持运行时追加任务。Execute 期间追加的任务会延迟到下一轮 `run()` 执行。

### 4.13 数学代数结构

**设计**：接口层次 `Semigroup<T> extends BiFunction<T,T,T>` → `Monoid<T> extends Semigroup<T> + identity()` → `Group<T> extends Monoid<T> + inverse(T)`。预定义常用实例。

**预定义实例**：
| 类型 | 实例 |
|------|------|
| Monoid | `BOOLEAN_AND`, `BOOLEAN_OR`, `INT_MULTIPLICATION`, `LONG_MULTIPLICATION`, `BYTE_MULTIPLICATION`, `SHORT_MULTIPLICATION` |
| Group | `BOOLEAN_XOR`, `INT_ADDITION`, `LONG_ADDITION`, `FLOAT_ADDITION`, `DOUBLE_ADDITION`, `FLOAT_MULTIPLICATION`, `DOUBLE_MULTIPLICATION` |

**用法**：
```java
Monoid<Integer> sum = Monoid.INT_MULTIPLICATION;
int result = sum.apply(sum.identity(), 5);  // 1 * 5 = 5

Group<Integer> add = Group.INT_ADDITION;
int zero = add.apply(5, add.inverse(5));    // 5 + (-5) = 0
```

### 4.14 其他工具类

**CollectionUtil**：`reverse(Stream<T>)`（sorted 实现，不依赖外部顺序）、`split(List<T>, separator)` 切分列表、`replace(String, src, tar)` 模板替换、`each(List<T>)` 返回可修改的 Ref 迭代器、`toObjectArray(Object)` 原始数组转包装数组、`newHashMap` / `newArrayList` 便捷构造。

**ArrayUtil**：`box(int[] → Integer[])` / `unbox(Integer[] → int[])` 8 种基本类型。`box(Object)` / `unbox(Object[])` 动态分发。

**MapBuilder**：`MapBuilder.hashMap().put(k, v).put(k2, v2).get()` 链式构建。

**StringParser**：`peek()` / `read()` / `readString(terminators)` 逐字符解析，`exception()` 返回含当前位置的 `ParseException`。

**IndexAllocator**：Free list 分配器。`alloc(value)` 分配索引，`free(index)` 回收，`free` 和后续 `alloc` 遵循 FIFO。`size()` = list 中有效项 - bin 中空闲项。

**SimpleProxy**：运行时代理。target 对象 + handler 接口 → ASM 生成代理类。`MethodHandle` 缓存（WeakHashMap）。handler 中的方法直接覆写 target 中同名方法，不冲突的 target 方法生成转发。

### 4.6 Wrapper 系统

**设计**：定义 Java interface extends `WrapperObject`，通过 ASM 运行时生成实现类，将对 interface 方法的调用转发到被包装对象。无需反射——生成类直接调用 MethodHandle（已 JIT 内联）。

**核心文件**：`WrapperObject.java`、`WrapperFactory.java`、`WrapperClassInfo.java`、`WrapClass.java`、`WrapMethod.java`、`WrapFieldAccessor.java`、`WrapConstructor.java`、`SpecificImpl.java`。

**规则**：
1. 必须是 `interface`，extends `WrapperObject`。
2. 应包含 `WrapperFactory<X> FACTORY = WrapperFactory.of(X.class);`。
3. 构造器用 `@WrapConstructor`，命名为 `static$of`，然后封装 `static of(...)` 方法调用 `FACTORY.getStatic().static$of(...)`。
4. 静态方法/字段加 `static$` 前缀，封装真实 static 方法通过 `FACTORY.getStatic()` 调用。
5. Setter 和 Getter 用相同的 `@WrapFieldAccessor` 注解。

**用法（最小示例）**：
```java
@WrapMinecraftClass(@VersionName(name = "net.minecraft.SaveVersion", begin = 1800))
public interface DataVersionV1800 extends WrapperObject {
    WrapperFactory<DataVersionV1800> FACTORY = WrapperFactory.of(DataVersionV1800.class);

    @WrapMinecraftMethod({ @VersionName(name = "getId", end = 2106), @VersionName(name = "id", begin = 2106) })
    int getNumber();
}

// 使用
DataVersionV1800 dv = DataVersionV1800.FACTORY.create(rawSaveVersion);
int number = dv.getNumber();
```

**用法（静态方法和构造器）**：
```java
@WrapClass(SomeClass.class)
public interface MyWrapper extends WrapperObject {
    WrapperFactory<MyWrapper> FACTORY = WrapperFactory.of(MyWrapper.class);

    @WrapConstructor
    MyWrapper static$of(String arg);        // 构造器 → static$of
    static MyWrapper of(String arg) {       // 真实静态工厂
        return FACTORY.getStatic().static$of(arg);
    }

    @WrapMethod("staticMethod")
    String static$staticMethod();           // 静态方法 → static$
    static String staticMethod() {
        return FACTORY.getStatic().static$staticMethod();
    }

    @WrapFieldAccessor("STATIC_FIELD")
    String static$getStaticField();         // 静态字段 Getter
    static String getStaticField() {
        return FACTORY.getStatic().static$getStaticField();
    }
}
```

**wrapper.basic 包**：`Wrapper_void`、`Wrapper_int` 等基础类型 Wrapper，用于 Nothing 注入返回值。

### 4.7 Minecraft 专用 Wrapper 注解

**设计**：`@WrapMinecraftClass`、`@WrapMinecraftMethod`、`@WrapMinecraftFieldAccessor`、`@WrapMinecraftInnerClass`。值均为 `@VersionName[]` 数组。同时实现 `ElementSwitcher`（开关）和 `WrappedClassFinder`/`WrappedMemberFinder`（查找）。注解中的 `name` 使用 Yarn 名称，运行时根据平台自动重映射。

**@VersionName**：`begin`、`end`（版本范围）、`name`（Yarn 名称）、`remap`（默认 true——Fabric 直接用 Yarn，其他平台映射到 Mojang）。

**用法**：
```java
// 类：多个版本段使用不同类名
@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.server.command.Console", end = 1400),
    @VersionName(name = "net.minecraft.server.dedicated.ServerCommandOutput", begin = 1400, end = 1600),
    @VersionName(name = "net.minecraft.server.rcon.RconCommandOutput", begin = 1600)
})
public interface RconConsole extends WrapperObject, CommandOutput { }

// 内部类
@WrapMinecraftInnerClass(outer = FontDescriptionV2109.class, name = @VersionName(name = "Font"))
interface Resource extends FontDescriptionV2109 { }
```

### 4.8 @SpecificImpl 特定实现

**设计**：为同一个声明方法提供多个版本/平台特定实现。运行时根据 `ElementSwitcher` 自动路由。

**规则**：
1. 声明方法不能有 `@WrapMethod`（否则冲突）。
2. 特定实现方法用 `@SpecificImpl("声明方法名")` 标记，可加 `@VersionRange` 等开关。
3. 参数类型必须与声明一致。

**用法**：
```java
// 声明
GameProfile static$of(@Nullable UUID id, @Nullable String name);

// 旧版本实现
@SpecificImpl("static$of")
@VersionRange(end = 2002)
default GameProfile static$of$implV_2002(@Nullable UUID id, @Nullable String name) {
    return this.static$of0(id, name);
}

// 新版本实现
@SpecificImpl("static$of")
@VersionRange(begin = 2002)
default GameProfile static$of$implV2002(@Nullable UUID id, @Nullable String name) {
    return this.static$of0(Option.fromNullable(id).unwrapOr(NIL_UUID_V2002),
                           Option.fromNullable(name).unwrapOr(""));
}

// 调用——自动路由
GameProfile gp = GameProfile.of(id, name);
```

**WrapperObject.equals 三层模式**：
```java
// Layer 1: 声明（名义上覆写 Object.equals）
@Override
boolean equals(@Nullable Object object);

// Layer 2: 类型检查与转换
@SpecificImpl("equals")
default boolean equals$impl(@Nullable Object object) {
    if (this == object) return true;
    if (!(object instanceof WrapperObject)) return false;
    return this.equals$impl((WrapperObject) object);
}

// Layer 3: 自动拆包委托给被包装对象
@WrapMethod("equals")
boolean equals$impl(WrapperObject object);
```
调用 `wrapper1.equals(wrapper2)` 实际执行 `wrapper1.getWrapped().equals(wrapper2.getWrapped())`。

### 4.9 模块系统

**设计**：`MzModule` 管理组件生命周期。注册对象按拓扑序（依赖顺序）注册，卸载时按栈序反向自动注销。模块与作用域无关——仅决定生命周期。

**MzModule**（非抽象类）：
- `load()`：检查 `isLoaded`，调 `onLoad()`，完成 `CompletableFuture`。
- `unload()`：反向顺序注销所有已注册对象，调 `onUnload()`。
- `register(Object)`：若为 `MzModule` 子类则自动 `load()`；否则查找匹配的 `IRegistrar`，按拓扑序注册。
- `registerIfEnabled(AnnotatedElement)`：先通过 `ElementSwitcher` 检查再注册。

**IRegistrar<T>**：`getType()` 返回 `Class<? super T>`，`register(MzModule, T)` / `unregister(MzModule, T)`，`getDependencies()` 声明依赖。

**Registrable**：更简单——实现 `onRegister(MzModule)` / `onUnregister(MzModule)` 即可，由内置 `RegistrableRegistrar` 处理。

**入口模式**：
```java
// Bukkit 入口
public class MzLibBukkit extends MzModule {
    public static MzLibBukkit instance = new MzLibBukkit();
    public void onLoad() {
        this.register(MzLib.instance);                         // 核心模块
        this.register(MinecraftPlatformBukkit.instance);        // 平台实例
        this.register(RegistrarCommandBukkit.instance);        // Bukkit 命令注册器
        this.register(MzLibMinecraft.instance);                // MC 功能模块
    }
}

// Fabric 入口
public class MzLibFabricInitializer extends MzModule implements ModInitializer {
    public void onLoad() {
        this.register(MzLib.instance);
        this.register(new MinecraftPlatformFabric());
        this.register(MzLibMinecraftInitializer.instance);
    }
}
```

**用法（创建模块）**：
```java
public class MyModule extends MzModule {
    public static MyModule instance = new MyModule();
    public void onLoad() {
        this.register(SomeRegistrar.instance);     // 注册器
        this.register(new MyComponent());          // 组件（自动查找匹配的注册器）
        this.register(MySubModule.instance);       // 子模块（自动 load）—— 这是一种子模块模式
    }
}
```

### 4.10 Nothing 零开销注入系统

**设计**：运行时通过 ASM 直接修改目标类的字节码，将注入方法调用嵌入目标方法。所有注入调用使用 `invokedynamic` + `ConstantCallSite`（JIT 可单态内联）。"零开销"指：除了执行用户期望的注入代码外，注入机械代码是固定数量的 JVM 指令（无循环、无递归），理论上可被 JIT 完全优化掉，类似 Mixin 的真正注入。当未注册任何 Nothing 时，目标类字节码完全未触碰。

**核心流程**：
1. 定义 `Nothing` 接口（extends `Nothing` + `WrapperObject`），用 `@NothingInject` 标注注入方法。
2. 注册 `Nothing` 类到模块→`RegistrarNothingClass`→创建 `NothingRegistration` 捕获原始字节码→`apply()` 用 ASM 重写目标类。
3. 注入方法的参数通过 `@LocalVar`（读取目标方法局部变量）、`@CustomVar`（新建跨注入点共享变量）、`@StackTop`（读取操作数栈顶）获取。
4. 反注册时重新 apply 移除注入（若无剩余 Nothing 则恢复原始字节码）。

**@NothingInject 字段**：
| 字段 | 说明 |
|------|------|
| `wrapperMethodName()` | 目标方法名 |
| `wrapperMethodParams()` | 目标方法参数类型 |
| `locateMethod()` | 定位注入点（static 方法名，接收 NothingInjectLocating） |
| `locateMethodEnd()` | BRTRUE/CATCH 的结束点 |
| `type()` | INSERT_BEFORE / BRTRUE / CATCH / RAW |
| `priority()` | 注入顺序（低先执行） |

**NothingInjectLocating 定位器**：`next(opcode)` 找下一条匹配指令，`allLater(opcode)` 找所有后续匹配指令，`nextAccess(owner, name, desc)` 找方法调用/字段访问，`tagLocalVar(tag)` 标记局部变量供 `@LocalVarTagged` 引用。

**用法**：
```java
@WrapSameClass(PlayerManager.class)
public interface NothingPlayerManager extends Nothing, PlayerManager {
    // 在每个 RETURN 前注入
    @NothingInject(wrapperMethodName = "addPlayerV_2002",
                   wrapperMethodParams = { ClientConnection.class, EntityPlayer.class },
                   locateMethod = "addPlayerEndLocate",
                   type = NothingInjectType.INSERT_BEFORE)
    default Wrapper_void addPlayerEnd(
        @CustomVar("eventJoin") WrapperObject.Generic<EventPlayerJoin> event) {
        event.getWrapped().finish();
        return Nothing.notReturn();  // null = 不修改返回值
    }

    // 定位方法：找所有 RETURN
    static void addPlayerEndLocate(NothingInjectLocating locating) {
        locating.allLater(AsmUtil.insnReturn(void.class).getOpcode());
    }

    // 在方法开头注入，可提前 return 中断执行
    @NothingInject(wrapperMethodName = "addPlayerV_2002",
                   wrapperMethodParams = { ClientConnection.class, EntityPlayer.class },
                   locateMethod = "",    // "" = 注入在位置 0
                   type = NothingInjectType.INSERT_BEFORE)
    default Wrapper_void addPlayerBegin(
        @CustomVar("eventJoin") WrapperObject.Generic<EventPlayerJoin> event,
        @LocalVar(1) ClientConnection conn,
        @LocalVar(2) EntityPlayer player) {
        event.setWrapped(new EventPlayerJoin(player, conn));
        event.getWrapped().call();
        if (event.getWrapped().isCancelled())
            return Wrapper_void.FACTORY.create(null);  // 非 null = 提前返回
        return Nothing.notReturn();
    }
}

// 注册（在模块 onLoad 中）
this.register(NothingPlayerManager.class);
```

**@CustomVar**：在目标方法开头插入 `aconst_null; astore N`，所有引用同一名称的注入点共享此变量，实现跨注入点状态传递。

### 4.11 Compound 复合模式

**设计**：运行时动态生成类，将多个 Wrapper 接口组合为一个实际对象。生成的类 `X$0CompoundImpl` 是真实 Java 对象，直接实现所有接口，可包含字段、委托、super 调用覆写。

**核心注解**：
- `@Compound`：标记 compound 接口（也是 `@WrappedClassFinderClass`，触发类生成）。
- `@PropAccessor("name")`：为 interface getter/setter 生成对应字段。
- `@CompoundOverride(parent, method)`：覆写父类包装方法，自动处理 wrap/unwrap。
- `@CompoundSuper(parent, method)`：生成 `super$method$parent()` 方法，绕过虚调用访问父类原始实现。
- `@DelegateField("name")`：声明委托字段（注入另一个 compound 对象），自动转发未实现的方法。

**用法**：
```java
@Compound
public interface Foo extends WrapperObject {
    WrapperFactory<Foo> FACTORY = WrapperFactory.of(Foo.class);

    @PropAccessor("value")
    int getValue();
    @PropAccessor("value")
    void setValue(int v);
}

@Compound
public interface Bar extends Foo {
    WrapperFactory<Bar> FACTORY = WrapperFactory.of(Bar.class);

    @WrapConstructor
    Bar static$of();

    static Bar of() { return FACTORY.getStatic().static$of(); }

    @DelegateField("delegate")
    OtherFeature getDelegate();
}

// 使用——生成的 Bar$0CompoundImpl 有 value 字段、构造器、委托转发
Bar bar = Bar.of();
bar.setValue(42);
bar.getDelegate().doSomething();
```

### 4.12 Event 事件系统

**设计**：两阶段——注册期（通过 ASM 重写 `Event.call()` 方法体，替换为 `invokedynamic` 调用）和执行期（JIT 内联后的零开销分发）。支持事件继承（父事件监听器传播到子事件）、取消（`Cancellable`）、延迟任务（`runLater`/`finish`）。

**核心类**：
- `Event`：抽象基类，声明 `abstract void call()`。注册时 `call()` 被 ASM 重写为：`aload_0; invokedynamic call(Event) -> ListenerHandler.getCallSite; return`。
- `ListenerHandler`：每个事件类一个。存储 `HashSet<EventListener>` + `ArrayList`（按 priority 降序）。`call(Event)` 遍历执行。fail-safe（异常捕获、打印、继续）。
- `EventListener<T>`：`eventClass` + `priority`（高先执行）+ `handler`（`Consumer<T>`）。
- `Cancellable`：接口，通过访问 Event 包私有的 `isCancelled` 字段实现。
- `RegistrarEventClass`：注册事件类时创建 ListenerHandler、从父事件继承监听器、重写 `call()`。
- `RegistrarEventListener`：注册监听器时同时添加到事件类及其所有子类的 handler。

**调用流程**：`event.call()` → `invokedynamic` bootstrap → `ListenerHandler.getCallSite` → 返回绑定 handler 的 `ConstantCallSite` → JIT 内联为直接虚调用 → `handler.call(event)` → 按 priority 降序遍历 listeners。

**用法（定义事件）**：
```java
public class MyEvent extends Event implements Cancellable {
    public EntityPlayer player;
    public MyEvent(EntityPlayer player) { this.player = player; }
    public void call() {}  // 空实现，运行时被 ASM 重写
}

// 在模块中注册事件类
this.register(MyEvent.class);
```

**用法（注册监听器）**：
```java
EventListener<MyEvent> listener = new EventListener<>(MyEvent.class, Priority.HIGH, event -> {
    if (event.player.getName().equals("admin")) {
        event.setCancelled(true);
    }
});
this.register(listener);
```

**用法（触发事件）**：
```java
MyEvent event = new MyEvent(player);
event.call();                          // 分发监听器
if (event.isCancelled()) return;       // 检查取消
// ... 继续处理
event.finish();                        // 执行 runLater 注册的延迟任务
```

### 4.13 Mappings 映射系统

**设计**：`Mappings<?>` 抽象类（extends `Invertible.Abstract`），链式管道 `MappingsPipe`，各种 Fetcher 负责下载/解析/缓存映射文件。`mapClass`/`mapField`/`mapMethod` 找不到时返回原名。

**Fetcher 列表**：
| Fetcher | 来源 |
|---------|------|
| `MinecraftMappingsFetcherMojang` | Mojang 官方（piston-meta API→下载 server_mappings.txt） |
| `MinecraftMappingsFetcherYarn` | FabricMC/yarn zip→解析 .mapping 文件 |
| `MinecraftMappingsFetcherYarnIntermediary` | FabricMC/intermediary tiny |
| `MinecraftMappingsFetcherLegacyYarn` | mzlib JAR 内置 mappings/yarn/ |
| `MinecraftMappingsFetcherLegacyYarnIntermediary` | Legacy-Fabric/Legacy-Intermediaries |
| `MinecraftMappingsFetcherSpigot` | hub.spigotmc.org→CSRG 文件 |

**缓存**：所有 Fetcher 使用 `MappingsUtil.cache(file, supplier)` 模式——缓存文件存在则直接读取，否则下载并写入缓存。

**MappingsPipe**：`List<Mappings>` 链式处理，每步输出是下一步输入。`invert()` 返回逆序且各自反转的 Pipe。

**用法**：
```java
// 构建映射管道（典型）
Mappings<?> mappings = new MappingsPipe(
    new MinecraftMappingsFetcherMojang().fetch("1.21.11", folder),
    new MinecraftMappingsFetcherYarnIntermediary().fetch("1.21.11", folder),
    new MinecraftMappingsFetcherYarn().fetch("1.21.11", folder)
);

// 运行时使用
String mojangClassName = mappings.inverse().mapClass(yarnName);
String mojangFieldName = mappings.inverse().mapField(mojangClass, yarnFieldName);
```

### 4.14 Data 系统

**设计**：类型安全的属性注册模式。`DataKey<H, T, R>` 是强类型属性标识符，`DataHandler<H, T, R>` 实现读取/写入逻辑，通过模块系统注册。

**用法**：
```java
// 定义 Key
public static final DataKey<Entity, Integer, Integer> CUSTOM_VALUE =
    new DataKey<>("mylib:custom_value");

// 创建 Handler 并注册
DataHandler.builder(CUSTOM_VALUE)
    .checker(holder -> holder instanceof MyEntity)
    .getter(holder -> ((MyEntity) holder).getCustomValue())
    .setter((holder, val) -> ((MyEntity) holder).setCustomValue(val))
    .register(module);

// 使用
int val = CUSTOM_VALUE.get(entity);
CUSTOM_VALUE.set(entity, 42);
```

### 4.15 I18n 国际化系统

**设计**：分层、优先级排序的翻译系统，支持 JavaScript 模板文字参数解析。翻译文件支持 `.lang`（Properties 格式）和 `.json`。多个 I18n 实例按优先级降序查找。

**用法**：
```java
// 从 JAR 加载语言文件
I18n i18n = I18n.load(jarFile, "lang", 1.0f);
this.register(i18n);

// 运行时获取翻译
String msg = I18n.getSource("zh_cn", "my.key", "默认值");

// 带参数解析（模板文字 ${...}）
// 翻译值: "你好，${name}！" → resolve("zh_cn", "greeting", Map.of("name", "世界"))
```

### 4.16 Plugin 插件系统

**设计**：从 `./plugins/` 目录发现 JAR，每个 JAR 有独立 ClassLoader（`UnionClassLoader`），通过 MANIFEST.MF `Main-Class` 入口调用 `main()` 注册 Plugin。拓扑排序依赖后按序加载。

**用法（创建插件 JAR）**：
```java
// 在 JAR 的 Main-Class 的 main() 中
PluginManager.instance.registerPlugin(
    new Plugin("myPlugin", () -> MyPluginModule.instance)
        .depends("mzlib")
);
```

**用法（加载所有插件）**：
```java
MzLib.instance.load();
PluginManager.instance.loadPlugins(args);
```

### 4.17 Tester 测试框架

**设计**：基于模块系统的异步测试框架。`Tester<C extends TesterContext>` 注册到模块，`Tester.Registrar` 收集，`Tester.testAll(context)` 按 level 过滤后异步执行。

**用法**：
```java
// 定义测试器并注册
SimpleTester<TesterContext> myTester = new SimpleTester.Builder<>(TesterContext.class)
    .setName("myTest")
    .setMinLevel(1)
    .setFunction(ctx -> {
        // 测试逻辑，返回 CompletableFuture<List<Throwable>>
        return CompletableFuture.completedFuture(List.of());
    })
    .build();
this.register(myTester);

// 运行所有测试
TesterContext ctx = new TesterContext(/* level= */ 1);
Map<Tester<?>, List<Throwable>> failures = Tester.testAll(ctx).get();
```

## 5. 关键模式和约定

- **Java 内部类 import**：不 import 内部类，写完整 `OuterClass.InnerClass`。`@Enabled` 必须写 `@MinecraftPlatform.Enabled` 或 import `MinecraftPlatform` 后写 `@MinecraftPlatform.Enabled`。
- **编码**：UTF-8，缩进 4 空格，行宽 120。
- **Nullability**：`@Nullable`、`@UnknownNullability`（来自 JetBrains annotations）。
- **提交格式**：Conventional Commits：`feat(core): ...`、`fix(minecraft): ...`。
- **包名**：全小写。类名：大驼峰。方法：小驼峰。常量：全大写。
- **Wrapper 命名**：`Nms*`（NMS）、`Obc*`（CraftBukkit）、`*Paper`（Paper 独有）。
- **模块命名**：`Module` + 功能名（如 `ModuleRecipe`）。注册器命名：`Registrar` + 功能名。
- **注册风格**：有固定注册/注销逻辑时用 `Registrable`；需要动态逻辑时用 `IRegistrar`。
- **类型编码可变性**：`Pair` / `Box` 不可变，`Pair.Mut` / `Box.Mut` 可变。内部用 Mut，对外暴露父类型即禁止修改。解耦 API 签名与内部实现。
- **Wrapper 命名**：`static$` 前缀表示静态方法转发（构建生成实现时需区分静态/实例）。`static$of` 是构造器包装。
- **Interface final 字段**：`SomeInterface instance = RuntimeUtil.nul()`——`nul()` 不是编译期常量，JVM 不内联，MethodHandle 可后续修改。
- **异常处理**：`ThrowableSupplier` / `ThrowableFunction` / `ThrowableRunnable` 支持受检异常的 lambda。`RuntimeUtil.sneakilyThrow()` 绕过声明要求。
- **单测规范**：用 JUnit 5 `@Test` + `assert*`。模块测试需 `@BeforeEach` load MzLib / `@AfterEach` unload。测试文件命名 `Test*.java`，与被测类同包。使用 `--offline` 避免网络依赖。

## 6. mzlib-core 包结构

| 包 | 内容 |
|----|------|
| `mz.mzlib.asm` | ASM 字节码库（嵌入） |
| `mz.mzlib.data` | DataKey、DataHandler |
| `mz.mzlib.event` | Event、EventListener、Cancellable、ListenerHandler、RegistrarEventClass、RegistrarEventListener |
| `mz.mzlib.i18n` | I18n、RegistrarI18n |
| `mz.mzlib.module` | MzModule、IRegistrar、Registrable、RegistrarRegistrar、RegistrableRegistrar |
| `mz.mzlib.plugin` | Plugin、PluginManager |
| `mz.mzlib.tester` | Tester、TesterContext、SimpleTester |
| `mz.mzlib.util` | ElementSwitcher、ElementSwitcherClass、Instance、RuntimeUtil、Option、ClassUtil、Config、IOUtil、JsUtil、CollectionUtil 等 |
| `mz.mzlib.util.asm` | AsmUtil |
| `mz.mzlib.util.async` | AsyncFunction、GeneratorFunction（Kotlin 协程支持） |
| `mz.mzlib.util.compound` | Compound、CompoundOverride、CompoundSuper、DelegateField、PropAccessor、ICompoundImpl |
| `mz.mzlib.util.math` | Semigroup、Monoid、Group、Complex、Quaternion |
| `mz.mzlib.util.nothing` | Nothing、NothingInject、NothingRegistration、NothingInjectLocating、@LocalVar、@CustomVar、@StackTop、@LocalVarTagged |
| `mz.mzlib.util.proxy` | CollectionProxy、ListProxy、MapProxy、SetProxy、EntryProxy、IteratorProxy、ListIteratorProxy |
| `mz.mzlib.util.wrapper` | WrapperObject、WrapperFactory、@WrapClass、@WrapMethod、@WrapFieldAccessor、@WrapConstructor、@SpecificImpl 等 |
| `mz.mzlib.util.wrapper.basic` | Wrapper_void、Wrapper_int 等基础类型 Wrapper |

## 7. mzlib-minecraft 包结构

| 包 | 内容 |
|----|------|
| `mz.mzlib.minecraft` | MinecraftPlatform、VersionRange、VersionName、VersionRanges、MzLibMinecraft |
| `mz.mzlib.minecraft.authlib` | GameProfile、Property、PropertyMap |
| `mz.mzlib.minecraft.block` | BlockState、BlockEntity |
| `mz.mzlib.minecraft.bukkit` | MinecraftPlatformBukkit、MzLibBukkit、MzLibBukkitPlugin、命令/实体/物品/包装器 |
| `mz.mzlib.minecraft.command` | Command、CommandManager、ArgumentParser、RconConsole |
| `mz.mzlib.minecraft.component` | 物品组件系统（1.20.5+） |
| `mz.mzlib.minecraft.datafixer` | 数据修复器 |
| `mz.mzlib.minecraft.entity` | EntityPlayer、DamageSource、DisplayEntity |
| `mz.mzlib.minecraft.event` | Minecraft 事件 |
| `mz.mzlib.minecraft.fabric` | MinecraftPlatformFabric、MzLibFabricInitializer |
| `mz.mzlib.minecraft.inventory` | 库存系统 |
| `mz.mzlib.minecraft.item` | Item、ItemStack |
| `mz.mzlib.minecraft.mappings` | Mappings、MappingsPipe、MappingsByMap、Fetcher 类 |
| `mz.mzlib.minecraft.nbt` | NBT 数据操作 |
| `mz.mzlib.minecraft.neoforge` | MinecraftPlatformNeoForge、MzLibNeoForge |
| `mz.mzlib.minecraft.network.packet` | PacketListener、PacketBundle、codec |
| `mz.mzlib.minecraft.permission` | 权限系统 |
| `mz.mzlib.minecraft.recipe` | Recipe、RecipeManager |
| `mz.mzlib.minecraft.serialization` | Codec、DynamicOps |
| `mz.mzlib.minecraft.text` | Text、TextColor、TextStyle |
| `mz.mzlib.minecraft.ui` | UiWindow、UiWrittenBook、UiWindowAnvil |
| `mz.mzlib.minecraft.vanilla` | MinecraftPlatformVanilla、MzLibMinecraftInitializer |
| `mz.mzlib.minecraft.version` | DataVersionV* 等版本特定 Wrapper |
| `mz.mzlib.minecraft.wrapper` | @WrapMinecraftClass、@WrapMinecraftMethod、@WrapMinecraftFieldAccessor、@WrapMinecraftInnerClass |
| `mz.mzlib.minecraft.window` | Window、ModuleWindow |

## 8. Typst 文档约定

- 链接省略 `.typ` 后缀：`#link("name")`（编译后是 HTML）。
- 含下划线的标识符用反引号包裹：`` `GITHUB_USERNAME` ``。
- 标题不能跳级（`=` → `===` 非法）。

## 9. 快速任务

- **添加新版本 Wrapper**：参考 `DataVersionV1800.java`，创建 interface extends WrapperObject，加 `@WrapMinecraftClass`、FACTORY 和 `@WrapMinecraftMethod`。
- **添加平台门控**：用 `@MinecraftPlatform.Enabled("fabric")` / `@MinecraftPlatform.Disabled("bukkit")`。
- **添加模块**：创建 extends MzModule 的类，在 `onLoad()` 中 `this.register(...)` 子模块和组件。
- **添加事件**：创建 extends Event 的类，在模块中 `this.register(MyEvent.class)`。
- **添加字节码注入**：创建 extends Nothing + WrapperObject 的接口，用 `@NothingInject` 标注注入点，`this.register(NothingClass.class)`。
- **添加翻译**：用 `I18n.load(jar, "lang", priority)` 加载，`this.register(i18nInstance)` 注册。
- **添加测试**：用 `SimpleTester.Builder` 创建 Tester，`this.register(testerInstance)`。
