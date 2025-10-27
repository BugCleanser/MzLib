#import "template.typ": *;

#let root = "/MzLib/"
#let hides = ("lib/",)
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
            result.push(link(root+path+name, aliases.at(path+name, default: name)))
        }
        else {
            if hides.contains(path+name+"/") {
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
#html_elem("style", ```
    aside {
        padding: 9pt;
        padding-inline-end: 3pt;
        margin: 5pt -9pt -9pt;
    }

    aside * {
        transition: ease .2s max-height, ease .2s opacity;
    }

    aside a {
        margin-block-end: 4px;
    }

    aside > ul > li > *:first-child {
        margin-block-end: 9pt;
    }

    aside ul {
        list-style: none;
        padding-inline-start: 12pt;
    }
    aside > ul {
        padding-inline-start: 0pt !important;
    }

    aside li > *:first-child {
        display: block;
        padding: 2pt 12pt 2pt 6pt;
        border-radius: 6px;
    }

    aside li {
        position: relative;
    }

    aside li.open > *:first-child {
        background: #eff0f3;
    }

    aside li > button.open {
        display: block;
        position: absolute;
        top: 0pt;
        right: 0pt;
        border: 0pt;
        background: #fdfdfd;
        width: 18pt;
        height: 18pt;
        padding: 3pt;
        border-radius: 4pt;
    }
    @media (hover: hover) {
        aside li > button.open {
            display: none;
        }
    }
    aside li:hover > button.open {
        display: block;
    }
    aside li.open > button.open {
        background: #eff0f3;
        display: block;
        transform: rotate(90deg);
    }
    aside li > button.open:hover {
        background: #e4e5ea;
    }

    aside li > ul {
        overflow: visible hidden;
        max-height: 0px;
        pointer-events: none;
        opacity: 0;
        user-select: none;
    }
    aside li.open > ul {
        max-height: none;
        pointer-events: auto;
        opacity: 1;
        user-select: auto;
    }

    aside li.current > *:first-child {
        color: #11566c;
        font-weight: 600;
    }
    aside li.current > *:first-child:after {
        content: "";
        position: absolute;
        width: 3pt;
        height: 3pt;
        border-radius: 50%;
        background: #11566c;
        right: 8pt;
        top: 9pt;
        margin-block: auto;
    }

    aside a {
        color: inherit;
        text-decoration: none;
    }

    aside a:hover {
        text-decoration: underline;
    }
```.text);
#html_elem("script", ```js
    document.addEventListener('DOMContentLoaded', function() {
        let currentPath = decodeURIComponent(window.location.pathname);
        if(currentPath.endsWith("/"))
            currentPath = currentPath.substr(0, currentPath.length-1);
        document.querySelectorAll('aside a').forEach(link => {
            const href = link.getAttribute('href');
            if (href === currentPath || href === currentPath+"/index") {
                link.closest('li').classList.add('current');
                let parent = link.closest('*:has(ul)');
                while (parent) {
                    parent.classList.add('open');
                    parent = parent.parentElement?.closest('*:has(ul)');
                }
            }
        });
        document.querySelectorAll('aside li > button.open').forEach(item => {
            item.addEventListener('click', function(e) {
                e.preventDefault();
                this.parentElement.classList.toggle('open');
            });
        });
    });
```.text);
