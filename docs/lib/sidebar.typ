#import "/lib/lib.typ": *;
#set raw(lang: "java");
#show: template.with(sidebar: false);

#let hides = (
    "lib",
    "res",
)
#let aliases = (
    "index": "简介",

    "dev": "开发文档",
    "dev/core": "Core",
    "dev/core/tutorial": "指南",
    "dev/core/tutorial/1": "1.Hello World",
    "dev/core/tutorial/2": "2.Config",
    "dev/core/event": "事件",
    "dev/core/util/wrapper": "包装类",
    "dev/core/util/option": `Option`,
    "dev/core/util/async_function": "异步函数",
    "dev/core/util/auto_completable": `AutoCompletable`,
    "dev/core/util/editor": `Editor`,
    "dev/core/util/compound": `Compound`,
    "dev/core/util/module": "模块与注册",
    "dev/core/util/class_cache": `ClassCache`,

    "dev/mc": "Minecraft",
    "dev/mc/tutorial": "指南",
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
};

#let alias(path, name) = {
    let result = aliases.at(path+name, default: none);
    return result;
};

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
            result.push(link(meta.root+path+name, alias(path, name)));
        } else {
            if hides.contains(path+name) {
                continue;
            };
            result.push[
                #if children.keys().map(stem).contains("index") {
                    link(meta.root+path+name+"/index", alias(path, name))
                } else {
                    alias(path, name)
                };
                #html_elem("button", attrs: (class: "open"))[]
                #gen(children, path: path+name+"/")
            ];
        };
    };
    return list(..result);
}

#importStyle("sidebar.css", "lib/");
#gen(meta.fileTree);
