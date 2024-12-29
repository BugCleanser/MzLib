# 创建简单命令

这是MzLib命令系统的入门教程

一个命令应该是mz.mzlib.minecraft.command.Command的实例

它的基本设置方法都会返回自身，因此我们可以用链式构造它，但下面的教程中我们会分步创建

## 创建Command实例

直接调用构造器，第一个参数是命令的名称，后面的参数是它的别名（可选）

```java
Command command = new Command("mzlibdemo", "mzd");
```

## 设置命名空间

命令可以以两种名称调用以防重名，/name 和 /namespace:name

其中name是你的命令名，namespace是命令的命名空间，默认是minecraft，一般设置成你的插件名(MOD_ID)

```java
command.setNamespace("mzlibdemo");
```

## 设置命令处理器

你当然需要规定如何处理你的命令，使用setHandler

```java
command.setHandler(context->
{
    if(!context.successful || !context.doExecute)
        return;
    // do sth. on execute
    context.sender.sendMessage(Text.literal("Hello World!"));
});
```

其中context是一个CommandContext的实例

我们先判断context.successful，它代表命令是否被成功解析

然后我们判断context.successful，它表示命令是否应该被执行（否则只是需要补全命令）

最后是我们命令执行的代码，示例中我们发送一条Hello World给命令发送者

## 注册命令

一般地，我们在一个模块中注册这个命令，不需要手动注销

```java
public class Demo extends MzModule
{
    public static Demo instance = new Demo();
    
    public Command command;
    
    @Override
    public void onLoad()
    {
        this.register(this.command=new Command("demo", "d").setHandler(context->{/* ... */}));
    }
}
```

## 添加子命令

使用Command#addChild添加子命令，你可以在父命令创建时直接添加

如果子命令足够复杂，你也可以单独为它创建一个模块，并在onLoad中添加到父命令，并在onUnload中从父命令移除

```java
public class DemoSubcommand extends MzModule
{
    public static DemoSubcommand instance = new DemoSubcommand();
    
    public Command command;
    
    @Override
    public void onLoad()
    {
        this.register(Demo.instance.command.addChild(this.command=new Command("sub").setHandler(context->{/* ... */})));
    }
}
```

然后在父命令注册后注册这个模块

## 设置命令权限检查器

使用setPermissionChecker方法设置权限检查器

检查sender的权限，如果权限不足，返回一个Text类型的提示，否则返回null

可以使用预设的静态方法Command#checkPermission

记得注册你的权限到模块中

```java
public class Demo extends MzModule
{
    public static Demo instance = new Demo();
    
    public String MOD_ID="mzlibDemo".toLowerCase();
    
    public Permission permission=new Permission(this.MOD_ID+".command.demo");
    public Command command;
    
    @Override
    public void onLoad()
    {
        // 注册权限
        this.register(this.permission);
        // 注册命令
        this.register(this.command=new Command("demo").setNamespace(this.MOD_ID).setPermissionChecker(sender->Command.checkPermission(sender, this.permission)));
    }
}
```

可以使用静态方法Command#checkPermissionSenderPlayer要求发送者必须是一个玩家

如果要联合多个这样的检查器，可以使用Command#checkPermissionAnd合并它们的结果

```java
public class Demo extends MzModule
{
    public static Demo instance = new Demo();
    
    public String MOD_ID="mzlibDemo".toLowerCase();
    
    public Permission permission=new Permission(this.MOD_ID+".command.demo");
    public Command command;
    
    @Override
    public void onLoad()
    {
        // 注册权限
        this.register(this.permission);
        // 注册命令
        this.register(this.command=new Command("demo").setNamespace(this.MOD_ID)
                .setPermissionChecker(sender->Command.checkPermissionAnd(Command.checkPermissionSenderPlayer(sender), Command.checkPermission(sender, this.permission))));
    }
}
```

## 处理命令参数

参数处理使用ArgumentParser的实例，构造器中name表示参数名，仅用于提示帮助信息

例如，处理字符串参数可以使用ArgumentParserString，第二个参数表示字符串中是否可以包含空格，后面的参数表示预设值

```java
public class Demo extends MzModule
{
    public static Demo instance = new Demo();
    
    public Command command;
    
    @Override
    public void onLoad()
    {
        this.register(this.command=new Command("demo", "d").setHandler(context->
        {
            // process arg0
            String arg0=new ArgumentParserString("arg0", false, "enum1", "enum2").process(context);
            // 若arg0解析失败直接返回
            if(!context.successful)
                return;
            switch(arg0)
            {
                case "enum1":
                    // 第一种用法
                    if(!context.successful || !context.doExecute)
                        return;
                    context.sender.sendMessage(Text.literal("This is the first usage of this command"));
                    break;
                case "enum2":
                    // 第二种用法
                    // 再读一个参数
                    // 后面没有其它参数，可以允许包含空格
                    String arg1=new ArgumentParserString("arg1", true).process(context);
                    if(!context.successful || !context.doExecute)
                        return;
                    context.sender.sendMessage(Text.literal("Second: "+arg1));
                    break;
                default: // 无效的arg0，命令解析失败
                    context.successful=false;
                    break;
            }
        }));
    }
}
```

## 进阶

命令系统的进阶用法参见[进阶教程](../advanced/command.md)
