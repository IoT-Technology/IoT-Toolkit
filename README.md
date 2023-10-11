<h2 align="center">
    <a href="https://github.com/IoT-Technology/IoT-Toolkit/wiki" target="blank_">
        <img height="250" alt="ToolKit" src="https://user-images.githubusercontent.com/23117382/237898570-74c2ddc8-46cd-49d5-9c31-fa0018e0fb5d.png"/>
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

**Toolkit** is a client-side command line tool that supports multiple iot protocols.

CoAP, MQTT and NB-IoT(Lightweight M2M) protocols are currently supported, and more protocols will be supported in the future.

Toolkit provides a modern design, automatic completion, multi-language configuration, good user interaction, and easy operation.

It runs on Linux, Unix, Windows and Raspberry Pi, does not depend on any runtime.

---

[📖 中文文档](README_CN.md) | 📖 English Document

----------------------------------------

## Main features

<table style="text-align:center">
  <tr>
    <td width='33%'>
     <h4>&#127932; Integrate multi IoT protocols</h4>
      <p>Support MQTT, CoAP and NB-IoT protocols, more protocols will be supported in the future, more detailed protocol printing information;</p>
    </td>
    <td width='33%'>
     <h4>&#128064; Multiple operating support</h4>
      Runs on Linux, Unix, Windows and Raspberry Pi
    </td>
    <td width='33%'>
      <h4>&#127759; International</h4>
      Multi-language configuration, Chinese、English、French and German support;
    </td>
  </tr>
  <tr>
    <td width='33%'>
      <h4>&#128147; ANSI Colors and Styles Help</h4>
       Colors make command output look good, contrast important elements with the rest, and reduce user cognitive load;
    </td>
    <td width='33%'>
      <h4>&#128101; Modern Design</h4>
      Provides Auto-Completion, user interaction is good and easy to operate;
    </td>
    <td width='33%'>
      <h4>&#128175; Low memory usage and faster startup speed</h4>
      Static compilation runs avoid the CPU overhead of the JIT. using Native Image provides faster VM performance and startup speed. No need to rely on any running environment.
    </td>
  </tr>
</table>

## Getting started

### MacOS

```bash
brew install iot-technology/tap/toolkit
```

### Linux

to install [SDKMAN!](https://sdkman.io/), see [its installation](https://sdkman.io/install).

```bash
sdk install toolkit
```

### Windows

To install [Chocolatey](https://chocolatey.org/), see [its installation](https://chocolatey.org/install).

```bash
choco install toolkit
```

### Direct Download

| Platform     | Toolkit                                                                                            |
| ------------ | -------------------------------------------------------------------------------------------------- |
| Linux-x86_64 | [toolkit-latest-linux-x86_64.zip](https://github.com/IoT-Technology/IoT-Toolkit/releases/latest)   |
| Linux-arm64  | [toolkit-latest-linux-aarch_64.zip](https://github.com/IoT-Technology/IoT-Toolkit/releases/latest) |
| Unix         | [toolkit-latest-osx-x86_64.zip](https://github.com/IoT-Technology/IoT-Toolkit/releases/latest)     |
| Windows      | [toolkit-latest-windows-x86_64.zip](https://github.com/IoT-Technology/IoT-Toolkit/releases/latest) |



## Example

### MQTT protocol client toolkit
more details see [mqtt client toolkit user guide](https://github.com/IoT-Technology/IoT-Toolkit/wiki/mqtt_shell)

[![asciicast](https://asciinema.org/a/612813.svg)](https://asciinema.org/a/612813)

### CoAP Protocol client toolkit
more details see [](https://github.com/IoT-Technology/IoT-Toolkit/wiki/CoAP)

[![asciicast](https://asciinema.org/a/585753.svg)](https://asciinema.org/a/585753)

### LwM2M Protocol Client toolkit
more details see [](https://github.com/IoT-Technology/IoT-Toolkit/wiki/CoAP)

## Contributing

Have a look through existing [Issues](https://github.com/IoT-Technology/IoT-Toolkit/issues) and [Pull Requests](https://github.com/IoT-Technology/IoT-Toolkit/pulls) that you could help with.
If you'd like to request a feature or report a bug, please [create a GitHub Issue](https://github.com/IoT-Technology/IoT-Toolkit/issues) using one of the templates provided.

## Contributors

This project exists thanks to all the people who contribute. [[Contributors](https://github.com/IoT-Technology/IoT-Toolkit/graphs/contributors)].

## License

IoT Toolkit is under the Apache 2.0 license. See the [LICENSE](https://github.com/IoT-Technology/IoT-Toolkit/blob/main/LICENSE.txt) file for details.

## Who is using

These are only part of the companies using IoT Toolkit, for reference only. If you are using IoT Toolkit, please [add your company
here](https://github.com/IoT-Technology/IoT-Toolkit/issues/4) to tell us your scenario to make IoT Toolkit better.

<div style='vertical-align: middle'>
    <img alt='Tuya Smart' height='50'  src='https://user-images.githubusercontent.com/23117382/254794501-889db181-ad45-4b68-979c-88d1d021c6ef.png'  /img>
    <img alt='China Gas' height='50'  src='https://user-images.githubusercontent.com/23117382/254795187-dde1f572-443e-4745-a34e-e04d4c1840f3.png'  /img>
</div>