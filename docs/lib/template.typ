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
    #html_elem("title", context document.title)
    #html_elem("style", ```
        body {
            background-color: #fdfdfd;
            min-height: 100vh;
            display: grid;
            grid-template-columns: minmax(auto, 160pt) minmax(auto, 600pt) minmax(auto, 160pt);
            gap: 27pt;
            justify-content: center;
            box-sizing: border-box;
        }

        main {
            flex-direction: column;
            padding: 0 24pt;
            min-height: 100vh;
        }
    ```.text);
    #html_elem("style", ```css
        body {
            font-family: HK Grotesk, Inter, system-ui, -apple-system, BlinkMacSystemFont, Segoe UI, Roboto, Oxygen, Ubuntu, Cantarell, Open Sans, Helvetica Neue, sans-serif;
            color: #19181f;
            font-size: 12pt;
        }
        p {
            margin-block-start: .5rem;
            margin-block-end: .5rem;
            display: block;
            margin-inline-start: 0px;
            margin-inline-end: 0px;
            unicode-bidi: isolate;
        }
    ```.text);
    #import "sidebar.typ";
    #sidebar;
    #import "catalogue.typ";
    #html_elem("main")[
        #show: catalogue.template;
        #content;
    ];
    #catalogue
]
