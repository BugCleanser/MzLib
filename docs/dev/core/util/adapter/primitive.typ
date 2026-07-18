#import "/lib/lib.typ": *;
#set raw(lang: "java");
#let title = [原始类型适配器];
#show: template.with(title: title);

目前来说，只有#link("../nothing/index")[Nothing]中需要使用，其它地方你完全可以直接使用*原始类型*

众所周知Java中每个原始类型都有其*包装类*，在包装类上添加`@AdapterPrimitive`注解，即得到对应原始类型的适配器

例如`@AdapterPrimitive Integer`是在适配`int`，`@AdapterPrimitive Void`是在适配`void`

好像没什么好说的（
