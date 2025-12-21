#import "/lib/lib.typ": *;
#set raw(lang: "java");
#let title = [Minecraft开发文档];
#show: template.with(title: title);

这里是MzLib-Minecraft的开发文档

= 依赖mzlib-minecraft

如果你要写mzlib-minecraft的下游程序（附属插件），依赖它

对于初学者，直接下载-all.jar然后导入

高手，请使用gradle(kts)导入，在build.gradle.kts中添加：

```kts
repositories {
    maven("https://maven.pkg.github.com/mzverse/mzlib") {
        credentials {
            username = System.getenv("GITHUB_USERNAME")
            password = System.getenv("GITHUB_TOKEN")
        }
    }
}
dependencies {
    compileOnly("org.mzverse:mzlib-minecraft:latest.integration")
}
```

确保你的环境变量中有GITHUB_USERNAME和GITHUB_TOKEN，并且token具有read:packages权限。

以上步骤依赖了最新快照版，仅依赖最新版请将"latest.integration"改为"latest.release"

= 版本表示和名称约定

为了简单起见，我们使用一个整数表示一个MC版本，即第二位版本号乘100加上第三位版本号

若MC版本"1.x.y"，用整数表示为`x*100+y`。
例如"1.12.2"表示为`1202`，"1.14"表示为`1400`，"1.21.5"表示为`2105`

从MC版本"26.1"起，"x.y"表示为`x*100+y*10`，"x.y.z"表示为`x*100+y*10+z`。
例如"26.1"表示为`2610`，"26.1.1"表示为`2611`

如果一个元素只在特定的版本段生效，则在标识符后加上"v"和版本段，若有多个版本段，使用两个下划线隔开

一个版本段是一个左开右闭区间[a,b)，表示为`a_b`；[a,+∞)表示为a，(+∞,b)表示为`_b`

例如，`exampleV1300`表示这个元素从1.13开始有效，即支持$[1.13, +infinity)$；`exampleV_1400`表示在1.14之前有效，即支持$[0, 1.14)$；`exampleV1300_1400`表示从1.13开始有效，从1.14开始失效，即支持$[1.13, 1.14)$

例如，`exampleV_1300__1400_1600`表示在(-∞, 1.13) ∪ [1.14, 1.16)有效；`exampleV_1600__1903`表示1.16之前和从1.19.3开始都有效，在[1.16, 1.19.3)无效

