#let template(content) = [
    #html.elem("link", attrs: (href: "https://cdn.jsdelivr.net/gh/PrismJS/prism-themes@master/themes/prism-ghcolors.min.css", rel: "stylesheet"))
    #html.elem("script", attrs: (src: "https://cdn.jsdelivr.net/npm/prismjs@1.29.0/components/prism-core.min.js"))
    #html.elem("script", attrs: (src: "https://cdn.jsdelivr.net/npm/prismjs@1.29.0/plugins/autoloader/prism-autoloader.min.js"))
    #html.elem("style", ```css
        .code-block {
            width: 100%;
            max-width: 800px;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            background-color: #2d2d2d;
        }

        .code-block .code-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 12px 20px;
            background-color: #1e1e1e;
            border-bottom: 1px solid #444;
        }

        .code-block .code-tag {
            display: flex;
            align-items: center;
            color: #f8f8f2;
            font-size: 14px;
            font-weight: 500;
        }

        .code-block .code-tag::before {
            content: "";
            display: inline-block;
            width: 12px;
            height: 12px;
            border-radius: 50%;
            background-color: #ff5f56;
            margin-right: 8px;
        }

        .code-block .code-copy {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }

        .code-block .code-copy:hover {
            background-color: #45a049;
        }

        .code-block .code-wrapper {
            overflow: auto;
            max-height: 500px;
        }

        .code-block pre[class*="language-"] {
            margin: 0;
        }
    ```.text)
    #html.elem("script", ```js
        document.addEventListener('DOMContentLoaded', function() {
            document.addEventListener('click', function(e) {
                const button = e.target;
                if (button.classList.contains('code-copy')) {
                    const codeContainer = button.closest('.code-block');
                    const codeElement = codeContainer.querySelector('code');
                    const codeText = codeElement.textContent;

                    navigator.clipboard.writeText(codeText).then(function() {
                        alert('代码已复制到剪贴板！');
                    }).catch(function(err) {
                        console.error('复制失败: ', err);
                        alert('复制失败，请手动选择文本复制', true);
                    });
                }
            });
        });
    ```.text)
    #show raw.where(block: false): r => html.elem("code", attrs: (class: "language-"+r.lang), r.text);
    #show raw.where(block: true): r => html.elem("div", attrs: (class: "code-block"))[
        #html.elem("div", attrs: (class: "code-header"))[
            #html.elem("div", attrs: (class: "code-tag"), r.lang)
            #html.elem("button", attrs: (class: "code-copy"))[复制代码]
        ]
        #html.elem("div", attrs: (class: "code-wrapper"), html.elem("pre", html.elem("code", attrs: (class: "language-"+r.lang), r.text)))
    ];
    #content
]