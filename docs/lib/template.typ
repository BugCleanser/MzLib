#import "meta.typ";

#import "content.typ";
#import content: *;

#let template(con) = [
    #html_elem("meta", attrs: (name: "root", content: meta.root))[];
    #content;
    #importStyle("template.css", "lib/");
    #import "style.typ";
    #show: style.templateStyle
    #style;
    #import "code_block.typ";
    #show: code_block.template;
    #html_elem("title", contentToString(context document.title));
    #import "sidebar.typ";
    #sidebar;
    #import "catalogue.typ";
    #html_elem("main")[
        #show: catalogue.template;
        #con;
    ];
    #catalogue;
];
