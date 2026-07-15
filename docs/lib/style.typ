#import "content.typ": *;

#let styleColor(value) = {
    if value == auto {
        value = color.black;
    };
    return value.to-hex();
};

#let styleLength(value) = {
    if value == auto {
        value = 1pt;
    };
    return repr(value);
};

#let styleRatio(value) = {
    if value == auto {
        value = 100%;
    };
    return repr(value);
};

#let styleRelative(value) = {
    if value == 0 {
        return "0";
    };
    if type(value) == fraction {
        return repr(value);
    };
    value += 0%+0pt;
    if value == 0%+0pt {
        return "0";
    };
    if value.ratio == 0% {
        return styleLength(value.length);
    };
    if value.length == 0pt {
        return styleRatio(value.ratio);
    };
    return "calc(" + repr(value) + ")";
};

#let styleStroke(value) = {
    if value == auto {
        value = 1pt + black;
    }
    value = stroke(value);
    return styleLength(value.thickness)+" solid "+styleColor(value.paint);
};

#let styleAlign(value) = {
    if value == auto {
        value = start + top;
    }
    let result = (:);
    if(value.x == center) {
        result.insert("text-align", "center");
    } else if(value.x == start) {
        result.insert("text-align", "start");
    } else if(value.x == end) {
        result.insert("text-align", "end");
    } else if(value.x == left) {
        result.insert("text-align", "left");
    } else if(value.x == right) {
        result.insert("text-align", "right");
    }
    if(value.y == horizon) {
        result.insert("vertical-align", "middle");
    } else if(value.y == top) {
        result.insert("vertical-align", "top");
    } else if(value.y == bottom) {
        result.insert("vertical-align", "bottom");
    }
    return result;
};

#let strStyle(value) = {
    return value.pairs().map(((k, v))=>k+": "+v+";\n").sum(default: "");
};

#let mapBoxDic(dic) = {
    if dic.keys().contains("x") {
        if not dic.keys().contains("top") {
            dic.insert("top", dic.at("x"));
        };
        if not dic.keys().contains("bottom") {
            dic.insert("bottom", dic.at("x"));
        };
    };
    if dic.keys().contains("y") {
        if not dic.keys().contains("left") {
            dic.insert("left", dic.at("y"));
        };
        if not dic.keys().contains("right") {
            dic.insert("right", dic.at("y"));
        };
    };
    if dic.keys().contains("rest") {
        if not dic.keys().contains("top") {
            dic.insert("top", dic.at("rest"));
        };
        if not dic.keys().contains("bottom") {
            dic.insert("bottom", dic.at("rest"));
        };
        if not dic.keys().contains("left") {
            dic.insert("left", dic.at("rest"));
        };
        if not dic.keys().contains("right") {
            dic.insert("right", dic.at("rest"));
        };
    };
    dic.remove("x", default: none);
    dic.remove("y", default: none);
    dic.remove("rest", default: none);
    return dic;
};

#let styleCommon(c) = {
    let style = (:);
    if c.width != auto {
        style.insert("width", styleRelative(c.width));
    };
    if c.height != auto {
        style.insert("height", styleRelative(c.height));
    };
    if c.stroke != none {
        if type(c.stroke) == dictionary {
            for (k, v) in mapBoxDic(c.stroke).pairs() {
                style.insert("border-"+k, styleStroke(v));
            };
        } else {
            style.insert("border", styleStroke(c.stroke));
        }
    };
    if c.inset != none {
        if type(c.inset) == dictionary {
            for (k, v) in mapBoxDic(c.inset).pairs() {
                style.insert("padding-"+k, styleRelative(v));
            };
        } else {
            style.insert("padding", styleRelative(c.inset));
        };
    };
    return style;
};

#let stackEx(..children, dir: ttb, spacing: none) = {
    children = children.pos();
    let style = (:);
    style.insert("flex-direction",
        if dir == ltr {
            "row";
        } else if dir == rtl {
            "row-reverse";
        } else if dir == ttb {
            "column";
        } else if dir == btt {
            "column-reverse";
        } else {
            panic();
        }
    );
    if spacing!=0 {
        style.insert("gap", styleRelative(spacing));
    };
    return html_elem("div", attrs: ("class": "container stack", "style": strStyle(style)), children.join());
}

#let templateStyle(con) = if not isHtml {
    con;
} else [
    #show box: c => {
        let style = styleCommon(c);
        // TODO
        return html_elem("span", attrs: ("class": "container box", "style": strStyle(style)), c.body)
    };
    #show block: c => {
        if c.body == auto {
            panic(c);
        }
        let style = styleCommon(c);
        // TODO
        return html_elem("div", attrs: ("class": "container block", "style": strStyle(style)), c.body)
    };
    #show table: it => {
        let result = ();
        let width = it.columns.len();
        for i in it.children {
            let x = i.fields().at("x", default: table.cell.x);
            let y = i.fields().at("y", default: table.cell.y);
            let rowspan = i.fields().at("rowspan", default: table.cell.rowspan);
            let colspan = i.fields().at("colspan", default: table.cell.colspan);
            if x != auto and y != auto {
                while y + rowspan > result.len() {
                    result.push((none,) * width);
                }
                for l in range(0, rowspan) {
                    for m in range(0, colspan) {
                        if result.at(y + l).at(x + m) != none {
                            panic(it);
                        }
                        result.at(y + l).at(x + m) = ();
                    }
                }
                result.at(y).at(x) = i.body;
            } else {
                let flag = false;
                let j = if y == auto { 0 } else { y };
                while(not flag) {
                    if j == result.len() {
                        result.push((none,) * width);
                    }
                    for k in if x == auto { range(0, width) } else { (x,) } {
                        if (() => {
                            for l in range(0, rowspan) {
                                if(j + l == result.len()) {
                                    result.push((none,) * width);
                                }
                                for m in range(0, colspan) {
                                    if result.at(j + l).at(k + m) != none {
                                        return false;
                                    }
                                }
                            }
                            return true;
                        })() {
                            for l in range(0, rowspan) {
                                for m in range(0, colspan) {
                                    result.at(j + l).at(k + m) = ();
                                }
                            }
                            result.at(j).at(k) = i.body;
                            flag = true;
                            break;
                        }
                    }
                    if y == auto {
                        j = j + 1;
                    }
                }
                if not flag {
                    panic(i);
                }
            }
        }
        let get_stroke;
        if type(it.stroke) == function {
            get_stroke = it.stroke;
        } else if type(it.stroke) == array {
            panic(it.stroke); // TODO
        } else {
            get_stroke = (x, y) => it.stroke;
        }
        let get_align;
        if type(it.align) == function {
            get_align = it.align;
        } else if type(it.align) == array {
            panic(it.align); // TODO
        } else {
            get_align = (x, y) => it.align;
        }
        html.table(result.map(i => i.filter(j => j != ())).filter(i => i != ()).enumerate().map(((y, i)) =>
            html.tr(i.enumerate().map(((x, j)) => html.td(j, style: strStyle((border: styleStroke(get_stroke(x, y)), ..styleAlign(get_align(x, y)))))).join())
        ).join())
    }
    #show stack: c => stackEx(dir: c.dir, spacing: c.spacing, ..c.children);
    #show repeat: [...]; // TODO
    #show grid: c=>c.children.sum(); // TODO
    #show grid.cell: c=>c.body; // TODO
    #show math.equation.where(block: false): it => box(html.frame(it))
    #show math.equation.where(block: true): html.frame
    #importStyle("style.css", "lib/");
    #con;
];