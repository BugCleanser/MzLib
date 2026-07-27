#import "/lib/lib.typ": *;
#set raw(lang: "java");
#let title = [捕获异常];
#show: template.with(title: title);

`NothingInjectType.CATCH`需要2个定位器，并且每个定位器定位到*唯一*位置

`locator[0]`的位置必须在`locator[1]`的位置*之前*

则*捕获*从`locator[0]`（包含）到`locator[1]`（不包含）的*指令*抛出的异常

可以理解为在`locator[0]`*前面*插入`try`，然后在`locator[1]`*前面*插入`catch`

`catch`时你的方法会被执行

= 栈

注意：`catch`时*原本的栈*必须是*空*的，注入后栈上是唯一的`Throwable`

于是你可以使用#link("stack")[`@StackTop`]来访问*栈顶*的异常（也可以不访问）

你可以用`instanceof`判断是否是你需要*处理*的异常，不是的话就*重新丢出*

```java
@NothingInject(name = "func", type = NothingInjectType.CATCH, locator = { "locate0", "locate1" }) // 你需要自己写定位器
static void func$catch(@StackTop Box.Mut<Throwable> stackTop /* 栈顶 */)
    throws Throwable
{
    Throwable exception = stackTop.get(); // 获取栈顶的异常
    if(exception instanceof Error) // 只处理Error
    {
        // 处理
    }
    else
        throw exception; // 其它重新丢出
}
```

= 优先级

若丢出异常的指令*同时*属于多个捕获块，它到底被谁捕获？

JVM规定，begin（对应`locator[0]`）的位置越靠后，则说明它在越内层，则更优先

相当于：

```java
try { // 外层
    // do sth.
    try { // 内层：这里更靠后
        throw new Throwable(); // 抛出
    } catch(Throwable e) {
        // 当然是内层先捕获到
        // 如果在这里重新抛出，外层才能捕获到
    }
} catch(Throwable e) {
}
```

由于我们在指令的前面插入，因此相同begin时*越晚*注入就在越内层，就*越先*捕获到异常

如果begin的位置*相同*，例如它们是同一个`try`的多个`catch`*子句*，或是多个`try`直接嵌套

此时越前面声明的块越先捕获。我们总是声明到最前面，因此越晚注入的也是越先捕获到

*总结*：不同`locator[0]`*越后面*的*越先*捕获；同一个`locator[0]`*越晚*注入的*越先*捕获
