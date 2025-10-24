#import "meta.typ": *

#import "sidebar.typ"

#let template(content) = [
    #show raw.where(block: true): r=>[
        #grid(box(r.lang, stroke: color.aqua, inset: 6pt),
            box(r, stroke: color.aqua, inset: 8pt))
    ]
    #html_elem("aside")[
        #sidebar
    ]
    #html_elem("main")[
        #content
    ]
]

