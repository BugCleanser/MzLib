#import "meta.typ";

#let sequence = [].func();
#let space = [ ].func();
#let styled = {set page(fill: auto);}.func();
#let text_def = ("size": 12pt, "fill": black);
#let std_symbol = [~].func();

#let is_html() = target() == "html";

#let contentToString(con) = {
    if type(con) == str {
        con
    } else if con.has("text") {
        con.at("text")
    } else if con.has("body") {
        contentToString(con.at("body"))
    } else if con.has("children") {
        con.at("children").map(contentToString).join()
    } else {
        repr(con)
    }
}

#let html_elem(tag, attrs: (:), ..args) = context {
    if is_html() {
        return html.elem(tag, args.pos().join(), attrs: attrs + args.named());
    }
    if ("script", "style", "template").contains(tag) {
        return none;
    }
    return args.pos().join();
}
#let html_inline-flex = html_elem.with("span", class: "inline-flex"); // TODO
#let html_frame(body) = context {
    if is_html() {
        html.frame(body)
    } else {
        body
    }
}

#let importStyle(name, path) = {
    if meta.environment=="development" {
        return html_elem("style", read(name));
    }
    else {
        return html_elem("link", attrs: (href: meta.root+path+name, rel: "stylesheet"))[];
    }
}
#let importScript(name, path) = {
    if meta.environment == "development" {
        return html_elem("script", read(name));
    }
    else {
        return html_elem("script", attrs: (src: meta.root+path+name))[]
    }
}

#let hr = html_elem("hr")[];

#html_elem("template", id: "card")[
    #html_elem("div", part: "icon")
    #html_elem("div", part: "content")[
        #html_elem("slot")[content]
    ]
]
#importScript("card.js", "lib/");
#importStyle("card.css", "lib/");
#import "@preview/note-me:0.6.0";
#let card(kind, con, alt: none) = context if is_html() { html.elem("ui-card", attrs: ("type": kind), con) } else if alt != none { alt(con) } else [/ [#kind]: #con];
#let cardInfo = card.with("info", alt: note-me.note);
#let cardTip = card.with("tip", alt: note-me.important);
#let cardAttention = card.with("attention");

#html_elem("template", id: "expand-panel")[
    #html_elem("div", part: "header")[
        #html_elem("slot", name: "trigger")[trigger]
        #html_elem("div", part: "arrow")[▼]
    ]
    #html_elem("div", part: "content")[
        #html_elem("div", part: "inner")[
            #html_elem("slot", name: "content")[content]
        ]
    ]
]
#importScript("expand-panel.js", "lib/");
#importStyle("expand-panel.css", "lib/");
#let expand-panel(trigger, con, open: false) = context if is_html() { html.elem("expand-panel", html.div(trigger, slot: "trigger") + html.div(con, slot: "content"), attrs: if open {("open":"")} else {(:)}) } else [/ #trigger: \ #con];
