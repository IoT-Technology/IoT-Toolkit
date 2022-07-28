

# 支持多种IoT协议的客户端命令行工具

`Toolkit`是一款支持多种物联网协议的客户端命令行工具。目前支持CoAP和MQTT协议，未来将支持更多的协议。它是用java语言编写的，但不依赖JRE环境。

<p align="center">
    <img src="/png/toolkit.png" alt="IoT Toolkit">
</p>

[📖 English Document](README.md) | 📖 中文文档

----------------------------------------

<table style="text-align:center">
  <tr>
    <td>
     <h4>支持多种IoT协议</h4>
      该客户端命令行工具当前支持MQTT和CoAP协议，未来会支持更多协议。无须打开多个客户端软件，更方便
    </td>
    <td >
      <h4>Java语言编写，但不依赖JRE环境</h4>
      此项目使用Java语言编写，使用GraalVM Native Image技术使其不依赖JRE运行环境。
    </td>
    <td>
     <h4>多平台支持: mac、linux和windows</h4>
      提供linux、unix和windows版本的release包，方便在不同的操作系统中运行
    </td>
  </tr>
  <tr>
    <td>
      <h4>低内存占用且更快的启动速度</h4>
      静态编译运行通过AOT避免了JIT的CPU开销，通过轻量化SubstrateVM实现，
      静态编译至native image中，提供了较快的vm性能和启动速度。
    </td>
    <td>
      <h4>ANSI颜色和样式帮助</h4>
       toolkit无论在windows还是linux下，都有很好的色彩输出。
    </td>
    <td>
      <h4>现代化的设计，良好的交互</h4>
      提供自动补全，国际化和多语言支持，用户交互良好且易于操作。
    </td>
  </tr>
</table>



下面是`Toolkit`下载的方便连接

