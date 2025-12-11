#import "/lib/lib.typ": *;
#set raw(lang: "typst");
#set document(title: [卡片组件]);
#show: template;
#title();

使用函数创建卡片，例如
```
#cardInfo[
    卡片内容
]
```

#cardInfo[
    "信息"卡片

    `cardInfo`
]

#cardAttention[
    "注意"卡片

    `cardAttention`
]
