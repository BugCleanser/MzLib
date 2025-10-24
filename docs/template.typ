#import "meta.typ": *

#import "sidebar.typ"

#let template(content) = [
    #show raw: r=>[
        #grid(box(r.lang, stroke: color.aqua, inset: 6pt),
            box(r, stroke: color.aqua, inset: 8pt))
    ]
    #sidebar
    #html_elem("main")[
        #content
    ]
]
