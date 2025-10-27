#import "template.typ": *

#let headings = state("headings", ())
#let headingPath = state("headingPath", ())

#let template(content) = [
    #show heading: x => {
        headings.update(s => {
            s.push(x);
            return s;
        });
        headingPath.update(s => {
            s = s.slice(0, x.level -1);
            if x.body.func()!=text {
                panic(x);
            }
            s.push(x.body.text);
            return s;
        });
        context html_elem("section", x, attrs: ("id": headingPath.get().join("-"))); // TODO
    }
    #content
]

#html_elem("style", ```css
    .catalogue {
        position: sticky;
        top: 18pt;
        padding-inline-start: 3pt;
        font-size: 12pt;
        margin-top: 48pt;
        color: #565565;
        font-weight: 600;
        align-self: start;
        box-sizing: border-box;
        overflow: hidden auto;
    }
    .catalogue a {
        color: inherit;
        text-decoration: none;
    }
    .catalogue a:hover {
        text-decoration: underline;
    }
    .catalogue ul {
        list-style: none;
        padding-inline-start: 12pt;
        display: block;
        margin-block-start: 0pt;
        margin-block-end: 0pt;
    }
    .catalogue > ul {
        padding-inline-start: 0px;
    }
```.text)
#context {
    let result = ();
    let path = ();
    for i in headings.final()+(heading(level: 1, "EOF"),) {
        path = path.slice(0, i.level -1);
        path.push(i.body.text);
        while result.len()>1 and result.first().at(0) < result.last().at(0) and result.last().at(0) >= i.level {
            let j = 0;
            while(result.at(j).at(0) < result.last().at(0)) {
                j += 1;
            }
            result.at(j - 1)+=result.slice(j);
            result = result.slice(0, j);
        }
        result.push((i.level, link("#"+path.join("-"), i.body)));
    };
    result.pop();
    let f(a) = {
        return list(..a.map(c=> if type(c)==content {c} else [
            #c.at(1);
            #f(c.slice(2));
        ]));
    };
    return html_elem("nav", attrs: (class: "catalogue"), f(result));
};
