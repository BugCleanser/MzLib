#import "/lib/lib.typ": *;
#set raw(lang: "java");
#let title = [数组类型适配器];
#show: template.with(title: title);


目标类型是一个数组，我们会以一个`List`呈现给你，只需加上注解`@AdapterArray`

同样地，元素类型也会被适配

换句话说，`@AdapterArray List<A>`是在适配`T[]`，其中`A`是`T`的适配器

例如`@AdapterArray List<@AdapterPrimitive Float>`是在适配`float[]`

#strike[
    这个示例不能说完全没用吧，也许你不想直接访问原始类型数组呢
]
