#import "/lib/lib.typ": *;
#set raw(lang: "typst");
#let title = [文档];
#show: template.with(title: title);


这里是文档的文档（

/ #[= Typst]:

   本文档使用Typst编写，而不是Markdown，详见#link("./typst")[Typst的简单教程]

/ #[= 贡献]:

   对于简单的修改，您可直接在#link("https://github.com/BugCleanser/MzLib/tree/main/docs")[Github]上修改然后提交PR

   对于较大修改，您可以克隆项目然后#link("./install")[本地部署和本地编辑]

/ #[= 文档结构]:

   像大部分Markdown编写的文档一样，您可以将所有内容平铺，然后利用标题和侧边目录表示层级结构：

   ```typst
   #import "/lib/lib.typ": *;
   #set raw(lang: "java");
   #let title = [这里是标题];
   #show: template.with(title: title);

   一些介绍

   = 一级标题

   一级内容

   == 二级标题

   二级内容

   = 又一个一级标题

   更多内容
   ```

   但现在我们更倾向于在内容本身就展示层级结构：

   ```typst
   #import "/lib/lib.typ": *;
   #set raw(lang: "java");
   #let title = [这里是标题];
   #show: template.with(title: title);

   一些介绍

   / #[= 一级标题]:

      一级内容

      / #[== 二级标题]:

         二级内容

   / #[= 又一个一级标题]:

      更多内容
   ```
   
   #cardInfo[
      使用`/ something:`语法来添加层级结构
      
      但其本身与标题语法不兼容，因此标题再使用`#[]`包裹
      
      你也可以用无序列表来表示层级结构：

      ```typst
      #import "/lib/lib.typ": *;
      #set raw(lang: "java");
      #let title = [这里是标题];
      #show: template.with(title: title);

      一些介绍

      - = 一级标题

         一级内容

         - == 二级标题

            二级内容

      - = 又一个一级标题

         更多内容
      ```
   ];
