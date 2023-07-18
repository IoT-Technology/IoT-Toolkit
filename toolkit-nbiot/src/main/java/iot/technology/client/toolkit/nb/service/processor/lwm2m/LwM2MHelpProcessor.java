/*
 * Copyright © 2019-2023 The Toolkit Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package iot.technology.client.toolkit.nb.service.processor.lwm2m;

import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkAbstractProcessor;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;

import java.util.List;
import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class LwM2MHelpProcessor extends TkAbstractProcessor implements TkProcessor {

    ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

    @Override
    public boolean supports(ProcessContext context) {
        return context.getData().startsWith("help");
    }

    @Override
    public void handle(ProcessContext context) {
        List<String> arguArgs = List.of(context.getData().split(" "));
        if (arguArgs.size() > 2) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format(ColorUtils.redError("argument:%s is illegal"), context.getData()))
                    .append(StringUtils.lineSeparator());
            sb.append(ColorUtils.blackBold("usage: help [subCommand]"));
            System.out.println(sb);
            return;
        }
        //user type help
        if (arguArgs.size() == 1) {
            StringBuilder sb = new StringBuilder();
            // list available objects, instances and resources
            sb.append(ColorUtils.cyanAnnotation("list:      " + bundle.getString("lwm2m.operation.list.desc")))
                    .append(StringUtils.lineSeparator());
            sb.append("    usage: list [objectId]").append(StringUtils.lineSeparator());
            sb.append(StringUtils.lineSeparator());

            // enable a new object
            sb.append(ColorUtils.cyanAnnotation("create:    " + bundle.getString("lwm2m.operation.create.desc")))
                    .append(StringUtils.lineSeparator());
            sb.append("    usage: create objectId").append(StringUtils.lineSeparator());
            sb.append(StringUtils.lineSeparator());

            // disable a new object
            sb.append(ColorUtils.cyanAnnotation("del:       " + bundle.getString("lwm2m.operation.delete.desc")))
                    .append(StringUtils.lineSeparator());
            sb.append("    usage: del object").append(StringUtils.lineSeparator());
            sb.append(StringUtils.lineSeparator());

            // trigger a registration update.
            sb.append(ColorUtils.cyanAnnotation("update:    " + bundle.getString("lwm2m.operation.update.desc")))
                    .append(StringUtils.lineSeparator());
            sb.append("    usage: update").append(StringUtils.lineSeparator());
            sb.append(StringUtils.lineSeparator());

            // send data to server
            sb.append(ColorUtils.cyanAnnotation("send:      " + bundle.getString("nb.operation.command.desc")))
                    .append(StringUtils.lineSeparator());
            sb.append("    usage: send lwm2mPath [contentFormat]").append(StringUtils.lineSeparator());
            System.out.format(sb.toString());
            return;

        }
        String subCommand = arguArgs.get(1);
        StringBuilder sb = new StringBuilder();
        switch (subCommand) {
            case "list":
                // list available objects, instances and resources
                sb.append(ColorUtils.cyanAnnotation("list:      " + bundle.getString("lwm2m.operation.list.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append("    usage: list [objectId]").append(StringUtils.lineSeparator());
                sb.append(StringUtils.lineSeparator());
                System.out.format(sb.toString());
                break;
            case "create":
                // enable a new object
                sb.append(ColorUtils.cyanAnnotation("create:    " + bundle.getString("lwm2m.operation.create.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append("    usage: create objectId").append(StringUtils.lineSeparator());
                sb.append(StringUtils.lineSeparator());
                System.out.format(sb.toString());
                break;
            case "del":
                // disable a new object
                sb.append(ColorUtils.cyanAnnotation("del:       " + bundle.getString("lwm2m.operation.delete.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append("    usage: del object").append(StringUtils.lineSeparator());
                sb.append(StringUtils.lineSeparator());
                System.out.format(sb.toString());
                break;
            case "update":
                // trigger a registration update.
                sb.append(ColorUtils.cyanAnnotation("update:    " + bundle.getString("lwm2m.operation.update.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append("    usage: update").append(StringUtils.lineSeparator());
                sb.append(StringUtils.lineSeparator());
                System.out.format(sb.toString());
                break;
            case "send":
                // send data to server
                sb.append(ColorUtils.cyanAnnotation("send:      " + bundle.getString("nb.operation.command.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append("    usage: send lwm2mPath [contentFormat]").append(StringUtils.lineSeparator());
                System.out.format(sb.toString());
                break;
            default:
                break;
        }
    }
}
