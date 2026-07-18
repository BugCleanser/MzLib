#import "/lib/lib.typ": *;
#set raw(lang: "java");
#let title = [容器适配器];
#show: template.with(title: title);

- `Map`也在此讨论范围内

容器适配器的作用是适配其中的*元素*

它看起来是元素适配器的容器，实现为*委托*

因此，当你*修改*适配或还原得到的容器时，对*原容器*生效

在容器接口上添加相关*注解*来得到适配器类型，*泛型*参数就是*元素*的适配器类型

例如`@AdapterList List<WrapperObject>`在适配`List<Object>`，因为`WrapperObject`是`Object`的适配器

理论上你可以任意嵌套：e.g. `@AdapterList List<@AdapterList List<WrapperObject>>`

以下是内置支持的容器及其注解：

#table(columns: 2)[容器][注解][
  `Collection`][`@AdapterCollection`][
  `List`][`@AdapterList`][
  `Set`][`@AdapterSet`][
  `Map`][`@AdapterMap`]

类必须严格与注解匹配，而不能是其子类或父类

例如`@AdapterSet HashSet<...>`是*错误*的，我们当然不会为每一种可能的`Set`都实现，因此你只能写`Set`本身：`@AdapterSet Set<...>`
