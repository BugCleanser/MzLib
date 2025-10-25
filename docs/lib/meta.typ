// #let html_elem = (tag, attrs: (:), body)=>body
// #let html_frame = body=>body
#let html_elem = html.elem
#let html_frame = html.frame
#let meta = (
    fileTree: (
        "index.typ": none,
        d1: (
            "f1.typ": none
        ),
        d2: (
            "f2.typ": none,
            d3: (
                "f3.typ": none,
                d4: (
                    "f4.typ": none,
                    d5: (
                        "f5.typ": none
                    )
                )
            )
        )
    )
)