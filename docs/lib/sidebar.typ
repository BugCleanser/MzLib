#import "/lib/lib.typ": *;
#set raw(lang: "java");
#show: template.with(sidebar: false);

#let hides = (
    "lib",
    "res",
)
#let aliases = (
    "index": "简介",

    "dev/core": "Core",
    "dev/core/tutorial": "指南",
    "dev/core/event": "事件",
    "dev/core/util/module": "模块与注册",

    "dev/mc": "Minecraft",
    "dev/mc/tutorial": "指南",

    "dev/mc/item": "物品",

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

#let alias(path, name, isDir, exists) = {
    let result = aliases.at(path+name, default: none);
    if result == none {
        result = name;
        if not exists {
            return result;
        };
        let file = if isDir {
            "/"+path+name+"/index.typ"
        } else {
            "/"+path+name+".typ"
        };
        import file as m;
        if "title" in dictionary(m) {
            result = m.title;
        };
    };
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
            result.push(link(meta.root+path+name, alias(path, name, false, true)));
        } else {
            if hides.contains(path+name) {
                continue;
            };
            result.push[
                #if children.keys().map(stem).contains("index") {
                    link(meta.root+path+name+"/index", alias(path, name, true, true))
                } else {
                    alias(path, name, true, false)
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
