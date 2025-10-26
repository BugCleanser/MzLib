#import "./template.typ": *

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
        if c.inset != none {
            style.insert("padding", styleRelative(c.inset));
        }
        // TODO
        html_elem("div", attrs: ("style": strStyle(style)), c.body)
    };
    #show grid: c=>c.children.sum();// TODO
    #show grid.cell: c=>c.body;// TODO
    #content
]