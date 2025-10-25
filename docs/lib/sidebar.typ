#import "meta.typ": *

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

#let gen(content, path) = list(..content.pairs().map(((name, children)) => {
    if children == none {
        return link(path+stem(name), stem(name))
    }
    return [
        #link("#", name)
        #gen(children, path+name+"/")
    ];
}))

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

    aside > div:first-child {
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
        content: "📁";
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
#html_elem("aside")[
    = awa
    #gen(meta.fileTree, "/MzLib/")
];
#html_elem("script", ```js
    document.addEventListener('DOMContentLoaded', function() {
        document.querySelectorAll('aside li:has(ul) > *:first-child').forEach(item => {
            item.addEventListener('click', function(e) {
                e.preventDefault();
                const parent = this.parentElement;

                // 切换打开状态
                parent.classList.toggle('open');
            });
        });
    });
```.text);
