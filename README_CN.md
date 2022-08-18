# 支持多种IoT协议的客户端命令行工具

`Toolkit`是一款支持多种物联网协议的客户端命令行工具。目前支持**CoAP**和**MQTT**协议，未来将支持更多的协议。现代化的设计，提供**自动补全**、**多语言配置**且用户交互良好且易于操作。可在**linux**、**unix**和**windows**等操作系统中运行。

<p align="center">
    <img src="/png/toolkit.png" alt="IoT Toolkit">
</p>

[📖 English Document](README.md) | 📖 中文文档

----------------------------------------

# 快速开始

https://github.com/IoT-Technology/IoT-Toolkit/wiki

# 特性

<table style="text-align:center">
  <tr>
    <td width='33%'>
     <h4>&#127932; 集成多种IoT协议</h4>
      <p>支持MQTT、CoAP协议，未来会支持更多协议，
        更详细的协议打印信息；</p>
    </td>
    <td width='33%'>
     <h4>&#128064; 多操作系统支持</h4>
      可在linux、unix和windows等操作系统中运行；
    </td>
    <td width='33%'>
      <h4>&#127759; 国际化服务</h4>
      多语言配置，中文、英文和德语支持；
    </td>
  </tr>
  <tr>
    <td width='33%'>
      <h4>&#128147; ANSI颜色和样式帮助</h4>
       颜色使命令输出看起来好看，将重要元素与其余部分进行对比，
       减少用户认知负荷；
    </td>
    <td width='33%'>
      <h4>&#128101; 现代化的设计</h4>
      提供自动补全，用户交互良好且易于操作。
    </td>
    <td width='33%'>
      <h4>&#128175; 低内存占用和更快的启动速度</h4>
      静态编译运行避免JIT的CPU开销。使用native image，提供了较快的vm性能和启动速度，
      无需依赖任何运行环境
    </td>
  </tr>
</table>

# 下载地址

下面是`Toolkit`下载的链接地址

| 平台      | Toolkit                                                                                                                                    |
| ------- | ------------------------------------------------------------------------------------------------------------------------------------------ |
| Linux   | [toolkit-0.4.5-linux-x86_64.zip](https://github.com/IoT-Technology/IoT-Toolkit/releases/download/0.4.5/toolkit-0.4.5-linux-x86_64.zip)     |
| Unix    | [toolkit-0.4.5-osx-x86_64.zip](https://github.com/IoT-Technology/IoT-Toolkit/releases/download/0.4.5/toolkit-0.4.5-osx-x86_64.zip)         |
| Windows | [toolkit-0.4.5-windows-x86_64.zip](https://github.com/IoT-Technology/IoT-Toolkit/releases/download/0.4.5/toolkit-0.4.5-windows-x86_64.zip) |

在`windows`下，可以使用**Chocolatey**进行安装

```bash
choco install toolkit
```

# 贡献

可以通过解决现有的[Issues](https://github.com/IoT-Technology/IoT-Toolkit/issues)和[ Pull Requests](https://github.com/IoT-Technology/IoT-Toolkit/pulls)来踏入贡献`Toolkit`的第一步。如果您想请求一个特性或者报告bug，请使用提供的模版之一创建GitHub Issue。
