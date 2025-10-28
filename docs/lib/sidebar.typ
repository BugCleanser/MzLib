#import "template.typ": *;

#let hides = ("lib",)
#let aliases = (
    "index": "简介",

    "development": "开发文档",
    "development/core": "Core",
    "development/core/tutorial": "指南",
    "development/core/tutorial/0": "快速开始",
    "development/core/tutorial/1": "1.Hello World",
    "development/core/tutorial/2": "2.Config",
    "development/core/event": "事件",
    "development/core/wrapper": "包装类",
    "development/core/option": "Option类",
    "development/core/async_function": "异步函数",
    "development/core/compound": "Compound类",

    "development/minecraft": "Minecraft",
    "development/minecraft/tutorial": "指南",
    "development/minecraft/tutorial/0": "快速开始",
    "development/minecraft/tutorial/1": "1.基本结构与约定",
    "development/minecraft/tutorial/2": "2.创建插件和模块",
    "development/minecraft/tutorial/3": "3.创建简单命令",
    "development/minecraft/tutorial/4": "4.监听事件",
    "development/minecraft/tutorial/5": "5.配置文件",
    "development/minecraft/command": "命令",
    "development/minecraft/window": "窗口",
    "development/minecraft/text": "文本组件",
    "development/minecraft/network_packet": "网络数据包",
    "development/minecraft/bukkit": "配合BukkitAPI使用",

    "development/minecraft/demo": "示例",

    "development/minecraft/item": "物品",
    "development/minecraft/item/player_head": "玩家头颅与玩家档案描述",

    "user": "用户手册"
)

#let stem(name) = {
    let name = name.clusters()
    let index = none;
    for i in range(0, name.len()) {
        if name.at(i) == "." {
            index = i;
        }
    }
    if index == none {
        return name.sum();
    }
    return name.slice(0, index).sum();
}

#let gen(content, path: "") = {
    let result = ();
    for (name, children) in content.pairs() {
        if children == none {
            name = stem(name);
            if hides.contains(path+name) {
                continue;
            }
            result.push(link(meta.root+path+name, aliases.at(path+name, default: name)))
        }
        else {
            if hides.contains(path+name) {
                continue;
            }
            result.push[
                #aliases.at(path+name, default: name)
                #html_elem("button", attrs: (class: "open"))[▶]
                #gen(children, path: path+name+"/")
            ];
        }
    }
    return list(..result);
}

#html_elem("aside", gen(meta.fileTree));
#importStyle("sidebar.css", "lib/");
#importScript("sidebar.js", "lib/");
