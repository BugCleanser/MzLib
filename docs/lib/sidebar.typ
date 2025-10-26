#import "meta.typ": *

#let root = "/MzLib/"
#let hides = ("lib/",)
#let aliases = (
    "index": "📄 简介",

    "development/": "💻开发文档",
    "development/core/tutorial/": "📚指南",
    "development/core/tutorial/0": "🚀快速开始",
    "development/core/tutorial/1": "1.Hello World",
    "development/core/tutorial/2": "1.Config",
    "development/core/event": "📢事件",
    "development/core/wrapper": "📦包装类",
    "development/core/option": "Option类",
    "development/core/async_function": "⏳异步函数",
    "development/core/compound": "🧩Compound类",

    "development/minecraft/tutorial/": "📚指南",
    "development/minecraft/tutorial/0": "🚀快速开始",
    "development/minecraft/tutorial/1": "基本结构与约定",
    "development/minecraft/tutorial/2": "创建插件和模块",
    "development/minecraft/tutorial/3": "创建简单命令",
    "development/minecraft/tutorial/4": "监听事件",
    "development/minecraft/tutorial/5": "配置文件",
    "development/minecraft/command": "💬命令",
    "development/minecraft/window": "🗔窗口",
    "development/minecraft/text": "📝文本组件",
    "development/minecraft/network_packet": "🌐网络数据包",
    "development/minecraft/bukkit": "🔌配合BukkitAPI使用",

    "development/minecraft/demo/": "🧪示例",

    "development/minecraft/item/": "💎物品",
    "development/minecraft/item/player_head": "🧑玩家头颅与玩家档案描述",

    "user/": "💡用户手册"
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
                #aliases.at(path+name+"/", default: name)
                #gen(children, path: path+name+"/")
            ];
        }
    }
    return list(..result);
}

#html_elem("aside")[
    = MzLib
    #gen(meta.fileTree)
];
#html_elem("style", ```
    aside {
        width: 100pt;
        background-color: #2c3e50;
        color: white;
        padding: 20px;
        position: fixed;
        top: 0;
        left: 0;
        height: 100vh;
        overflow-y: auto;
    }
    @media (min-width: 1000pt) {
        aside {
            width: calc(30% - 200pt);
        }
    }

    aside * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
        font-family: 'Segoe UI', 'Microsoft YaHei', sans-serif;
    }

    aside h2 {
        padding: 0 20px 20px;
        border-bottom: 1px solid rgba(255, 255, 255, 0.1);
        margin-bottom: 20px;
    }

    aside h2 {
        font-size: 1.5rem;
        font-weight: 600;
        display: flex;
        align-items: center;
        gap: 10px;
    }

    aside h2::before {
        content: "📖";
        font-size: 1.8rem;
    }

    aside ul {
        list-style: none;
    }

    aside li {
        position: relative;
    }

    aside li > *:first-child {
        display: flex;
        align-items: center;
        padding: 12px 20px;
        color: rgba(255, 255, 255, 0.85);
        text-decoration: none;
        transition: all 0.3s ease;
        border-left: 3px solid transparent;
    }

    aside li > *:first-child:hover {
        background-color: rgba(255, 255, 255, 0.1);
        color: white;
        border-left-color: #1abc9c;
    }

    aside li.active > *:first-child {
        background-color: rgba(255, 255, 255, 0.15);
        color: white;
        border-left-color: #e74c3c;
    }

    aside li:has(ul) > p::after {
        content: "▶";
        margin-left: auto;
        font-size: 0.8rem;
        transition: transform 0.3s ease;
    }

    aside li:has(ul).open > *:first-child::after {
        transform: rotate(90deg);
    }

    aside ul ul {
        max-height: 0;
        overflow: hidden;
        transition: max-height 0.4s ease;
        background-color: rgba(0, 0, 0, 0.1);
    }

    aside li.open > ul {
        max-height: 500px;
    }

    aside ul ul li > *:first-child {
        padding-left: 40px;
        font-size: 0.9rem;
    }

    aside ul ul ul li > *:first-child {
        padding-left: 60px;
    }

    aside .menu-icon {
        margin-right: 10px;
        font-size: 1.1rem;
        width: 20px;
        text-align: center;
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
                link.closest('li').classList.add('active');
                let parent = link.closest('*:has(ul)');
                while (parent) {
                    parent.classList.add('open');
                    parent = parent.parentElement?.closest('*:has(ul)');
                }
            }
        });
        document.querySelectorAll('aside li:has(ul) > *:first-child').forEach(item => {
            item.addEventListener('click', function(e) {
                e.preventDefault();
                this.parentElement.classList.toggle('open');
            });
        });
    });
```.text);
