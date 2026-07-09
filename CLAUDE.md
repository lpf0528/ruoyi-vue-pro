# ruoyi-vue-pro 项目指南

本项目 AI 协作规则已迁移至 Cursor 自动加载的规则文件：

```
.cursor/rules/
├── project-overview.mdc    # 始终生效：项目概览、技术栈、AI 总则
├── java-conventions.mdc      # Java 文件：开发规范、注释、测试
└── zc-curtain-module.mdc     # 窗帘模块：ZC 领域模型与业务流程
```

Cursor 会在对话中按 `alwaysApply` 与 `globs` 自动注入对应规则，无需手动引用。

如需修改规范，请直接编辑上述 `.mdc` 文件（YAML frontmatter + Markdown 正文）。
