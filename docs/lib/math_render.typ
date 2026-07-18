#import "fp.typ": *;
#import "contents.typ": *;
#import "style.typ": *;

#importStyle("math.css", "lib/");
#context if is_html() {
    html.link(rel: "stylesheet",
        href:"https://fred-wang.github.io/MathFonts/LatinModern/mathfonts.css")
}

#let math_symbol = $a$.body.func();
#let math_align-point = $&$.body.func();

#let html_none = html_elem.with("none");
#let m_row = html_elem.with("mrow");
#let m_i = html_elem.with("mi");
#let m_o = html_elem.with("mo"/*, lspace: "0", rspace: "0"*/);
#let m_n = html_elem.with("mn");
#let m_text = html_elem.with("mtext");
#let m_under = html_elem.with("munder");
#let m_over = html_elem.with("mover");

/// 
///
/// - lines (array): 
/// -> content
#let math_align(lines) = {
    if lines.len() == 1 {
        let result = lines.first();
        if result.func() == sequence {
            result = result.children.split(math_align-point()).join(default: ()).join();
        }
        return result;
    }
    html.elem("mtable", lines.map(it => if it == none { () } else if it.func() == sequence { it.children } else { (it,) })
        .map(it => it.split(math_align-point()).map(i => html.elem("mtd", i.join())))
        .map(it => html.elem("mtr", it.join())).join())
}

#let render0(cont) = {
    let render_row(it) = m_row(render0(it));
    let func = cont.func();
    let fields = cont.fields();
    if func == math.equation {
        render0(cont.body)
    } else if func == sequence {
        cont.children.map(render0).join()
    } else if func == text {
        if cont.text.find(regex("\\d+(\\.\\d+)?")) == cont.text {
            m_n(cont.text)
        } else {
            m_text(cont.text)
        }
    } else if func == math_align-point or func == linebreak {
        cont
    } else if func == math_symbol {
        if cont.text.find(regex("[a-zA-Z\u{03b1}-ωΑ-Ω]")) == cont.text {
            m_i(cont.text)
        } else {
            m_o(cont.text)
        }
    } else if func == math.op {
        m_o(cont.text)
    } else if func == math.accent {
        m_over(render_row(cont.base), m_o(cont.accent, accent: "true"))
    } else if func == math.frac {
        html_elem("mfrac", render_row(cont.num), render_row(cont.denom))
    } else if func == math.primes {
        m_o("'" * cont.count) // TODO
    } else if func == math.root {
        if fields.at("index", default: math.root.index) == none {
            html.elem("msqrt", render_row(cont.radicand))
        } else {
            html.elem("mroot", render_row(cont.radicand), render_row(cont.index))
        }
    } else if func == math.lr {
        let children = cont.body.children;
        let match(f) = {
            let tar = f(none).body.children;
            children.first() == tar.first() and children.last() == tar.last()
        }
        m_row(
            ..if children.len() >= 2 and match(math.abs) {("class": "abs")},
            m_o(children.first(), fence: "true", form: "prefix"),
            children.slice(1, -1).map(render0).join(),
            m_o(children.last(), fence: "true", form: "postfix")
        )
    } else if func == math.mid {
        m_o(form: "infix", it.body)
    } else if func == math.cases { // TODO
        let reverse = fields.at("reverse", default: math.cases.reverse);
        m_row(class: "cases",
            m_o(if reverse {""} else {"{"}, fence: "true", form: "prefix"),
            math_align(cont.children.map(render0)),
            m_o(if reverse {"}"} else {""}, fence: "true", form: "postfix")
        )
    } else if func == math.underbrace {
        let result = m_under(render_row(cont.body), m_o(sym.brace.b)/*, accentunder: "true"*/);
        if cont.annotation != none {
            result = m_under(result, render_row(cont.annotation));
        }
        result
    } else if func == math.overbrace {
        let result = m_over(render_row(cont.body), m_o(sym.brace.t)/*, accentunder: "true"*/);
        if cont.annotation != none {
            result = m_over(result, render_row(cont.annotation));
        }
        result
    } else if func == math.stretch {
        m_o(cont.body, minsize: styleRelative(cont.size))
    } else if func == math.limits or func == math.scripts {
        render0(cont.body)
    } else if func == math.attach {
        if cont.base.func() != math.limits {
            let smart = ($sum$, $product$, $lim$).map(it => it.body);
            if cont.base.func() == math.scripts or not smart.contains(cont.base) {
                if "t" in fields and "tr" not in fields {
                    fields.insert("tr", fields.remove("t"));
                }
                if "b" in fields and "br" not in fields {
                    fields.insert("br", fields.remove("b"));
                }
            }
        }
        let result = render_row(cont.base);
        if "bl" in fields or "tl" in fields {
            let br = fields.at("br", default: none);
            if br != none {
                result += render_row(br);
            } else {
                result += html_none();
            }
            let tr = fields.at("tr", default: none);
            if tr != none {
                result += render_row(tr);
            } else {
                result += html_none();
            }
            result += html.elem("mprescripts");
            let bl = fields.at("bl", default: none);
            if bl != none {
                result += render_row(bl);
            } else {
                result += html_none();
            }
            let tl = fields.at("tl", default: none);
            if tl != none {
                result += render_row(tl);
            } else {
                result += html_none();
            }
            result = html.elem("mmultiscripts", result);
        } else if "br" in fields {
            result += render_row(fields.br);
            if "tr" in fields {
                result += render_row(fields.tr);
                result = html.elem("msubsup", result);
            } else {
                result = html.elem("msub", result);
            }
        } else if "tr" in fields {
            result += render_row(fields.tr);
            result = html.elem("msup", result);
        }
        if "b" in fields {
            result += render_row(fields.b);
            if "t" in fields {
                result += render_row(fields.t);
                result = html_elem("munderover", result);
            } else {
                result = m_under(result);
            }
        } else if "t" in fields {
            result += render_row(fields.t);
            result = m_over(result);
        }
        return result;
    } else {
        if cont == none or cont == [] {
            return none;
        } else if cont == [ ] {
            return m_text(class: "space", sym.space);
        }
        if dictionary(math).pairs().contains((repr(func), func)) {
            panic(cont);
        }
        return m_text(cont);
    }
}

#let render(equation) = {
    let result = render0(equation.body);
    if result != none and result.func() == sequence {
        result = math_align(result.children.split(linebreak()).map(it => it.join()));
    }
    return result;
}

#let template(con) = [
    #show math.equation: it => context if not is_html() { it } else {
        let result = style_text(style => {
            html_elem("math", render(it), style: strStyle(style), attrs: if it.block {("display": "block")} else {(:)})
        })
        if it.block {
            if it.numbering != none {
                result += html.div(class: "number", context {
                    numbering(it.numbering, ..counter(math.equation).at(it.location()))
                });
            }
            result = html.div(class: "equation", result);
        } else {
            result = box(result);
        }
        return result;
    }
    #con
]
