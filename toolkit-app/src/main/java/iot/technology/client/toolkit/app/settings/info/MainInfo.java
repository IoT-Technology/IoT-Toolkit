/*
 * Copyright © 2019-2025 The Toolkit Authors
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
package iot.technology.client.toolkit.app.settings.info;

import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class MainInfo {

    public static void printMainInfo() {
        //i18n
        ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

        //
        StringBuilder sb = new StringBuilder();
        sb.append(ColorUtils.colorBold(bundle.getString("general.header"), ""))
                .append(StringUtils.lineSeparator());
        sb.append(StringUtils.lineSeparator());

        sb.append(ColorUtils.colorBold(bundle.getString("general.usage"), ""))
                .append(StringUtils.lineSeparator());
        sb.append(ColorUtils.colorItalic("toolkit [--version] [--help] <command> [<args>]", "yellow"))
                .append(StringUtils.lineSeparator());
        sb.append(StringUtils.lineSeparator());
        sb.append(ColorUtils.colorBold(bundle.getString("general.description"), "red")).append(StringUtils.lineSeparator());
        sb.append(StringUtils.lineSeparator());


        sb.append(bundle.getString("general.common.commands.desc")).append(StringUtils.lineSeparator());
        // toolkit config simple description
        sb.append(String.format("%s %s", ColorUtils.colorBold(bundle.getString("config.description"), ""),
                        "(" + bundle.getString("general.reference") + ColorUtils.colorBold("toolkit config -h", "red") + ")"))
                .append(StringUtils.lineSeparator());
        sb.append(String.format("%s %s", ColorUtils.colorBold("locale        ", "yellow"),
                ColorUtils.colorItalic((bundle.getString("config.lang.header") + " " + bundle.getString("config.locale")),
                        "cyan"))).append(StringUtils.lineSeparator());
        sb.append(StringUtils.lineSeparator());

        // toolkit coap simple description
        sb.append(String.format("%s %s",
                        ColorUtils.colorBold(bundle.getString("coap.description"), ""),
                        "(" + bundle.getString("general.reference") + ColorUtils.colorBold("toolkit coap -h", "red") + ")"))
                .append(StringUtils.lineSeparator());
        sb.append(String.format("%s %s",
                        ColorUtils.colorBold("disc:          ", "yellow"),
                        ColorUtils.colorItalic(bundle.getString("coap.disc.description"), "cyan")))
                .append(StringUtils.lineSeparator());
        sb.append(String.format("%s %s",
                        ColorUtils.colorBold("get:           ", "yellow"),
                        ColorUtils.colorItalic(bundle.getString("coap.get.description"), "cyan")))
                .append(StringUtils.lineSeparator());
        sb.append(String.format("%s %s",
                        ColorUtils.colorBold("post:          ", "yellow"),
                        ColorUtils.colorItalic(bundle.getString("coap.post.description"), "cyan")))
                .append(StringUtils.lineSeparator());
        sb.append(String.format("%s %s",
                        ColorUtils.colorBold("put:           ", "yellow"),
                        ColorUtils.colorItalic(bundle.getString("coap.put.description"), "cyan")))
                .append(StringUtils.lineSeparator());
        sb.append(String.format("%s %s",
                        ColorUtils.colorBold("delete:        ", "yellow"),
                        ColorUtils.colorItalic(bundle.getString("coap.del.description"), "cyan")))
                .append(StringUtils.lineSeparator());
        sb.append(StringUtils.lineSeparator());

        // toolkit mqtt simple description
        sb.append(String.format("%s %s",
                        ColorUtils.colorBold(bundle.getString("mqtt.description"), ""),
                        "(" + bundle.getString("general.reference") + ColorUtils.colorBold("toolkit mqtt -h", "red") + ")"))
                .append(StringUtils.lineSeparator());
        sb.append(String.format("%s %s",
                        ColorUtils.colorBold("desc:          ", "yellow"),
                        ColorUtils.colorItalic(bundle.getString("mqtt.description"), "cyan")))
                .append(StringUtils.lineSeparator());
        sb.append(String.format("%s %s",
                        ColorUtils.colorBold("set:           ", "yellow"),
                        ColorUtils.colorItalic(bundle.getString("mqtt.settings.desc"), "cyan")))
                .append(StringUtils.lineSeparator());
        sb.append(String.format("%s %s",
                        ColorUtils.colorBold("shell:         ", "yellow"),
                        ColorUtils.colorItalic(bundle.getString("mqtt.shell.description"), "cyan")))
                .append(StringUtils.lineSeparator());

        sb.append(StringUtils.lineSeparator());
        // toolkit nb simple description
        sb.append(String.format("%s %s",
                        ColorUtils.colorBold(bundle.getString("nb.description"), ""),
                        "(" + bundle.getString("general.reference") + ColorUtils.colorBold("toolkit nb -h", "red") + ")"))
                .append(StringUtils.lineSeparator());
        sb.append(String.format("%s %s",
                        ColorUtils.colorBold("desc:          ", "yellow"),
                        ColorUtils.colorItalic(bundle.getString("nb.desc.desc"), "cyan")))
                .append(StringUtils.lineSeparator());
        sb.append(String.format("%s %s",
                        ColorUtils.colorBold("set:           ", "yellow"),
                        ColorUtils.colorItalic(bundle.getString("nb.settings.desc"), "cyan")))
                .append(StringUtils.lineSeparator());
        sb.append(String.format("%s %s",
                        ColorUtils.colorBold("shell:         ", "yellow"),
                        ColorUtils.colorItalic(bundle.getString("nb.shell.desc"), "cyan")))
                .append(StringUtils.lineSeparator());
        sb.append(StringUtils.lineSeparator());


        sb.append(bundle.getString("general.main.page.help"));
        System.out.println(sb);
    }
}
