#import "meta.typ": *;

#import "sidebar.typ";

#let styleColor(value) = {
    if value == auto {
        value = color.black;
    }
    return value.to-hex();
}

#let styleLength(value) = {
    if value == auto {
        value = 1pt;
    }
    return [#value].text;
}

#let styleRatio(value) = {
    if value == auto {
        value = 100%;
    }
    return [#value].text;
}

#let styleRelative(value) = {
    if value.length == 0pt {
        return styleRatio(value.ratio);
    }
    if value.ratio == 0% {
        return styleLength(value.length);
    }
    return "calc(" + styleRatio(value.ratio) + " + " + styleLength(value.length) + ")";
}

#let styleStroke(value) = {
    if value == auto {
        value = 1pt + black;
    }
    return styleLength(value.thickness)+" solid "+styleColor(value.paint);
}

#let strStyle(value) = {
    if value.len() == 0 {
        return "";
    }
    return value.pairs().map(((k, v))=>k+": "+v+";\n").sum();
}

#let template(content) = [
    #html.elem("link", attrs: (href: "https://cdn.jsdelivr.net/npm/prismjs@1.29.0/themes/prism.min.css", rel: "stylesheet"))
    #html.elem("script", attrs: (src: "https://cdn.jsdelivr.net/npm/prismjs@1.29.0/components/prism-core.min.js"))
    #html.elem("script", attrs: (src: "https://cdn.jsdelivr.net/npm/prismjs@1.29.0/plugins/autoloader/prism-autoloader.min.js"))
    #show box: c => {
        let style = (:)
        if c.width != auto {
            style.insert("width", styleRelative(c.width));
        }
        if c.height != auto {
            style.insert("height", styleRelative(c.height));
        }
        if c.stroke != none {
            style.insert("border", styleStroke(c.stroke));
        }
        // TODO
        html.elem("div", attrs: ("style": strStyle(style)), c.body)
    };
    #show grid: c=>c.children.sum();// TODO
    #show raw.where(block: true): r=>[
        #grid(
            box(r.lang, stroke: color.aqua, inset: 6pt),
            box(html.elem("pre", html.elem("code", attrs: (class: "language-"+r.lang), r.text)), stroke: color.aqua, inset: 8pt))
    ];
    #html_elem("aside")[
        #sidebar
    ];
    #html_elem("main")[
        #content
    ];
]
