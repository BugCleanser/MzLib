#import "meta.typ";

#let isHtml = "html" in dictionary(std);
#let sequence = [].func();

#let contentToString(con) = {
    if type(con) == str {
        return con;
    }
    if con.has("text") {
        return con.at("text");
    }
    else if con.has("body") {
        return contentToString(con.at("body"));
    }
    else if con.has("children") {
        return con.at("children").map(contentToString).join();
    }
    else {
        return repr(con);
    };
}

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
