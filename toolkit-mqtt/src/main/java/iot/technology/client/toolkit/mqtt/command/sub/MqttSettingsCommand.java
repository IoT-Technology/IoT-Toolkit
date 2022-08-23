package iot.technology.client.toolkit.mqtt.command.sub;

import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import iot.technology.client.toolkit.common.constants.MqttSettingsConstants;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import picocli.CommandLine;

import java.util.HashMap;
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
		HashMap<Integer, String> map = getPromptMap();
		int site = 1;
		while (true) {
			String line = null;
			try {
				line = reader.readLine(map.get(site));
				site = site + 1;
				if (site > map.size()) {
					break;
				}
				System.out.println(line);
			} catch (UserInterruptException e) {
				return ExitCodeEnum.ERROR.getValue();
			} catch (EndOfFileException e) {
				return ExitCodeEnum.ERROR.getValue();
			}
		}
		return ExitCodeEnum.SUCCESS.getValue();
	}

	public HashMap<Integer, String> getPromptMap() {
		HashMap map = new HashMap();
		map.put(1, MqttSettingsConstants.mqttVersionPrompt);
		map.put(2, MqttSettingsConstants.clientIdPrompt);
		return map;
	}
}
