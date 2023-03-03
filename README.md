<div align=center> 

<img src="https://raw.githubusercontent.com/BugCleanser/MzLib/main/.github/assets/banner.png"/>

***A base plugin and library in Bukkit***

![Code-Size](https://img.shields.io/github/languages/code-size/BugCleanser/MzLib?style=flat-square)
![Release](https://img.shields.io/github/v/release/BugCleanser/MzLib?style=flat-square)
![Actions](https://img.shields.io/github/actions/workflow/status/BugCleanser/MzLib/build.yml?style=flat-square)

</div>

<br>

## 概述
MzLib 是一个基于 Bukkit 平台的开发类库，同时自带许多基础功能。
> 本插件同时在 [Mcbbs](https://www.mcbbs.net/thread-1250793-1-1.html) 上发布。

## 安装
本体安装：
1. 在 [官网](https://mz.bugcleaner.cn:6/res/BukkitPlugins)，[Releases (稳定)](https://github.com/BugCleanser/MzLib/releases) 或 [Actions (测试)](https://github.com/BugCleanser/MzLib/actions) 下载插件本体；
2. 将插件本体放入服务端的 `plugins` 文件夹内；
3. 使用 [PlugManX](https://www.spigotmc.org/resources/plugmanx.88135/) 热加载 或执行 `/stop` 命令重启服务器以使用 MzLib。

> 如果按照以上步骤启动时报错（提示需安装 MzLibAgent），请参照下方安装步骤安装 MzLibAgent。若报错中未出现此提示，请提交问题反馈。

<br>

MzLibAgent 安装：
1. 在 [官网](https://mz.bugcleaner.cn:6/res/BukkitPlugins)，[Releases (稳定)](https://github.com/BugCleanser/MzLib/releases) 或 [Actions (测试)](https://github.com/BugCleanser/MzLib/actions) 下载 MzLibAgent；
2. 将插件本体放入服务端根目录（与核心同级）；
3. 在服务端的启动参数内添加 `-javaagent:MzLibAgent.jar`（需在 `java` 参数之后，`-jar` 参数之前）；
4. 重启服务器以使用 MzLib。

## 构建
1. Clone 此项目；
2. 执行 `./gradlew shadowJar` 命令；
3. 最终构建产物生成在项目根目录的 `out` 文件夹中。

### FAQ
- Q: `PlugManX` 依赖下载失败如何解决？
- A: 请参考此教程: [CSDN](https://blog.csdn.net/cxxxxxxxxxxxxx/article/details/106152542)

## 支持与捐赠
若您觉得 MzLib 为您带来了有效的帮助，您可以选择捐赠以支持我们的工作！
![image](https://user-images.githubusercontent.com/75569395/222644311-97cd65d4-cfe5-45c9-a8ca-76511426ab61.png)

## 开源协议
本项目源码采用 [Mozilla Public License Version 2.0](https://www.mozilla.org/en-US/MPL/) 开源协议。
