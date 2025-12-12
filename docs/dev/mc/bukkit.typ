#import "/lib/lib.typ": *;
#set raw(lang: "java");
#let title = [配合BukkitAPI使用];
#show: template.with(title: title);


目前MzLib的功能并不完善，有时你可能需要监听Bukkit的事件

或者，使用其它插件的API时，你也需要用到Bukkit的对象

/ #[= 转换物品堆]:

    通过`BukkitItemUtil`将Bukkit和MzLib的`ItemStack`相互转换

    ```java
    org.bukkit.inventory.ItemStack bukkitItemStack = BukkitItemUtil.toBukkit(itemStack);
    ```
    ```java
    ItemStack itemStack = BukkitItemUtil.fromBukkit(bukkitItemStack);
    ```

/ #[= 转换实体]:

    通过`BukkitEntityUtil`将Bukkit和MzLib的实体相互转换

    ```java
    org.bukkit.entity.Player bukkitPlayer = (org.bukkit.entity.Player) BukkitEntityUtil.toBukkit(player);
    ```
    ```java
    // 注意：包装类不要使用强转
    EntityPlayer player = BukkitEntityUtil.fromBukkit(bukkitPlayer).as(EntityPlayer.FACTORY);
    ```