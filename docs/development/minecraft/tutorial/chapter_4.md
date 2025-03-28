# 监听事件

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

