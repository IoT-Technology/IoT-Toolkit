/*
 * Copyright © 2019-2022 The Toolkit Authors
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
import iot.technology.client.toolkit.common.constants.MqttSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.NodeTypeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.common.utils.ObjectUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.mqtt.config.MqttSettings;
import iot.technology.client.toolkit.mqtt.service.MqttSettingsRuleChainProcessor;
import iot.technology.client.toolkit.mqtt.service.domain.MqttPubNewConfigDomain;
import iot.technology.client.toolkit.mqtt.service.domain.MqttPubSelectConfigDomain;
import iot.technology.client.toolkit.mqtt.service.impl.MqttBizService;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import picocli.CommandLine;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "publish",
		aliases = "pub",
		requiredOptionMarker = '*',
		description = "${bundle:mqtt.pub.description}",
		optionListHeading = "%n${bundle:general.option}:%n",
		sortOptions = false,
		footerHeading = "%nCopyright (c) 2019-2022, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei"
)
public class MqttPublishCommand implements Callable<Integer> {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);


	@Override
	public Integer call() throws Exception {
		Terminal terminal = TerminalBuilder.builder()
				.system(true)
				.build();
		LineReader reader = LineReaderBuilder.builder()
				.terminal(terminal)
				.parser(new DefaultParser())
				.build();

		MqttBizService bizService = new MqttBizService();
		if (bizService.notExistOrContents()) {
			MqttSettingsRuleChainProcessor ruleChain = new MqttSettingsRuleChainProcessor();
			Map<String, String> processor = ruleChain.getPublishNewConfigProcessor();
			String code = ruleChain.getRootPublishNewConfigNode();

			MqttPubNewConfigDomain domain = new MqttPubNewConfigDomain();
			NodeContext context = new NodeContext();
			context.setType(NodeTypeEnum.MQTT_PUBLISH.getType());
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
					tkNode.check(context);
					printValueToConsole(code, tkNode.getValue(context), context);
					ObjectUtils.setValue(domain, code, tkNode.getValue(context));
					bizService.mqttNewConfigLogic(code, data, domain, true);
					code = tkNode.nextNode(context);
				} catch (UserInterruptException e) {
					return ExitCodeEnum.ERROR.getValue();
				} catch (EndOfFileException e) {
					return ExitCodeEnum.ERROR.getValue();
				}
			}

		}
		List<String> configList = bizService.getMqttConfigList();
		NodeContext context = new NodeContext();
		context.setType(NodeTypeEnum.MQTT_PUBLISH.getType());
		context.setPromptData(configList);

		MqttSettingsRuleChainProcessor ruleChain = new MqttSettingsRuleChainProcessor();
		Map<String, String> processor = ruleChain.getPublishSelectConfigProcessor();
		MqttPubSelectConfigDomain domain = new MqttPubSelectConfigDomain();
		String code = ruleChain.getRootPublishSelectConfigNode();
		while (true) {
			String data;
			try {
				if (code.equals(MqttSettingsCodeEnum.PUBLISH_MESSAGE.getCode())
						&& !domain.getSelectConfig().equals("new")) {
					bizService.mqttPubSelectConfigPreLogic(domain);
				}
				String node = processor.get(code);
				TkNode tkNode = ObjectUtils.initComponent(node);
				if (tkNode == null) {
					break;
				}
				tkNode.prePrompt(context);
				data = reader.readLine(tkNode.nodePrompt());
				context.setData(data);
				tkNode.check(context);
				printValueToConsole(code, tkNode.getValue(context), context);
				ObjectUtils.setValue(domain, code, tkNode.getValue(context));
				bizService.mqttPubSelectConfigAfterLogic(code, data, domain, true);
				code = tkNode.nextNode(context);
			} catch (UserInterruptException e) {
				return ExitCodeEnum.ERROR.getValue();
			} catch (EndOfFileException e) {
				return ExitCodeEnum.ERROR.getValue();
			}
		}
		return ExitCodeEnum.NOTEND.getValue();
	}

	private void printValueToConsole(String code, String data, NodeContext context) {
		if (StringUtils.isNotBlank(data)) {
			if (context.isCheck() && code.equals(MqttSettingsCodeEnum.SELECT_CONFIG.getCode())) {
				MqttSettings settings = JsonUtils.jsonToObject(data, MqttSettings.class);
				System.out.format(
						ColorUtils.blackFaint(bundle.getString("call.prompt") + Objects.requireNonNull(settings).getName()) + "%n");
			} else {
				System.out.format(ColorUtils.blackFaint(bundle.getString("call.prompt") + data) + "%n");
			}

		}

	}
}
