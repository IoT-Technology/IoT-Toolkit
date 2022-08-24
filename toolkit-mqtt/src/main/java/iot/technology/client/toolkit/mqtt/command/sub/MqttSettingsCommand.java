package iot.technology.client.toolkit.mqtt.command.sub;

import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.mqtt.service.MqttSettingsRuleChainProcessor;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import picocli.CommandLine;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "settings",
		aliases = "set",
		requiredOptionMarker = '*',
		description = "${bundle:mqtt.settings.desc}",
		optionListHeading = "%n${bundle:general.option}:%n",
		sortOptions = false,
		footerHeading = "%nCopyright (c) 2019-2022, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei",
		versionProvider = iot.technology.client.toolkit.common.constants.VersionInfo.class
)
public class MqttSettingsCommand implements Callable<Integer> {


	@Override
	public Integer call() throws Exception {
		Terminal terminal = TerminalBuilder.builder()
				.system(true).build();
		LineReader reader = LineReaderBuilder.builder()
				.terminal(terminal)
				.parser(new DefaultParser())
				.build();
		MqttSettingsRuleChainProcessor ruleChain = new MqttSettingsRuleChainProcessor();
		Map<String, String> processor = ruleChain.getProcessor();
		String code = ruleChain.getRootNode();

		while (true) {
			String data = null;
			try {
				String node = processor.get(code);
				TkNode tkNode = initComponent(node);
				if (tkNode == null) {
					break;
				}
				data = reader.readLine(tkNode.nodePrompt());
				tkNode.check(data);
				System.out.println(tkNode.getValue(data));
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
		return ExitCodeEnum.NOTEND.getValue();
	}

	private TkNode initComponent(String node) {
		TkNode tkNode = null;
		if (node != null) {
			try {
				Class<?> componentClazz = Class.forName(node);
				tkNode = (TkNode) (componentClazz.getDeclaredConstructor().newInstance());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return tkNode;
	}

}
