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

## 创建主模块

```java
public class Demo extends MzModule
{
    public static String MOD_ID = "mzlibdemo";
    
    public static Demo instance = new Demo();
    
    @Override
    public void onLoad()
    {
        // 加载子模块和其它对象（如果有的话）
        this.register(DemoSubmodule.instance);
    }
}
```

## 从Bukkit加载主模块

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