#import "./template.typ": *;

#let template(con) = context if not is_html() [
    #show raw.where(lang: "output"): set text(fill: white);
    #show raw.where(block: false): it => box(inset: (left: 2pt, right: 2pt), box(it, fill: if it.lang == "output" { black } else { silver }, outset: (top: 2pt, bottom: 2pt), inset: (left: 2pt, right: 2pt)));
    #show raw.where(block: true): it => if it.lang == "output" { 
        box(it, fill: black, inset: 6pt, width: 100%) 
    } else { 
        box(it, stroke: 0.6pt, inset: 6pt, width: 100%) 
    };
    #con;
] else [
    #html_elem("template", id: "code-block")[
        #html_elem("link", href: "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css", rel: "stylesheet")
        #html_elem("div", part: "header")[
            #html_elem("div", part: "language-badge")
            #html_elem("button", part: "copy", {
                html_elem("i", class: "far fa-copy")
                "复制"
            })
        ]
        #html_elem("div", part: "content")[
            #html_elem("pre", part: "pre")[
                #html_elem("code", part: "code")[
                    #html_elem("slot")[code]
                ]
            ]
        ]
    ]
    #show raw.where(block: false): r => html.code(r.text, class: "language-"+r.lang);
    #show raw.where(block: true): r => html.elem("code-block", attrs: if r.lang != none {(lang: r.lang)} else {(:)}, html.pre(r.text));
    #html_elem("link", attrs: (href: "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css", rel: "stylesheet"))[];
    #html_elem("link", attrs: (href: "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.7.0/styles/atom-one-light.min.css", rel: "stylesheet"))[];
    #html_elem("script", attrs: (src: "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/highlight.min.js"))[];
    #importStyle("code_block.css", "lib/");
    #importScript("code_block.js", "lib/");
    #con;
];