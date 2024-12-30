# 网络数据包

## 收发包监听

对于一个Packet的子类，我们可以监听它实例的发送或接收

只需创建一个PacketListener实例并注册到你的模块中

例如我们需要监听PacketC2sChatMessage

```java
// in your module
@Override
public void onLoad()
{
    this.register(new PacketListener<>(PacketC2sChatMessage.class, (event, packet)->
    {
        // event表示当且数据包事件的信息，它并不是Event的子类
        event.setCancelled(true); // 取消事件
    }));
}
```

### 同步监听

有时处理收发包时你可能需要进行同步操作

这时调用sync方法得到一个CompletableFuture，事件会被转发到主线程的下一个tick处理，处理时这个CompletableFuture被完成

```java
(event, packet)->
{
    // 如果需要同步处理
    event.sync().whenComplete((r,e)->
    {
        // 这里在主线程中处理数据包事件
        // 如果需要则取消事件
        event.setCancelled(true); // 取消事件
    });
};
```

## 发包

发送一个数据包非常简单，只需调用EntityPlayer#sendPacket

## 收包

使得服务器认为收到了一个玩家的数据包并进行处理，调用EntityPlayer#receivePacket
