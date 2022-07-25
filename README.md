# A handy client toolkit CLI for IoT developers and learners

`Toolkit` is a client command line tool that supports multiple IoT protocols. **CoAP** and  **MQTT** protocols are currently supported. more protocols will be supported in the future. it is written in the java language, but does not rely on the JRE environment.

![](/Users/mushuwei/IdeaProjects/iot-toolkit/png/toolkit.png)

## Future

- multi-platform support: mac、linux and windows;

- very small and takes up little memory;

- command's output look good in both on windows and linux

- written in the java language, but does not rely on the JRE environment

- integration of multiple protocols eliminates the neet to open multiple client software

## CoAP

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

## MQTT

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
