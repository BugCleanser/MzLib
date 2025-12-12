#import "/lib/lib.typ": *;
#set raw(lang: "typst");
#let title = [卡片组件];
#show: template.with(title: title);


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

#cardTip[
    "提示"卡片

    `cardTip`
]

#cardAttention[
    "注意"卡片

    `cardAttention`
]
