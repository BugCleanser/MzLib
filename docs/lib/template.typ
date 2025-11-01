#import "content.typ": *;

#let template(con) = [
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