| 平台      | Toolkit                                                                                                                                       |
| ------- | --------------------------------------------------------------------------------------------------------------------------------------------- |
| Linux   | [toolkit-linux-0.0.1-release](https://github.com/IoT-Technology/IoT-Toolkit/releases/download/v0.0.1/toolkit-linux-0.0.1-release)             |
| Unix    | [toolkit-unix-0.0.1-release](https://github.com/IoT-Technology/IoT-Toolkit/releases/download/v0.0.1/toolkit-unix-0.0.1-release)               |
| Windows | [toolkit-windows-0.0.1-release.exe](https://github.com/IoT-Technology/IoT-Toolkit/releases/download/v0.0.1/toolkit-windows-0.0.1-release.exe) |


# CoAP

```bash
mushuwei@mushuweideMacBook-Pro-2 ~ % toolkit coap -h
CoAP Client Toolkit
Usage: toolkit coap [-hV] [COMMAND]
user-friendly CoAP protocol client toolkit

Options are:
  -h, --help      Show this help message and exit.
  -V, --version   Print version information and exit.
Commands:
  describe, desc   introduction and description of CoAP protocol
  media-types, mt  List supported MIME types
  discover, disc   list available resources
  get              Request data from CoAP Resource
  post             Create/Update data in CoAP Resource
  put              Update data in CoAP Resource
  delete, del      Delete CoAP Resource

Copyright (c) 2019-2022, IoT Technology
Developed by mushuwei
```

### Get Option

[![asciicast](https://asciinema.org/a/510626.svg)](https://asciinema.org/a/510626)

### Put Option

[![asciicast](https://asciinema.org/a/510629.svg)](https://asciinema.org/a/510629)

### Post Option

[![asciicast](https://asciinema.org/a/510631.svg)](https://asciinema.org/a/510631)

### Delete Option

[![asciicast](https://asciinema.org/a/510628.svg)](https://asciinema.org/a/510628)

# MQTT

```bash
mushuwei@mushuweideMacBook-Pro-2 ~ % toolkit mqtt -h
MQTT Client Toolkit
Usage: toolkit mqtt [-hV] [COMMAND]
user-friendly MQTT protocol client toolkit

Options are:
  -h, --help      Show this help message and exit.
  -V, --version   Print version information and exit.
Commands:
  describe, desc  introduction and description of MQTT protocol
  publish, pub    publish a message to the broker
  subscribe, sub  subscribe for updates from the broker

Copyright (c) 2019-2022, IoT Technology
Developed by mushuwei
```



### MQTT describe

```bash
mushuwei@mushuweideMacBook-Pro-2 ~ % toolkit mqtt desc 
MQTT (Message Queuing Telemetry Transport)

MQTT is an OASIS standard messaging protocol for the Internet of Things (IoT).
It is designed as an extremely lightweight publish/subscribe messaging transport
that is ideal for connecting remote devices with a small code footprint and
minimal network bandwidth. MQTT today is used in a wide variety of industries,
such as automotive, manufacturing, telecommunications, oil and gas, etc.

The Official address: https://mqtt.org/
The English MQTT 3.1.1 Specification: http://docs.oasis-open.org/mqtt/mqtt/v3.1.1/os/mqtt-v3.1.1-os.html
The Chinese MQTT 3.1.1 Specification: https://iot.mushuwei.cn/#/mqtt3/
The English MQTT 5 Specification: https://docs.oasis-open.org/mqtt/mqtt/v5.0/mqtt-v5.0.html
The Chinese MQTT 5 Specification: https://iot.mushuwei.cn/#/mqtt5/

```



### MQTT Publish and Subscribe

**Publish**

```bash
mushuwei@mushuweideMacBook-Pro-2 ~ % toolkit mqtt pub -h                                                             
Missing required parameter for option '--hostname' (<host>)
Usage: toolkit mqtt publish -h=<host> [-i=<clientId>] -m=<message> [-p=<port>]
                            [-P=<password>] [-q=<qos>] -t=<topic>
                            [-u=<username>]
publish a message to the broker

Options are:
* -h, --hostname=<host>     the broker host
  -p, --port=<port>         the broker port
                              Default: 1883
  -i, --client-id=<clientId>
                            the client id
  -u, --username=<username> the username
  -P, --password=<password> the password
  -q, --qos=<qos>           the QoS of the message, 0/1/2
                              Default: 0
* -t, --topic=<topic>       the message topic
* -m, --message=<message>   the message body

Copyright (c) 2019-2022, IoT Technology
Developed by mushuwei
mushuwei@mushuweideMacBook-Pro-2 ~ % toolkit mqtt pub -h=test.mosquitto.org -t=topic-test -m "Hi From IoT Technology"
mushuwei@mushuweideMacBook-Pro-2 ~ % 
```



**Subscribe**

```bash
mushuwei@mushuweideMacBook-Pro-2 ~ % toolkit mqtt sub -h
Missing required parameter for option '--hostname' (<host>)
Usage: toolkit mqtt subscribe -h=<host> [-i=<clientId>] [-k=<keepalive>]
                              [-p=<port>] [-P=<password>] [-q=<qos>] -t=<topic>
                              [-u=<username>]
subscribe for updates from the broker

Options are:
* -h, --hostname=<host>   the broker host
  -p, --port=<port>       the broker port
                            Default: 1883
  -i, --client-id=<clientId>
                          the client id
  -u, --username=<username>
                          the username
  -P, --password=<password>
                          the password
  -q, --qos=<qos>         the QoS of the message, 0/1/2
                            Default: 0
* -t, --topic=<topic>     the message topic
  -k, --keepalive=<keepalive>
                          send a ping every SEC seconds
                            Default: 60

Copyright (c) 2019-2022, IoT Technology
Developed by mushuwei
mushuwei@mushuweideMacBook-Pro-2 ~ % toolkit mqtt sub -h=test.mosquitto.org -t=topic-test
topic-test success subscribe message: Hi From IoT Technology


```
