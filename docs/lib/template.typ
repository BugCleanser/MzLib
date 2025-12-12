#import "meta.typ";

#import "content.typ";
#import content: *;

#let template(con, sidebar: true, title: none) = [
    #html_elem("meta", attrs: (name: "root", content: meta.root))[];
    #content;
    #importStyle("template.css", "lib/");
    #import "style.typ";
    #show: style.templateStyle
    #style;
    #import "code_block.typ";
    #show: code_block.template;
    #if title != none {
        html_elem("title", contentToString(title));
    };
    #if sidebar {
        importScript("sidebar.js", "lib/");
        customElem("sidebar-component")[];
    };
    #import "catalogue.typ";
    #html_elem("main")[
        #show: catalogue.template;
        #if title != none {
            html_elem("h1", title);
        };
        #con;
    ];
    #catalogue;
];
