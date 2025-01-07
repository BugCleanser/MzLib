# MzLib Changelog (Since v10)

- MzLib v10.0.1-beta-dev7
  - 添加实体数据追踪器
- MzLib v10.0.1-beta-dev7
  - 优化数据包同步监听
  - 改进物品升级策略
  - 兼容至1.15+
- MzLib v10.0.1-beta-dev6
  - 添加语言文件
  - 添加配置文件
  - 添加自定义语言编辑器
  - 兼容至1.16+
- MzLib v10.0.1-beta-dev5
  - 兼容至1.17+
- MzLib v10.0.1-beta-dev4
  - 修复有概率触发的异步报错
  - 添加了基本测试器
  - 兼容至MC1.19+
- MzLib v10.0.1-beta-dev3
  - 兼容至MC1.20.5+
- MzLib v10.0.1-beta-dev2
  - 增加了命令补全
  - 添加命令/givenbt
- MzLib v10.0.1-beta-dev1
  - 重构了项目，现在与Bukkit解耦


- MzLib v10.0.1-pre48
  - 修复与paper1.17.1的兼容性问题
  - 开源到GitHub
  - 现在支持展示快捷栏的任意物品了（如%1%2%i)
  - 添加了语言文件夹的递归加载
  - 自带了兼容EcoEnchants的语言文件
  - 修复插件卸载时可能出现的死递归报错
  - 修复了部分nms映射错误

- MzLib v10.0.1-pre47
  - 更新API
  - 重写底层实现以优化效率和体积
  - 优化合成
  - 现在地图画可以直接用网址加载了
  - 地图画加载使用了抖动算法
  - 删除了fengjian接口，取而代之的是更快更灵活的nothing接口
  - 再次重写配方编辑器
  - 压缩了地图画的nbt
  - 兼容了docker容器以及Java8+的所有JRE
  - 兼容了1.19.3
  - 修复了与虚拟实体(如QS商店悬浮物)的兼容性问题
  - 为命令添加了权限
  - 添加命令/mzlib skull

- MzLib v10.0.1-pre46
  - 修复兼容spigot1.16.5~1.18.2
  - 修复部分细节问题
  - 优化凋落物名称显示
  - 更新API
  - 增加地图画功能
  - 改进命令提示

- MzLib v10.0.1-pre45
  - 少量优化
  - 修复只能热加载的问题

- MzLib v10.0.1-pre44  
  - 优化底层实现
  - 优化铁砧界面
  - 提供简易脚本接口

- MzLib v10.0.1-pre43
  - 修复高版本铁砧界面的bug

- MzLib v10.0.1-pre42
  - 兼容Paper-1.16.5
  - 兼容Paper-1.19
  - 兼容至MC1.19.2

- MzLib v10.0.1-pre41
  - 更新API
- MzLib v10.0.1-pre40
  - 更新API
- MzLib v10.0.1-pre39
  - 因导致卡顿，暂时禁用了合成书
- MzLib v10.0.1-pre38
  - 更新API
- MzLib v10.0.1-pre37
  - 修复了1.16前聊天不兼容字体问题
- MzLib v10.0.1-pre36
  - 修复了1.17+异步触发事件问题

- MzLib v10.0.1-pre35  
  - 重写了聊天组件API以兼容所有消息
  - 修复了1.13.x村民交易界面的兼容性问题
  - 修复了1.13.x物品名称显示的问题

- MzLib v10.0.1-pre34
  - 重写插件架构
  - 重写配方编辑器
  - 兼容旧版QuickShop
  - 修复bug
  - 兼容了1.19、1.15、1.13
  - 优化封装和注入

- MzLib v10.0.1-pre33
  - 修复部分问题
- MzLib v10.0.1-pre32
  - 修复部分问题
- MzLib v10.0.1-pre31
  - 修复部分问题
- MzLib v10.0.1-pre30
  - 修复部分问题

- MzLib v10.0.1-pre29  
  - 添加铁砧配方
  - 优化部分细节问题

- MzLib v10.0.1-pre28  
  - 兼容了MC1.18.2
  - 修复部分细节问题
 
- MzLib v10.0.1-pre27
  - 添加燃料编辑器
  - 修复了部分原版熔炉配方在1.12.x失效的问题

- MzLib v10.0.1-pre26  
  - 添加熔炉配方编辑器
  - 添加配方ID编辑功能（待完善）

- MzLib v10.0.1-pre25
  - 优化部分API
  - 添加主手物品展示功能
  - 重写并改进了配方编辑器
  - 添加了对交易界面中的物品显示处理
  - 添加了对消息中的物品显示处理
  - 添加物品信息的复制功能
  - 添加了协议工具，现在MzLib不需要依赖于ProtocolLib了

- MzLib v10.0.1-pre24
  - 修复少量细节问题
- MzLib v10.0.1-pre23
  - 修复少量细节问题
- MzLib v10.0.1-pre22
  - 添加配置重载命令
- MzLib v10.0.1-pre21
  - 修复若干bug

- MzLib v10.0.1-pre20
  - 测试并兼容了MC1.18.1
  - 添加字节码修改、网络交互模块

- MzLib v10.0.1-pre19
  - 修复pre18版本的更改导致无法打开配方编辑器的问题
- MzLib v10.0.1-pre18
  - 增加了对掉落物名称中未本地化文本的处理（例如灾厄旗帜）
- MzLib v10.0.1-pre17
  - 读取nbt允许了近似的数值类型
- MzLib v10.0.1-pre16
  - 增加自定义nbt配方功能
- MzLib v10.0.1-pre15
  - 修复配方中无法加入1.13后的物品的兼容性问题
- MzLib v10.0.1-pre14
  - 修复无序配方重启后失效的bug
- MzLib v10.0.1-pre13
  - 修复与其他附魔插件的命令的兼容问题
- MzLib v10.0.1-pre12
  - 自定义配方功能现在支持无序配方了

- MzLib v10.0.1-pre11
  - 修复扁平化前版本中添加配方时报错bug
  - 添加本地化附魔命令

- MzLib v10.0.1-pre10
  - bug修复
- MzLib v10.0.1-pre9
  - 添加自定义配方功能

- MzLib v10.0.1-pre8
  - 修复诅咒附魔显示异常bug
  - 兼容了GoldenEnchants

- MzLib v10.0.1-pre7  
  - 增加罗马数字上限设置
  - 兼容了DeEnchantment和ExcellentEnchants

- MzLib v10.0.1-pre6
  - 重写大部分代码，修复兼容性bug
- MzLib v10.0.1-pre5
  - 测试并兼容了核心Akarin-1.14.4
- MzLib v10.0.1-pre4
  - 兼容了其他附魔插件（需要自己手动改语言文件）
- MzLib v10.0.1-pre3
  - 将几乎所有信息移至语言文件（可编辑）
- MzLib v10.0.1-pre2
  - 测试并兼容了核心Spigot-1.12
- MzLib v10.0.1-pre1
  - v10第一个预览版


- MzLib v8
  - 再次重构
- MzLib v7
  - 再次重构
- MzLib v6
  - 再次重构
- MzLib v5
  - 再次重构，这次没那么史了
- MzLib v4
  - 使用Java重写，这时候写的就是一坨史 
- MzLib v3
  - 使用Skript脚本重写
- MzLib v2.7
  - 现在支持自定义方块实体了
- MzLib v2.5
  - 增加一个C语言程序用于编译脚本，现在mcfunction由它生成
- MzLib v2
  - 将命令迁移至mcfunction
- MzLib v1
  - 基于1.12.2使用命令方块编写的一组功能