# 网络数据包

## 发包

发送一个数据包非常简单，只需调用EntityPlayer#sendPacket

## 收包

使得服务器认为收到了一个玩家的数据包并进行处理，调用EntityPlayer#receivePacket

## 收发包监听

对于一个Packet的子类，我们可以监听它实例的发送或接收

只需创建一个PacketListener实例并注册到你的模块中

监听数据包不一定在主线程进行

例如我们需要监听PacketC2sChatMessage

```java
// in your module
@Override
public void onLoad()
{
    this.register(new PacketListener<>(PacketC2sChatMessage::create, (event, packet)->
    {
        // event表示当且数据包事件的信息，它并不是Event的子类
        event.setCancelled(true); // 取消事件
    }));
}
```

### 同步监听

异步监听能进行的操作有限

有时处理收发包时你可能需要进行同步操作

这时调用sync方法得到一个CompletableFuture，这个CompletableFuture在主线程中被完成

如果该监听器本身是在主线程中触发，则该CompletableFuture会立即完成

否则该数据包会被推迟到主线程下一个tick再继续处理

如果MC本身就要在主线程处理这个数据包（如PacketC2sCloseWindow），则调用并sync()不会导致更多延迟

```java
this.register(new PacketListener<>(PacketC2sCloseWindow::create, (event, packet)->
{
    // 如果需要同步处理
    event.sync().whenComplete((r,e)->
    {
        // 这里在主线程中处理数据包事件
        // 如果需要则取消事件
        event.setCancelled(true); // 取消事件
    });
}));
```
