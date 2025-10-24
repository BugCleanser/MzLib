#import "meta.typ": *

#html_elem("style", ```
    body {
        display: flex;
        margin: 0;
        height: 100vh;
    }

    aside {
        width: 200px;
        background: #f0f0f0;
        padding: 20px;
    }

    main {
        flex: 1;
        padding: 20px;
    }
```.text)

#let stem(name) = {
    let index = none;
    for i in range(0, name.len()) {
        if name.at(i) == "." {
            index = i;
        }
    }
    if index == none {
        return name;
    }
    return name.slice(0, index);
}

#let gen(content, path) = list(..content.pairs().map(((name, children)) => {
    if children == none {
        return link(path+stem(name), stem(name))
    }
    return [
        #name
        #gen(children, path+name+"/")
    ];
}))

#html_elem("aside")[
    #gen(meta.fileTree, "")
]