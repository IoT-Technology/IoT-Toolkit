package iot.technology.client.toolkit.mqtt.command.sub;

import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.util.concurrent.Future;
import iot.technology.client.toolkit.common.constants.EmojiEnum;
import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.ObjectUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.mqtt.service.MqttClientConfig;
import iot.technology.client.toolkit.mqtt.service.MqttClientService;
import iot.technology.client.toolkit.mqtt.service.MqttSettingsRuleChainProcessor;
import iot.technology.client.toolkit.mqtt.service.domain.MqttCallDomain;
import iot.technology.client.toolkit.mqtt.service.domain.MqttConnectResult;
import iot.technology.client.toolkit.mqtt.service.handler.MqttSubMessageHandler;
import iot.technology.client.toolkit.mqtt.service.impl.MqttClientServiceImpl;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "call",
		requiredOptionMarker = '*',
		description = "${bundle:mqtt.call.desc}",
		optionListHeading = "%n${bundle:general.option}:%n",
		sortOptions = false,
		footerHeading = "%nCopyright (c) 2019-2022, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei",
		versionProvider = iot.technology.client.toolkit.common.constants.VersionInfo.class
)
public class MqttCallCommand implements Callable<Integer> {

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
		MqttSettingsRuleChainProcessor ruleChain = new MqttSettingsRuleChainProcessor();
		Map<String, String> processor = ruleChain.getProcessor();
		String code = ruleChain.getRootNode();

		MqttCallDomain domain = new MqttCallDomain();
		while (true) {
			String data = null;
			try {
				String node = processor.get(code);
				TkNode tkNode = ObjectUtils.initComponent(node);
				if (tkNode == null) {
					break;
				}
				tkNode.prePrompt();
				data = reader.readLine(tkNode.nodePrompt());
				tkNode.check(data);
				if (!StringUtils.isBlank(tkNode.getValue(data))) {
					System.out.format(ColorUtils.blackFaint(bundle.getString("call.prompt") + tkNode.getValue(data)) + "%n");
				}
				ObjectUtils.setValue(domain, code, tkNode.getValue(data));
				code = tkNode.nextNode(data);
				if (code.equals("end")) {
					break;
				}
			} catch (UserInterruptException e) {
				return ExitCodeEnum.ERROR.getValue();
			} catch (EndOfFileException e) {
				return ExitCodeEnum.ERROR.getValue();
			}
		}
		mqttSubLogic(domain);
		return ExitCodeEnum.NOTEND.getValue();
	}

	private void mqttSubLogic(MqttCallDomain domain) {
		MqttClientConfig config = domain.convertMqttClientConfig(domain);
		MqttSubMessageHandler handler = new MqttSubMessageHandler();
		MqttClientService mqttClientService = new MqttClientServiceImpl(config, handler);
		MqttQoS qosLevel = MqttQoS.valueOf(Integer.parseInt(domain.getQos()));
		Future<MqttConnectResult> connectFuture = mqttClientService.connect(domain.getHost(), Integer.parseInt(domain.getPort()));
		MqttConnectResult result;
		try {
			result = connectFuture.get(config.getTimeoutSeconds(), TimeUnit.SECONDS);
			System.out.format("%s%s %s %s%s" + "%n",
					bundle.getString("mqtt.clientId"),
					domain.getClientId(),
					bundle.getString("mqtt.connect.broker"),
					domain.getHost() + ":" + domain.getPort(),
					String.format(EmojiEnum.smileEmoji));
		} catch (TimeoutException | InterruptedException | ExecutionException ex) {
			connectFuture.cancel(true);
			mqttClientService.disconnect();
			String hostPort = domain.getHost() + ":" + domain.getPort();
			throw new RuntimeException(String.format("%s %s %s.", bundle.getString("mqtt.failed.connect"), hostPort,
					String.format(EmojiEnum.pensiveEmoji)));
		}
		if (!result.isSuccess()) {
			connectFuture.cancel(true);
			mqttClientService.disconnect();
			String hostPort = domain.getHost() + ":" + domain.getPort();
			throw new RuntimeException(
					String.format("%s %s. %s %s", bundle.getString("mqtt.failed.connect"),
							hostPort, bundle.getString("mqtt.result.code"), result.getReturnCode()));
		}
		Future<Void> resultFuture = mqttClientService.on(domain.getTopic(), handler, qosLevel);
	}

}
