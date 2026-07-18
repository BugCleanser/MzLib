#import "/lib/lib.typ": *;
#set raw(lang: "java");
#let title = [局部变量];
#show: template.with(title: title);

此处是访问方法*原有*局部变量的参考手册，包括*参数*和*标记*的变量

另见#link("custom-variable")[自定义变量]

TODO

= 标记变量

`NothingInjectLocating`不但定位指令，也能定位和*标记*局部变量

在定位到*访问*局部变量的指令时，调用`tagLocalVar(tag)`将这个变量标记，其中`tag`是它在方法中的唯一标识（由你自己命名）

随后在注入方法的参数中使用`@LocalVarTagged(tag)`，类型要求同`@LocalVar`
