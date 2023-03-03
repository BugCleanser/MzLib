# 贡献
若您想要为 MzLib 项目贡献自己的力量，以下是一些我们推荐的方式及相关的指南。

## [反馈问题](https://github.com/BugCleanser/MzLib/issues/new?assignees=mzmzpwq%2CYurinann&labels=Type%3A+Bug&template=bug_report.yml&title=%5BBug%5D+)
根据 Issue Template 详细的反馈一个符合标准的问题。

## [提交建议](https://github.com/BugCleanser/MzLib/issues/new?assignees=mzmzpwq%2CYurinann&labels=Type%3A+Bug&template=bug_report.yml&title=%5BBug%5D+)
根据 Issue Template 详细的提交一个符合标准的建议。

## 测试最新构建
在 [Github Actions](https://github.com/BugCleanser/MzLib/actions) 处测试我们的最新构建，并对可能出现的问题进行反馈。

## 代码贡献
我们欢迎您参与 MzLib 的开发！

在进行开发之前，希望您审慎阅读并理解以下准则，我们仅会通过符合规则的 Pull Request。

### 标识符的命名规则
- 如果是一个 `NMS` 包装类，请在类名前添加 `Nms`；
- 如果是一个 `org.bukkit.craftbukkit` 下的包装类，请在类名前添加 `Obc`；
- 如果被包装的类为 Paper 独有，且名称易与其他类混淆时，请在类名末尾添加 `Paper`；
- 如果目标是 Minecraft 特定版本区间独有的，请在类名后添加 `vA` 或 `vA_B` 或 `v_B`（左闭右开区间，分别表示版本 `大于等于A`、`大于等于A且小于B`、`小于B`）。
  - 例: 对于一个 Minecraft 版本号 `1.x.x`，去掉开头的 `1` 和所有的 `.`；`v_13` 表示 `1.12.x`，`v13` 表示从 `1.13` 开始的版本，`v13_193` 表示符合 `[1.13, 1.19.3)` 的版本。
- 如果方法或字段所在的类已经标示了作用域，则此方法或字段不需要再次标示。
  - 例: 在类 `ATestClassV13` 中标示一个支持 `[1.13, 1.19.3)` 的方法：`metV_193`。 
