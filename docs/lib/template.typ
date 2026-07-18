#import "./meta.typ";

#import "contents.typ";
#import contents: *;

#let template(con, sidebar: true, title: none) = context {
    set text(lang: "zh", font: "Microsoft YaHei", size: 12pt);
    show raw: set text(font: ("Consolas", "Microsoft YaHei"));
    html_elem("meta", attrs: (name: "root", content: meta.root));
    include contents;
    importStyle("template.css", "lib/");
    import "style.typ";
    show: style.templateStyle;
    include style;
    import "math_render.typ";
    show: math_render.template;
    include math_render;
    import "code_block.typ";
    show: code_block.template;
    if title != none {
        html_elem("title", contentToString(title));
    };
    if sidebar {
        importScript("sidebar.js", "lib/");
        html_elem("sidebar-component")[];
    };
    import "catalogue.typ";
    if is_html() {
        html_elem("main")[
            #show: catalogue.template;
            #if title != none {
                html_elem("h1", title)
            }
            #con
        ]
        include catalogue;
    } else {
        con
    }
}
