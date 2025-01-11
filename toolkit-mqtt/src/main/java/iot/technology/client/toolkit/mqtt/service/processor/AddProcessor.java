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
package iot.technology.client.toolkit.mqtt.service.processor;

import iot.technology.client.toolkit.common.constants.MqttSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.NodeTypeEnum;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ObjectUtils;
import iot.technology.client.toolkit.mqtt.service.MqttBizService;
import iot.technology.client.toolkit.mqtt.service.MqttSettingsRuleChainProcessor;
import iot.technology.client.toolkit.mqtt.service.domain.MqttConfigSettingsDomain;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @author mushuwei
 */
public class AddProcessor implements TkProcessor {

	private final MqttBizService bizService = new MqttBizService();
	private final MqttSettingsRuleChainProcessor ruleChain = new MqttSettingsRuleChainProcessor();
	private final Map<String, String> processor = ruleChain.getMqttRuleChainProcessor();

	@Override
	public boolean supports(ProcessContext context) {
		return Objects.requireNonNull(context.getData()).equals("add");
	}

	@Override
	public void handle(ProcessContext context) {
		try {
			Terminal terminal = TerminalBuilder.builder()
					.system(true)
					.build();
			LineReader reader = LineReaderBuilder.builder()
					.terminal(terminal)
					.parser(new DefaultParser())
					.build();

			String code = MqttSettingsCodeEnum.SETTINGS_NAME.getCode();
			MqttConfigSettingsDomain domain = new MqttConfigSettingsDomain();

			NodeContext nodeContext = new NodeContext();
			nodeContext.setType(NodeTypeEnum.MQTT_SETTINGS.getType());

			boolean isEnd = true;
			while (isEnd) {
				String node = processor.get(code);
				TkNode tkNode = ObjectUtils.initComponent(node);
				if (tkNode == null) {
					break;
				}
				tkNode.prePrompt(nodeContext);
				String data = reader.readLine(tkNode.nodePrompt());
				nodeContext.setData(data);
				tkNode.check(nodeContext);
				bizService.printValueToConsole(nodeContext);
				ObjectUtils.setValue(domain, code, tkNode.getValue(nodeContext));
				bizService.mqttProcessorAfterLogic(code, domain, nodeContext, terminal);
				code = tkNode.nextNode(nodeContext);
				if (code.equals(MqttSettingsCodeEnum.END.getCode())) {
					isEnd = false;
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
