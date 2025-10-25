#import "meta.typ": *;

#import "style.typ"
#import "code_block.typ"

#import "sidebar.typ";

#let template(content) = [
    #show: style.template
    #show: code_block.template
    #html.elem("aside")[
        #sidebar
    ];
    #html.elem("main")[
        #content
    ];
]
