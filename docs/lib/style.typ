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
    return repr(value);
}

#let styleRatio(value) = {
    if value == auto {
        value = 100%;
    }
    return repr(value);
}

#let styleRelative(value) = {
    if type(value) == fraction {
        return repr(value);
    }
    if value.ratio == 0% {
        return styleLength(value.length);
    }
    if value.length == 0pt {
        return styleRatio(value.ratio);
    }
    return "calc(" + repr(value) + ")";
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

#let mapBoxDic(dic) = {
    if dic.keys().contains("x") {
        if not dic.keys().contains("top") {
            dic.insert("top", dic.at("x"));
        }
        if not dic.keys().contains("bottom") {
            dic.insert("bottom", dic.at("x"));
        }
    }
    if dic.keys().contains("y") {
        if not dic.keys().contains("left") {
            dic.insert("left", dic.at("y"));
        }
        if not dic.keys().contains("right") {
            dic.insert("right", dic.at("y"));
        }
    }
    if dic.keys().contains("rest") {
        if not dic.keys().contains("top") {
            dic.insert("top", dic.at("rest"));
        }
        if not dic.keys().contains("bottom") {
            dic.insert("bottom", dic.at("rest"));
        }
        if not dic.keys().contains("left") {
            dic.insert("left", dic.at("rest"));
        }
        if not dic.keys().contains("right") {
            dic.insert("right", dic.at("rest"));
        }
    }
    dic.remove("x", default: none);
    dic.remove("y", default: none);
    dic.remove("rest", default: none);
    return dic;
}

#let styleCommon(c) = {
    let style = (:)
    if c.width != auto {
        style.insert("width", styleRelative(c.width));
    }
    if c.height != auto {
        style.insert("height", styleRelative(c.height));
    }
    if c.stroke != none {
        if type(c.stroke) == dictionary {
            for (k, v) in mapBoxDic(c.stroke).pairs() {
                style.insert("border-"+k, styleStroke(v));
            }
        }
        else {
            style.insert("border", styleStroke(c.stroke));
        }
    }
    if c.inset != none {
        if type(c.inset) == dictionary {
            for (k, v) in mapBoxDic(c.inset).pairs() {
                style.insert("padding-"+k, styleRelative(v));
            }
        }
        else {
            style.insert("padding", styleRelative(c.inset));
        }
    }
    return style;
}

#let template(content) = [
    #show box: c => {
        let style = styleCommon(c);
        // TODO
        return html_elem("span", attrs: ("style": strStyle(style)), c.body)
    };
    #show block: c => {
        if c.body == auto {
            panic(c);
        }
        let style = styleCommon(c);
        // TODO
        return html_elem("div", attrs: ("style": strStyle(style)), c.body)
    };
    #show repeat: [...]; // TODO
    #show grid: c=>c.children.sum();// TODO
    #show grid.cell: c=>c.body;// TODO
    #content
]