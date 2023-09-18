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
package iot.technology.client.toolkit.mqtt.command.sub;

import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import iot.technology.client.toolkit.common.constants.NodeTypeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.constants.SystemConfigConst;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.FileUtils;
import iot.technology.client.toolkit.common.utils.ObjectUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.mqtt.service.MqttBizService;
import iot.technology.client.toolkit.mqtt.service.MqttSettingsRuleChainProcessor;
import iot.technology.client.toolkit.mqtt.service.domain.MqttConfigSettingsDomain;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import picocli.CommandLine;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
        name = "shell-mode",
        aliases = "shell",
        requiredOptionMarker = '*',
        description = "${bundle:mqtt.shell.description}",
        optionListHeading = "%n${bundle:general.option}:%n",
        sortOptions = false,
        footerHeading = "%nCopyright (c) 2019-2023, ${bundle:general.copyright}",
        footer = "%nDeveloped by mushuwei"
)
public class MqttShellCommand implements Callable<Integer> {

    private final MqttBizService bizService = new MqttBizService();
    private final MqttSettingsRuleChainProcessor ruleChain = new MqttSettingsRuleChainProcessor();
    private final Map<String, String> processor = ruleChain.getMqttRuleChainProcessor();

    @Override
    public Integer call() throws Exception {
        Terminal terminal = TerminalBuilder.builder()
                .system(true)
                .build();
        LineReader reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .parser(new DefaultParser())
                .build();

        MqttConfigSettingsDomain domain = new MqttConfigSettingsDomain();
        NodeContext context = new NodeContext();
        String code = ruleChain.getMqttRootConfigNode();
        StringUtils.toolkitPromptText();

        while (true) {
            String data;
            try {
                String node = processor.get(code);
                TkNode tkNode = ObjectUtils.initComponent(node);
                if (tkNode == null) {
                    break;
                }
                tkNode.prePrompt(context);
                data = reader.readLine(tkNode.nodePrompt());
                context.setData(data);
                if (data.equals("exit")) {
                    break;
                }
                tkNode.check(context);
                String codeData = tkNode.getValue(context);
                bizService.printValueToConsole(context);
                ObjectUtils.setValue(domain, code, codeData);
                boolean processLogicResult = bizService.mqttProcessorAfterLogic(code, domain, context, terminal);
                if (!processLogicResult) {
                    break;
                }
                code = tkNode.nextNode(context);
            } catch (UserInterruptException | EndOfFileException e) {
                return ExitCodeEnum.ERROR.getValue();
            }
        }
        return ExitCodeEnum.SUCCESS.getValue();
    }
}
