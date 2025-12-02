#import "../../../lib/lib.typ": *;

#set raw(lang: "java");

#set document(title: `Editor`);

#show: template;

#title();

`Editor`是get/set的封装，基于#link("auto_completable")[`AutoCompletable`]

一次完整的操作为：get、修改、set，可中途打断避免set

= 使用`Editor`

```java
Editor<T> editor = ...; // 获取Editor实例
for(T data: editor)
{
    // 在此处修改data
}
```

在`for`开始时，get被应用；`for`完成时，set被应用

可以用`continue`使`for`立即完成；也可用`break`或`return`等使`for`退出

```java
Editor<T> editor = ...; // 获取Editor实例
for(T data: editor)
{
    // 在此处修改data
    if(...)
        break; // 之前的修改不会生效，如果它依赖于set
}
```

详细的例子在minecraft的#link(meta.root+"dev/mc/item/item#自定义数据-修改自定义数据")[`Item`]中

= 创建实例

已知getter和setter时，使用`Editor#of`

```java
Ref<T> ref = new RefStrong<>(new T());
Editor<T> editor = Editor.of(
        ThrowableFunction.of(ref::get).thenApply(T::clone), // get后克隆
        ref::set
    );
```

也可通过`Editor#then`嵌套，详细的例子在minecraft的#link(meta.root+"dev/mc/item/item#自定义数据-修改自定义数据-传递revise")[`Item`]中
