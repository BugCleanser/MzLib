#import "meta.typ";
#import "style.typ": *;

#let isHtml = "html" in dictionary(std);
#let sequence = [].func();

#let html_elem(tag, attrs: (:), body) = {
    if isHtml {
        return html.elem(tag, body, attrs: attrs);
    }
    return body;
};
#let html_frame(body) = {
    if isHtml {
        return html.frame(body);
    }
    return body;
};

#let importStyle(name, path) = {
    if meta.environment=="development" {
        return html_elem("style", read(name));
    }
    else {
        return html_elem("link", attrs: (href: meta.root+path+name, rel: "stylesheet"))[];
    };
};
#let importScript(name, path) = {
    if meta.environment=="development" {
        return html_elem("script", read(name));
    }
    else {
        return html_elem("script", attrs: (src: meta.root+path+name))[]
    };
};

#let title() = html_elem("h1", context document.title)

#let hr = html_elem("hr")[]

#let template(content) = [
    #importStyle("template.css", "lib/");
    #import "style.typ"
    #show: style.template
    #style
    #import "code_block.typ"
    #show: code_block.template
    #html_elem("title", context document.title)
    #import "sidebar.typ";
    #sidebar;
    #import "catalogue.typ";
    #html_elem("main")[
        #show: catalogue.template;
        #content;
    ];
    #catalogue
]
