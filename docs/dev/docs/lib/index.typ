#import "/lib/lib.typ": *;
#set document(title: [lib]);
#show: template;
#title();

`docs/lib`存放了文档的基础设施，包括模板、组件声明、样式、脚本等

- `template.typ`

    文档的模板

- `style.typ`

    Typst基本组件的样式处理

- `code_block.typ`

    代码块的样式处理

- `content.typ`

    一些组件

    - #link("./card")[card]

        卡片组件