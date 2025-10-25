#import "meta.typ": *

#let languageIcons = (
    "html": "html5",
    "css": "css3-alt",
    "javascript": "js",
    "c++": "cpp",
    "C#": "csharp",
    "go": "golang",
    "ts": "typescript",
    "vue": "vuejs"
)

#let template(content) = [
    #html_elem("link", attrs: (href: "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css", rel: "stylesheet"))[]
    #html_elem("link", attrs: (href: "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/styles/atom-one-dark.min.css", rel: "stylesheet"))[]
    #html_elem("script", attrs: (src: "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/highlight.min.js"))[]
    #html_elem("style", ```css
        .code-block * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        .code-block {
            border-radius: 8px;
            overflow: hidden;
            transition: all 0.3s ease;
        }

        .code-block:hover {
            box-shadow: 0 8px 30px rgba(0, 0, 0, 0.12);
        }

        .code-block .code-header {
            background-color: #282c34;
            color: #abb2bf;
            padding: 0.75rem 1rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 1px solid #3e4451;
        }

        .code-block .code-content {
            background-color: #282c34;
            padding: 0;
            margin: 0;
        }

        .code-block .code-content pre {
            margin: 0;
            padding: 1rem;
            overflow-x: auto;
            font-family: 'Consolas', 'Monaco', 'Courier New', monospace;
            font-size: 14px;
            line-height: 1.5;
        }

        .code-block .copy-btn {
            background-color: #3e4451;
            color: #abb2bf;
            border: none;
            border-radius: 4px;
            padding: 0.25rem 0.75rem;
            cursor: pointer;
            font-size: 12px;
            transition: all 0.2s ease;
            display: flex;
            align-items: center;
            gap: 0.25rem;
        }

        .code-block .copy-btn:hover {
            background-color: #4b5263;
            color: #ffffff;
        }

        .code-block .copy-btn.copied {
            background-color: #98c379;
            color: #ffffff;
        }

        .code-block .language-badge {
            background-color: #3e4451;
            color: #abb2bf;
            padding: 0.25rem 0.5rem;
            border-radius: 4px;
            font-size: 12px;
            font-weight: 500;
        }

        code {
            font-family: 'Fira Code', 'Consolas', 'Monaco', 'PingFang SC', 'Microsoft YaHei', 'Courier New', monospace;
        }

        :not(pre) > code {
          background: #f5f5f5;
          padding: 0.2em 0.4em;
          border-radius: 4px;
        }
    ```.text)
    #html_elem("script", ```js
        document.addEventListener('DOMContentLoaded', (event) => {
            hljs.configure({
                cssSelector: 'code'
            });
            hljs.highlightAll();
            document.querySelectorAll('.code-block .copy-btn').forEach(button => {
                button.addEventListener('click', () => {
                    const codeContainer = button.closest('.code-block');
                    const codeElement = codeContainer.querySelector('code');
                    const codeText = codeElement.textContent;
                    navigator.clipboard.writeText(codeText).then(() => {
                        const originalText = button.innerHTML;
                        button.replaceChildren(Object.assign(document.createElement('i'), { className: 'fas fa-check' }), ' 已复制');
                        button.classList.add('copied');
                        setTimeout(() => {
                            button.innerHTML = originalText;
                            button.classList.remove('copied');
                        }, 2000);
                    }).catch(err => {
                        console.error('复制失败:', err);
                    });
                });
            });
        });
    ```.text)
    #show raw.where(block: false): r => html_elem("code", attrs: (class: "language-"+r.lang), r.text);
    #show raw.where(block: true): r => html_elem("div", attrs: (class: "code-block"))[
        #html_elem("div", attrs: (class: "code-header"))[
            #html_elem("span", attrs: (class: "language-badge"))[
                #html_elem("i", attrs: (class: "fab mr-1 fa-"+languageIcons.at(r.lang, default: r.lang)))[]
                #r.lang
            ]
            #html_elem("button", attrs: (class: "copy-btn"))[
                #html_elem("i", attrs: (class: "far fa-copy"))[]
                复制代码
            ]
        ]
        #html_elem("div", attrs: (class: "code-content"), html.elem("pre", html.elem("code", attrs: (class: "language-"+r.lang), r.text)))
    ];
    #content
]