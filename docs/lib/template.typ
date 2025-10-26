#import "meta.typ": *;

#let html_elem(tag, attrs: (:), body) = context{
    if target() == "html" {
        return html.elem(tag, body, attrs: attrs);
    }
    return body;
}
#let html_frame(body) = context{
    if target() == "html" {
        return html.frame(body);
    }
    return body;
}

#let title() = html_elem("h1", context document.title)

#let hr = html_elem("hr")[]

#let template(content) = [
    #import "style.typ"
    #show: style.template
    #import "code_block.typ"
    #show: code_block.template
    #import "sidebar.typ";
    #sidebar;
    #html_elem("style", ```
        body {
            background-color: #f5f7fa;
            color: #333;
            min-height: 100vh;
        }

        main {
            width: 700pt;
            background-color: white;
            padding: 30pt;
            margin: 0 auto;
            min-height: calc(100% + 100vh);
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
    #html_elem("title", context document.title)
    #html_elem("main")[
        #content
    ];
]
