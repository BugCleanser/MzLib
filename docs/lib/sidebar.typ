#import "template.typ": *;

#set raw(lang: "java");

#let hides = (
    "lib",
)
#let aliases = (
    "index": "简介",

    "dev": "开发文档",
    "dev/core": "Core",
    "dev/core/tutorial": "指南",
    "dev/core/tutorial/0": "快速开始",
    "dev/core/tutorial/1": "1.Hello World",
    "dev/core/tutorial/2": "2.Config",
    "dev/core/event": "事件",
    "dev/core/wrapper": "包装类",
    "dev/core/option": `Option`,
    "dev/core/async_function": "异步函数",
    "dev/core/auto_completable": `AutoCompletable`,
    "dev/core/editor": `Editor`,
    "dev/core/compound": `Compound`,

    "dev/mc": "Minecraft",
    "dev/mc/tutorial": "指南",
    "dev/mc/tutorial/0": "快速开始",
    "dev/mc/tutorial/1": "1.基本结构与约定",
    "dev/mc/tutorial/2": "2.创建插件和模块",
    "dev/mc/tutorial/3": "3.创建简单命令",
    "dev/mc/tutorial/4": "4.监听事件",
    "dev/mc/tutorial/5": "5.配置文件",
    "dev/mc/command": "命令",
    "dev/mc/window": "窗口",
    "dev/mc/text": "文本组件",
    "dev/mc/network_packet": "网络数据包",
    "dev/mc/bukkit": "配合BukkitAPI使用",

    "dev/mc/demo": "示例",

    "dev/mc/item": "物品",
    "dev/mc/item/item": `Item`,
    "dev/mc/item/player_head": "玩家头颅与玩家档案描述",

    "user": "用户手册",
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
            if name == "index" and path != "" {
                continue;
            };
            if hides.contains(path+name) {
                continue;
            };
            result.push(link(meta.root+path+name, aliases.at(path+name, default: name)));
        } else {
            if hides.contains(path+name) {
                continue;
            };
            result.push[
                #if children.keys().map(stem).contains("index") {
                    link(meta.root+path+name+"/index", aliases.at(path+name, default: name))
                } else {
                    aliases.at(path+name, default: name)
                };
                #html_elem("button", attrs: (class: "open"))[]
                #gen(children, path: path+name+"/")
            ];
        };
    };
    return list(..result);
}

#html_elem("aside", gen(meta.fileTree));
#importStyle("sidebar.css", "lib/");
#importScript("sidebar.js", "lib/");
