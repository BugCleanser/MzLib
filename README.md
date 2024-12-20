<div align=center> 

<img src="https://raw.githubusercontent.com/BugCleanser/MzLib/main/.github/assets/banner.png"/>

***A Minecraft Plugin***

![Code-Size](https://img.shields.io/github/languages/code-size/BugCleanser/MzLib?style=flat-square)
![Release](https://img.shields.io/github/v/release/BugCleanser/MzLib?style=flat-square)
![Actions](https://img.shields.io/github/actions/workflow/status/BugCleanser/MzLib/build.yml?style=flat-square)
![Group](https://img.shields.io/badge/group-750455476-yellow?style=flat-square)

</div>

<br>

## 开发教程参见[Wiki](https://github.com/BugCleanser/MzLib/wiki)

# 以下页面已过时
## 概述
MzLib 是一个基于 Bukkit 平台的开发类库，同时自带许多基础功能。
> 本插件同时在 [Mcbbs](https://www.mcbbs.net/thread-1250793-1-1.html) 上发布。

## 特性
- **多语言支持!** MzLib 将自动获取每个玩家客户端所设置的语言并用于显示！
- **地图画支持!** MzLib 提供了地图画相关的 API，并支持从图片文件加载地图画！
- **附魔支持!** MzLib 附魔指令支持多语言，且代理附魔显示，将原版乱码替代为罗马数字与阿拉伯数字！
- **掉落物名称显示!** MzLib 通过网络数据包修改实时显示掉落物名称，不会影响实体本身的数据，且支持多语言！
- **手持物品展示!** MzLib 通过网络数据包修改将聊天消息中的 `%i` 替换为手中物品信息，并支持修改更多命令参数！

更多特性、功能、效果展示及插件配置详见 [Wiki](https://github.com/BugCleanser/MzLib/wiki)。

## 安装
> 本插件需要 Java 8 及以上，理论支持在 Spigot 1.12+ 的所有原生服务端及其下游上运行，不兼容 Hybrid 服务端（如 CatServer、Mohist 等）。
> 
> 支持在 Windows、Linux、Docker 环境下运行，其中在 Docker 容器中运行可能需要安装 MzLibAgent。

### 本体安装
1. 在 [官网](https://mz.bugcleaner.cn:6/res/BukkitPlugins)，[Releases (稳定)](https://github.com/BugCleanser/MzLib/releases) 或 [Actions (测试)](https://github.com/BugCleanser/MzLib/actions) 下载插件本体；
2. 将插件本体放入服务端的 `plugins` 文件夹内；
3. 使用 [PlugManX](https://www.spigotmc.org/resources/plugmanx.88135/) 热加载 或执行 `/stop` 命令重启服务器以使用 MzLib。

> 如果按照以上步骤启动时报错（提示需安装 MzLibAgent），请参照下方安装步骤安装 MzLibAgent。若报错中不含此提示或在安装后仍然报错，请在 Issues 处提交问题反馈。

### MzLibAgent 安装（常见于 Docker 环境下）
1. 在 [官网](https://mz.bugcleaner.cn:6/res/BukkitPlugins)，[Releases (稳定)](https://github.com/BugCleanser/MzLib/releases) 或 [Actions (测试)](https://github.com/BugCleanser/MzLib/actions) 下载 MzLibAgent；
2. 将插件本体放入服务端根目录（与核心同级）；
3. 在服务端的启动参数内添加 `-javaagent:MzLibAgent.jar`（需在 `java` 参数之后，`-jar` 参数之前）；
4. 执行 `/stop` 重启服务器以使用 MzLib。

## 构建
构建 MzLib 需要 Java 8 及以上。

1. Clone 此项目；
2. 执行 `./gradlew shadowJar` 命令；
3. 最终构建产物生成在项目根目录的 `out` 文件夹中。

### FAQ
- Q: `PlugManX`依赖下载失败如何解决？
- A: 请参考此教程: [CSDN](https://blog.csdn.net/cxxxxxxxxxxxxx/article/details/106152542)

## 附属插件
- (官方) LoginAUI 铁砧登录页面 [Mcbbs](https://www.mcbbs.net/thread-1324546-1-1.html)
- (官方) MzBackwards 回跨版本显示优化 [Mcbbs](https://www.mcbbs.net/thread-1369629-1-1.html)
- (官方) MzItemStack 自定义物品堆叠 [Mcbbs](https://www.mcbbs.net/thread-1370314-1-1.html)

若您希望我们收录您基于 MzLib 开发的附属插件，可以在 [Issues](https://github.com/BugCleanser/MzLib/issues) 处提交您的插件信息！

## 支持与捐赠
若您觉得 MzLib 为您带来了有效的帮助，您可以选择捐赠以支持我们的工作！

![Plzzz](.github/assets/Plzzz.png)
![Pay](.github/assets/MzLibWePay.png)

您也可以选择为我们的 [Mcbbs](https://www.mcbbs.net/thread-1250793-1-1.html) 发布帖增加一个免费的三连！

[![3L](.github/assets/3L.png)](https://www.mcbbs.net/thread-1250793-1-1.html)

感谢您成为开源项目的支持者！

## 贡献
详见 [CONTRIBUTING.md](CONTRIBUTING.md)。
非常感谢您对此项目作出的任何贡献！

### 仓库贡献者
<a href="https://github.com/BugCleanser/MzLib/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=BugCleanser/MzLib" />
</a>

## 更新记录
详见 [CHANGELOG.md](CHANGELOG.md)。

## 开源协议
本项目源码采用 [Mozilla Public License Version 2.0](https://www.mozilla.org/en-US/MPL/) 开源协议。

<details>
  <summary>关于 MPL 协议</summary>

> Mozilla 公共许可证第二版（简称 MPL2.0）是一个弱 copyleft 许可证，但是其条款的特殊性质又使其更像一个宽松许可证（甚至，有人专门创造了词语 copycenter 来描述这一类许可证），该许可证虽然要求软件源代码需要使用相同许可证进行分发，但是对于可执行软件和包含本软件的大型作品的协议做出了宽松的要求。
>
> MPL2.0 被设计为兼容 GPL 的：其定义了“次要许可证”的概念：这些许可证包含 GPLv2，LGPLv2.1，AGPLv3 及其所有后续版本。对于在合并作品中使用与这些许可证不兼容的许可证时，MPL2.0 额外允许您根据这些次要许可证分发此类作品，且无须公开源代码。

<details>
  <summary>详细信息</summary>

> MPL2.0 许可证许可任何人使用，修改和分发程序及其源代码，额外的：
> 
> - 任何贡献者均不会因您选择根据 MPL2.0 的后续版本或根据次要许可证的条款（如果允许）分发本软件而授予额外的授权；
> - 所有以源代码形式分发的软件均应遵守 MPL2.0 许可证的条款。您必须告知接收者，软件的源代码形式受 MPL2.0 许可证条款的约束，以及他们如何获取 MPL2.0 许可证的副本。您不得尝试在源代码中更改或限制收件人的权利；
> - 以可执行文件形式分发的软件仍应同上述条款提供其源代码形式，且您即可以根据 MPL2.0 许可证，也可以根据其他不同的许可证对该软件的可执行文件形式进行再许可，前提是可执行软件的许可证不试图限制或更改接收者在 MPL2.0 许可证下在软件源代码部分中的权利；
> - 如果您打算分发一个合并作品，且该合并作品是该软件和受一个或多个次要许可证管辖的作品的组合，并且该软件与次要许可证不见容，则 MPL2.0 允许您根据此类次要许可证的条款额外分发本软件以便大型作品的开发者可以，根据他们的选择，使用 MPL2.0 或此类次要许可证的条款进一步分发该软件。

</details>
  
> *以上文字来自 [深入理解开源许可证（Open Source Licenses In Depth）](https://github.com/shaokeyibb/open-source-licenses-in-depth) 。*

</details>

## 鸣谢
- https://gitlab.ow2.org/asm/asm （本项目中包含的部分代码来源）
