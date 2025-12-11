#import "/lib/lib.typ": *;
#set raw(lang: "typst");
#set document(title: [Typst的简单教程]);
#show: template;
#title();

#let codeWithResult(code) = stack(code, block(eval(code.text, mode: "markup"), stroke: 1pt, inset: 3pt), dir: ltr);

Typst的简单教程

/ #[= 为什么使用Typst]:

    大部分其它项目的文档使用*Markdown*或特殊wiki语言编写 \
    Markdown是一种简易的标记语言，但其不支持*自定义函数和嵌套*等 \
    相对应的另一种常用语言是LaTeX，但其非常臃肿和复杂，并且*不支持html*

    我们可以使用js实现所需功能，但实际上Typst已经为我们做了一部分工作 \
    Typst是一种静态脚本语言，有着类似js的*灵活性*

    详见#link("https://typst.app/docs/")[Typst官方文档]

/ #[= 起步]:

    Typst兼容一部分的Markdown语法，以下是一些常见区别：

    - 标题使用`=`代替`#`

    - 无序列表使用`-`而不是`*`或`+`

    - 有序列表可用`+`代替`1.`等

    - 加粗使用`*`包围而不是`**`

    - 不支持Markdown的链接、图片、表格语法

    你的文档源码是`.typ`文件，在开头加入以下内容：

    ```
    #import "/lib/lib.typ": *;
    #set raw(lang: "java");
    #set document(title: [这里是标题]);
    #show: template;
    #title();
    ```

    以下是各语句的简单注释：
    ```
    #import "/lib/lib.typ": *; // 导入依赖，我们写了一些基础设施在这里
    #set raw(lang: "java"); // 设置代码块的默认语言，例如java
    #set document(title: [这里是标题]); // 设置文档标题
    #show: template; // 应用模板（样式），这个模板也在基础设施中
    #title(); // 在此处插入标题以显示
    ```

/ #[= 标记模式]:

    我们默认就在这个模式中，你可以认为你在写一个超大的字符串字面量（当然此处其实是html元素）

    在这里你可以插入一些表达式，以`#`开始，结束的`;`可以省略，例如：

    #codeWithResult(````
    文本直接写在这里
    - 支持标题等特殊语法
    - 支持无序列表

    + 支持
    + 有序
    + 列表
    ```text
    支持代码块
    ```
    `支持行内代码`

    #let x = "这是一个变量";
    1#x;2#x;3#x;
    ````);

/ #[= 代码模式]:

    当然你插入的表达式就在这个模式，遵循特定语法

    / #[== 常量字面值]:

        常量字面值的语法与C++、Java等语言类似：

        #codeWithResult(```
        #"字符串字面量";
        #114514; // 整数
        #3.1415926; // 浮点数
        #true; // 布尔值
        #none; // 空
        ```);

    / #[== 代码块]:

        代码块使用`{}`包围，代码块也是表达式，其中每一个语句的值则为代码块的值，多个则拼接，没有则为`none`（插入进标记模式中不显示）

        若语句以换行结束，`;`可以省略

        #codeWithResult(```
        #{ // 代码块开始
            "字符串表达式1";
            "表达式2";
        };
        ```);

    / #[== 变量]:

        使用`let`创建变量，使用`=`赋值

        #codeWithResult(```
        #{
            let x = 10;
            x = x + 5;
            x;
        };
        ```);

        声明语句可以直接插入标记模式中，则变量的作用域为所在内容的作用域

        #codeWithResult(```
        #let x = 10;
        #(x = x + 5); // 此处添加括号，否则只解析#x为代码
        #x;
        ```);

    / #[== 函数]:

        函数的声明方式与变量类似：

        #codeWithResult(```
        #let add(a, b) = a + b;
        #add(1, 2);
        ```);

/ #[= 数学模式]:

    插入一个数学公式，使用`$`包围，在此文档不常用