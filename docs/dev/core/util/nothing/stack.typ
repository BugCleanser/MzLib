#import "/lib/lib.typ": *;
#set raw(lang: "java");
#let title = [栈];
#show: template.with(title: title);

`@StackTop`用于访问*栈*上的值，当然我们*不会*真的把它*消费*掉

声明多个`@StackTop`时，它们*按顺序*从栈顶取出

在你的方法结束时，它们会被*放回*栈顶（保持原来的顺序）

常用于在`return`时访问*返回值*，或是`catch`时访问*捕获*到的异常

像`@LocalVar`一样，你需要以`Box.Mut`（或`Box`）包装类型，类型可以是*适配器*

```java
// 包装方法
@WrapMethod("func")
int func(); // 假设返回int

@NothingInject(name = "func", type = NothingInjectType.INSERT_BEFORE, locator = "followingReturn") // 定位所有return指令
static void func$return(@StackTop Box.Mut<@AdapterPrimitive Integer /* int适配器 */> returnValue)
{
    System.out.println(returnValue.get()); // 获取返回值
    returnValue.set(114514); // 修改返回值
}
```
