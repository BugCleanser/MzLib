#import "meta.typ": *

#html_elem("style", ```
    .sidebar {
                width: 200px;
                height: 100vh;
                background: #f0f0f0;
                padding: 20px;
                position: fixed;
                left: 0;
                top: 0;
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
        return link(path+name, stem(name))
    }
    return [
        #name
        #gen(children, path+name+"/")
    ];
}))

#html_elem("div", attrs: (class: "sidebar"))[
    #gen(meta.fileTree, "")
]