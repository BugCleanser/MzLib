#import "/lib/lib.typ": *;
#set raw(lang: "java");
#let title = [适配器];
#show: template.with(title: title);

适配器的作用是与目标类型（adaptee）*相互转换*，以便访问

处理适配器需要完整的类型信息（`AnnotatedType`），而不只是`Class`

由适配器类型可以获得*适配器处理器*，然后获得目标类型（adaptee）

一个类型不是适配器，我们也可以说它的适配器处理器是identity（不做任何转换），目标类型与其相同

#link("../wrapper/index")[包装器]是一类特殊的适配器

具体流程如下：

+ 类型上注解了`@Adapter`或相关注解，则得到对应的处理器

+ 类本身注解了`@Adapter`，得到对应处理器

+ 类具有父接口`WrapperObject`，则其为包装器，目标类型就是*被包装的类型*

+ 否则这个类型不是适配器

注解了`@NoAdapter`的类型也不是适配器

= 常用的适配器

- #link("collection")[容器适配器]
  - `@AdapterList`
  - `@AdapterSet`
  - `@AdapterCollection`
  - `@AdapterMap`
- #link("array")[数组适配器]
- #link("primitive")[原始类型适配器]
- #link("../wrapper/index")[包装器]

= 适配器处理器

自定义处理器目前是*实验性*的
