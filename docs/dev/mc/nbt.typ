#import "./../../lib/lib.typ": *

#set raw(lang: "java");

#set document(title: [NBT])

#show: template

#title()

#set enum(spacing: 2em);

类似Json，NBT是一种可嵌套的数据结构，基类是`NbtElement`

/ #[= 基本类型]:

    - `NbtByte`

    - `NbtShort`

    - `NbtInt`

    - `NbtLong`

    - `NbtFloat`

    - `NbtDouble`

    - `NbtString`

    `boolean`一般转换为`byte`储存(`1`或`0`)

    调用对应类的`newInstance`方法来创建nbt

    ```java
    NbtByte nbt1 = NbtByte.newInstance((byte)114);
    NbtByte nbt2 = NbtByte.newInstance(true);
    NbtString nbt3 = NbtString.newInstance("HelloWorld");
    ```

    通过`getValue`得到其储存的值
    ```java
    byte v1 = nbt1.getValue();
    String v3 = nbt3.getValue();
    ```

    基本类型一般认为是只读的，创建新实例而不是使用`setValue`修改已有实例

/ #[= `NbtCompound`]:

    类似`Map<String, NbtElement>`的结构，储存键值对，键是唯一的`String`

    使用`newInstance`创建实例，使用`put`添加元素

    ```java
    NbtCompound nbt = NbtCompound.newInstance();
    nbt.put("v1", NbtInt.newInstance(114514));
    ```

    使用`put`的重载方法快捷添加元素

    ```java
    nbt.put("v1", (short) 114);
    nbt.put("v2", 514);
    nbt.put("v3", "awa");
    ```

    使用`get`获取元素：由于元素不一定存在，返回值是`Option<NbtElement>`

    ```java
    for(NbtElement ele: nbt.get("v1")) // 如果元素存在
    {
        if(ele.is(NbtString.FACTORY)) // 判断元素类型
            System.out.println(ele.as(NbtString.FACTORY).getValue()); // 转换类型
    }
    ```

    对于已知类型的元素，使用特化的get方法

    ```java
    Option<String> s = nbt.getString("v3");
    Option<NbtCompound> child = nbt.getNbtCompound("v4");
    ```

    当元素不存在或不为对应类型时，返回`Option.none()`

    / #[== 修订]:

        当你想修改元素但不想改变原实例时，可以使用浅克隆
        ```java
        NbtCompound child = nbt.getNBTCompound("child")
            .map(NbtCompound::clone0) // 浅克隆
            .unwrapOrGet(NbtCompound::newInstance); // 不存在则newInstance
        child.put("value", 114514); // 修改
        nbt.put("child", child); // 将结果放回
        ```

        也可以直接使用等效的修订（revise）
        ```java
        for(NbtCompound child: nbt.reviseNbtCompoundOrNew("child")) // 修订
        {
            child.put("value", 114514); // 修改
        }
        ```
        修订方法的返回值为#link(meta.root+"dev/core/editor")[`Editor`]，更详细的例子在#link(meta.root+"dev/mc/item/item#自定义数据-修改自定义数据")[`Item`]中

/ #[= `NbtList`]:

    类似`List<NbtElement>`的结构，但*所有元素必须为同一类型*

    使用`newInstance`创建实例

    ```java
    NbtList nbt = NbtList.newInstance();
    NbtList nbtListInt = NbtList.newInstance(NbtInt.newInstance(114), NbtInt.newInstance(514));
    ```

    使用`add`和`set`修改元素

    ```java
    nbt.add(NbtString.newInstance("hello"));
    nbt.add(NbtString.newInstance("world"));
    nbt.set(1, NbtString.newInstance("nbt"));
    ```

    使用`get`获取元素

    ```java
    NbtElement e1 = nbt.get(0);
    String e2 = nbt.getString(1);
    ```

/ #[= 保存到文件]:

    使用`NbtIo`类

    将一个`NbtElement`存入文件，*原则上它必须是`NbtCompound`*

    ```java
    NbtIo.write(nbt, dataOutput);
    ```

    读取时使用`NbtSizeTracker`限制其大小，`NbtSizeTracker.newInstance()`是默认最大限度
    ```java
    NbtElement nbt = NbtIo.read(dataInput, NbtSizeTracker.newInstance());
    ```

    / #[== 压缩保存]:

        推荐使用默认的压缩格式保存：*仅支持`NbtCompound`*

        ```java
        NbtIo.writeCompoundCompressed(nbt, stream);
        ```
        ```java
        NbtCompound nbt = NbtIo.readCompoundCompressed(stream);
        ```