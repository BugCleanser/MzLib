# 物品栏、窗口和Compound

在Bukkit中，一个物品栏可以被玩家直接打开，但在原版中并非如此

## 物品栏

物品栏是一个接口，表示大小固定、包含若干有序物品堆的对象，可以理解为ItemStack[]的接口

通过InventorySimple可以创建一个简单的物品栏

```java
Inventory inventory=InventorySimple.newInstance(10 /* 容量*/);
```

## 窗口

玩家实际上打开的是一个窗口，你也可以管它叫物品栏界面，Mojang叫它菜单，Fabric(yarn)叫它屏幕

窗口是玩家可以访问的界面，玩家通过窗口操作物品栏

窗口中有若干个槽位(WindowSlot)，槽位一般对应物品栏中的一个索引，你也可以自定义它的行为

### 创建窗口

例如我们可以创建一个箱子界面，也就是WindowChest的实例

```java
Inventory inventory=InventorySimple.newInstance(9*5);
Window window=WindowChest.newInstance(UnionWindowType.GENERIC_9x5.typeV1400, syncId, player.getInventory(), inventory, 5);
```

WindowTypeV1400表示从1.14开始的窗口类型，可以通过UnionWindowType#typeV1400得到

其中UnionWindowType.GENERIC_9x5表示一个9*5的箱子界面

现在你发现你没有syncId来创建窗口，但你先别急

### 打开窗口

即使你创建了一个窗口，你也不能让玩家直接打开它，AbstractEntityPlayer#openWindow的参数是WindowFactory，你需要提供WindowFactory实例来创建这个窗口

窗口的标题由WindowFactory提供

虽然这个设计可能很愚蠢，但Mojang代码就是这样写的（

因此我们提供了一个WindowFactorySimple让你可以很容易创建WindowFactory的实例

注意：在1.14之前，窗口的类型id由WindowFactory提供；从1.14开始，窗口的类型由Window提供

```java
Inventory inventory=InventorySimple.newInstance(9*5);
WindowFactory windowFactory=WindowFactorySimple.newInstance(UnionWindowType.GENERIC_9x5.typeV1400, Text.literal("标题"), (syncId, inventoryPlayer)->
{
    // 在这里创建窗口
    return WindowChest.newInstance(UnionWindowType.GENERIC_9x5.typeV1400, syncId, inventoryPlayer, inventory, 5);
});
// 让玩家打开WindowFactory
player.openWindow(windowFactory);
```

我们将箱子界面进行了简单封装，因此上面的代码可以简化为

```java
Inventory inventory=InventorySimple.newInstance(9*5);
WindowFactory windowFactory=WindowFactorySimple.chest(Text.literal("标题"), inventory, 5, window->
{
    // 可以对窗口进行设置
});
player.openWindow(windowFactory);
```

### 设置槽位

我们可以设置某个槽位的行为，它是WindowSlot的实例

我们先以WindowSlotButton为例，它的canPlace和canTake始终返回false

```java
Inventory inventory=InventorySimple.newInstance(9*5);
// 设置0号物品
inventory.setItemStack(0, new ItemStackBuilder("minecraft:stick").build());
WindowFactory windowFactory=WindowFactorySimple.chest(Text.literal("标题"), inventory, 5, window->
{
    // 设置0号槽位
    window.setSlot(0, WindowSlotButton.newInstance(inventory, 0));
});
player.openWindow(windowFactory);
```

这样玩家就不能在0号槽位放入或拿出物品

### 自定义WindowSlot

你已经迫不及待想要实现自己的WindowSlot了，它是一个封装类，我们需要用Compound类继承

```java
// 必须加上这个注解
@Compound
public interface WindowSlotButton extends WindowSlot
{
    // 作为一个WrapperObject的子类，应有creator
    @WrapperCreator
    static WindowSlotButton create(Object wrapped)
    {
        return WrapperObject.create(WindowSlotButton.class, wrapped);
    }
    
    /**
     * 构造器会直接原封不同继承
     * 对其包装即可
     */
    @WrapConstructor
    WindowSlot staticNewInstance(Inventory inventory, int index, int x, int y);
    static WindowSlot newInstance(Inventory inventory, int index)
    {
        return create(null).staticNewInstance(inventory, index, 0, 0);
    }
    
    /**
     * 继承一个方法需要加@CompoundOverride注解
     * parent表示这个方法所在的包装类
     * method表示对应的包装方法
     * 参数和返回值类型必须与被继承方法完全一致
     */
    @Override
    @CompoundOverride(parent=WindowSlot.class, method="canPlace")
    default boolean canPlace(ItemStack itemStack)
    {
        return false;
    }
    
    @Override
    @CompoundOverride(parent=WindowSlot.class, method="canTake")
    default boolean canTake(AbstractEntityPlayer player)
    {
        return false;
    }
}
```
