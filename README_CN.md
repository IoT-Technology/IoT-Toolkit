<h2 align="center">
    <a href="https://github.com/IoT-Technology/IoT-Toolkit/wiki" target="blank_">
        <img height="250" alt="ToolKit" src="png/toolkit-logo.png"/>
    </a>
    <br>
    ToolKit: human-friendly IoT Protocol client CLI🔧
</h2>

<div align="center">

[![GitHub issues](https://img.shields.io/github/issues/IoT-Technology/IoT-Toolkit)](https://github.com/IoT-Technology/IoT-Toolkit/issues)
[![GitHub stars](https://img.shields.io/github/stars/IoT-Technology/IoT-Toolkit)](https://github.com/IoT-Technology/IoT-Toolkit/stargazers)
![](https://img.shields.io/badge/language-java-orange.svg)
![Chocolatey-downloads](https://img.shields.io/chocolatey/dt/toolkit)
![GitHub release (latest by date)](https://img.shields.io/github/downloads/IoT-Technology/IoT-Toolkit/latest/total)

</div>

Toolkit is a IoT protocol client CLI for IoT developer and learners.
CoAP and MQTT protocol are currently supported. It supports colors, autocompletion,
internationalization(chinese, english and german…) and multi-platforms

<div align="center">

<img src="png/toolkit-show_en.gif" alt="HTTPie in action" width="100%"/>

</div>

[📖 中文文档](README.md) | 📖 English Document

----------------------------------------

# Getting started

## Linux & MacOS

to install [SDKMAN!](https://sdkman.io/), see [its installation](https://sdkman.io/install).

```bash
sdk install toolkit
```

## Windows

To install [Chocolatey](https://chocolatey.org/), see [its installation](https://chocolatey.org/install).

```bash
choco install toolkit
```

## Direct Download

| Platform | Toolkit                                                                                                                                    |
| -------- |--------------------------------------------------------------------------------------------------------------------------------------------|
| Linux    | [toolkit-0.5.0-linux-x86_64.zip](https://github.com/IoT-Technology/IoT-Toolkit/releases/download/0.5.0/toolkit-0.5.0-linux-x86_64.zip)     |
| Unix     | [toolkit-0.5.0-osx-x86_64.zip](https://github.com/IoT-Technology/IoT-Toolkit/releases/download/0.5.0/toolkit-0.5.0-osx-x86_64.zip)         |
| Windows  | [toolkit-0.5.0-windows-x86_64.zip](https://github.com/IoT-Technology/IoT-Toolkit/releases/download/0.5.0/toolkit-0.5.0-windows-x86_64.zip) |

# Features

<table style="text-align:center">
  <tr>
    <td width='33%'>
     <h4>&#127932; Integrate multi IoT protocols</h4>
      <p>Support for MQTT、CoAP, more protocols in the future.</p>
    </td>
    <td width='33%'>
     <h4>&#128064; Multiple operating support</h4>
      Runs on linux or unix or windows.
    </td>
    <td width='33%'>
      <h4>&#127759; International Service</h4>
      Multi-language configuration, chinese、english and german support.
    </td>
  </tr>
  <tr>
    <td width='33%'>
      <h4>&#128147; ANSI Colors and Styles</h4>
       Using colors in your command’s output does not just look good:
       by contrasting important elements like option names from the rest of the message,
       it reduces the cognitive load on the user.
    </td>
    <td width='33%'>
      <h4>&#128101; Modern design</h4>
      Provides Auto-Completion, user interaction is good 
      and easy to operate.
    </td>
    <td width='33%'>
      <h4>&#128175; Low memory usage and faster startup speed </h4>
      Static compilation runs avoid the CPU overhead of the JIT.
      using Native Image provides faster VM performance and startup speed.
      No need to rely on any running environment.
    </td>
  </tr>
</table>

# Examples

Set the language to German, Support 

- zh=chinese

- de=german

- en=english

- fr=french

```bash
toolkit config -l=de 
```

## CoAP

**Get**  the resource at `/test` path from data provided by [coap.me](https://coap.me/)

```bash
toolkit coap get coap://coap.me/test
```

**Update**  the data in the resource at `/sink` path  provided by [coap.me](https://coap.me/)

```bash
toolkit coap post coap://coap.me/sink -p='testing for post data' -f=text/plain
```

## MQTT

**Subscribe** to updates with the `hello` topic from `test.mosquitto.org`

```bash
toolkit mqtt sub
```

**Publish** a message to the `hello` topic of `test.mosquitto.org`

```bash
toolkit mqtt pub 
```


# Contributing

Have a look through existing [Issues](https://github.com/IoT-Technology/IoT-Toolkit/issues) and [Pull Requests](https://github.com/IoT-Technology/IoT-Toolkit/pulls) that you could help with.
If you'd like to request a feature or report a bug, please [create a GitHub Issue](https://github.com/IoT-Technology/IoT-Toolkit/issues) using one of the templates provided.
