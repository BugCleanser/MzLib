#  创建插件和模块

让我们创建一个Bukkit插件

在项目资源中添加plugin.yml，并创建一个Bukkit的入口（JavaPlugin子类）

```yaml
name: MzLibDemo
version: 0.1
authors: [ mz ]
main: mz.mzlib.demo.DemoPlugin
depend: [ MzLib ]
api-version: 1.13
```

现在开始对接MzLib

## 模块化

你的插件应该有一个主模块和若干子模块，子模块还可以有它的子模块

一个模块是一个单例，它是MzModule的子类

```java
public class Demo extends MzModule
{
    public static Demo instance = new Demo();
    
    @Override
    public void onLoad()
    {
        // do sth. on load
    }
}
```

## 加载主模块
主模块需要被手动加载和卸载

一般地，在Bukkit插件onEnable中将其注册到MzLib

在onDisable中将其注销

```java
public class DemoPlugin extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        MzLib.instance.register(Demo.instance);
    }
    
    @Override
    public void onDisable()
    {
        MzLib.instance.unregister(Demo.instance);
    }
}
```

## 加载子模块

如果你有子模块的话，只需让其注册到父模块

在父模块注销时子模块也会自动注销


```java
public class Demo extends MzModule
{
    public static Demo instance = new Demo();
    
    @Override
    public void onLoad()
    {
        this.register(DemoSubmodule.instance);
    }
}
```