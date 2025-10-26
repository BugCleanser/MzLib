#import "meta.typ": *;

#import "style.typ"
#import "code_block.typ"

#import "sidebar.typ";

#let title() = html_elem("h1", context document.title)

#let hr = html_elem("hr")

#let template(content) = [
    #show: style.template
    #show: code_block.template
    #html_elem("style", ```
        body {
            background-color: #f5f7fa;
            color: #333;
            min-height: 100vh;
        }

        main {
            width: 800px;
            background-color: white;
            padding: 30px;
            margin: 0 auto;
            min-height: 100vh;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
    ```.text);
    #html_elem("style", ```css
        p {
            margin-top: 0;
            margin-bottom: 0;
            padding-top: 1em;
            padding-bottom: 1em;
        }
    ```.text);
    #sidebar;
    #html_elem("title", context document.title)
    #html_elem("main")[
        #content
    ];
]
