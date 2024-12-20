# 命令的监听和创建

## 注册监听器

对于一个已注册的事件，你可以直接在模块上创建监听器实例并注册

例如假设我们要监听所有EventEntity的实例

```java
// in your module
@Override
public void onLoad()
{
    this.register(new EventListener<>(EventEntity.class, e->
    {
        System.out.println("Event is called: "+e.getClass());
    }));
}
```

## 注册事件类

创建一个自己的事件类，直接或间接继承Event，实现call方法，然后将类注册到你的模块中

call方法中不需要写任何代码，但它必须有方法体

```java
public class MyEvent extends Event
{
    // 实现call方法
    @Override
    public void call()
    {
        // 这里不用写任何代码
    }
    
    // 处理该事件的模块
    public static class Module extends MzModule
    {
        // 模块实例，在其父模块中注册
        public static Module instance=new Module();
        
        @Override
        public void onLoad()
        {
            // 注册事件类
            this.register(MyEvent.class);
        }
    }
}
```

在合适的时候触发该事件即可

## 触发事件

若你创建了一个事件实例，调用call方法即可触发所有监听器

如果事件没有被取消，则执行事件

无论如何，最后都应该调用complete方法来结束事件

```java
MyEvent event=new MyEvent();
// 触发监听器
event.call();
if(!event.isCancelled())
{
    // 执行事件
}
// 完成事件
event.complete();
```