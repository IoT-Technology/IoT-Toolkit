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
