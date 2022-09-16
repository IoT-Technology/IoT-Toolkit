<h2 align="center">
    <a href="https://github.com/IoT-Technology/IoT-Toolkit/wiki" target="blank_">
        <img height="250" alt="ToolKit" src="png/toolkit-logo.png"/>
    </a>
    <br>
    ToolKit: 用户友好的IoT协议客户端命令行工具🔧
</h2>

<div align="center">

[![GitHub issues](https://img.shields.io/github/issues/IoT-Technology/IoT-Toolkit)](https://github.com/IoT-Technology/IoT-Toolkit/issues)
[![GitHub stars](https://img.shields.io/github/stars/IoT-Technology/IoT-Toolkit)](https://github.com/IoT-Technology/IoT-Toolkit/stargazers)
![](https://img.shields.io/badge/language-java-orange.svg)
![Chocolatey-downloads](https://img.shields.io/chocolatey/dt/toolkit)
![GitHub release (latest by date)](https://img.shields.io/github/downloads/IoT-Technology/IoT-Toolkit/latest/total)

</div>

**Toolkit** 是一款支持多种物联网协议的客户端命令行工具。目前支持**CoAP**和**MQTT**协议，未来将支持更多的协议。现代化的设计，提供**自动补全**、**多语言配置**且用户交互良好且易于操作。可在**linux**、**unix**和**windows**等操作系统中运行。

<div align="center">

<img src="png/toolkit-show_zh.gif" alt="HTTPie in action" width="100%"/>

</div>

[📖 English Document](README.md) | 📖 中文文档

----------------------------------------

# 快速开始

## Linux & MacOS

安装[SDKMAN!](https://sdkman.io/), 请看[安装文档](https://sdkman.io/install)

```bash
sdk install toolkit
```

## Windows

安装[Chocolatey](), 请看[安装教程](https://chocolatey.org/install)

```bash
choco install toolkit
```

## 直接安装

| Platform | Toolkit                                                                                                                                    |
| -------- | ------------------------------------------------------------------------------------------------------------------------------------------ |
| Linux    | [toolkit-0.4.8-linux-x86_64.zip](https://github.com/IoT-Technology/IoT-Toolkit/releases/download/0.4.8/toolkit-0.4.8-linux-x86_64.zip)     |
| Unix     | [toolkit-0.4.8-osx-x86_64.zip](https://github.com/IoT-Technology/IoT-Toolkit/releases/download/0.4.8/toolkit-0.4.8-osx-x86_64.zip)         |
| Windows  | [toolkit-0.4.8-windows-x86_64.zip](https://github.com/IoT-Technology/IoT-Toolkit/releases/download/0.4.8/toolkit-0.4.8-windows-x86_64.zip) |

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

# 范例

设置语言为德语，支持

- zh=中文

- de=德语

- en=英语

- fr=法语

```bash
toolkit config -l=de 
```

## CoAP

请求由 [coap.me](https://coap.me/) 提供`/test` 路径下的资源

```bash
toolkit coap get coap://coap.me/test
```

更新由 [coap.me](https://coap.me/) 提供`/sink`路径下的资源

```bash
toolkit coap post coap://coap.me/sink -p='testing for post data' -f=text/plain
```

## MQTT

订阅 `test.mosquitto.org` 下 `hello`主题下的数据

```bash
toolkit mqtt sub -host=test.mosquitto.org -q=0 -t=hello
```

发布消息到`test.mosquitto.org` 的`hello` 主题里

```bash
toolkit mqtt pub -host=test.mosquitto.org -q=0 -t=hello -m='hi toolkit'
```

# # 贡献

可以通过解决现有的[Issues](https://github.com/IoT-Technology/IoT-Toolkit/issues)和[ Pull Requests](https://github.com/IoT-Technology/IoT-Toolkit/pulls)来踏入贡献`Toolkit`的第一步。如果您想请求一个特性或者报告bug，请使用提供的模版之一创建GitHub Issue。
