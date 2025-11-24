#import "./../../lib/lib.typ": *;

#set document(title: [安装MzLibMinecraft]);

#show: template;

#title();

首先在群内或#link("https://github.com/BugCleanser/MzLib/releases")[Github Release]中下载最新版MzLibMinecraft的jar

/ #[= 作为Bukkit插件安装]:

    注意，当你使用mod混合端时，不应该通过此种方式安装

    作为Bukkit插件，只需丢进plugins文件夹然后重启你的服务端即可

    若已安装PlugMan，则可以使用```command /plugman load MzLib```而无需重启服务端

/ #[= 作为Fabric mod安装]:

    将插件放入mods文件夹，然后重启你的服务端

/ #[= 其它平台]:

    暂不支持其它平台，如果有这方面的需求可以联系我们

/ #[= 测试兼容性]:

    首次安装在您所在的平台或MC版本，可以测试插件的兼容性，安装后使用命令

    ```command
    /mzlib test
    ```

    在控制台执行以进行主要测试，或玩家执行以进行全部测试