package iot.technology.client.toolkit.nb.command;

import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.NbSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.nb.service.processor.TelecomProcessor;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "nb",
		requiredOptionMarker = '*',
		header = "@|fg(Cyan),bold ${bundle:nb.header}|@",
		description = "@|fg(Cyan),italic ${bundle:nb.description}|@",
		optionListHeading = "%n${bundle:general.option}:%n",
		subcommands = {
		},
		footerHeading = "%nCopyright (c) 2019-2022, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei")
public class NbCommand implements Callable<Integer> {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "${bundle:general.help.description}")
	boolean usageHelpRequested;

	public final List<TkProcessor> getTkProcessorList() {
		List<TkProcessor> tkProcessorList = new ArrayList<>();
		tkProcessorList.add(new TelecomProcessor());
		return tkProcessorList;
	}

	@Override
	public Integer call() throws Exception {
		Terminal terminal = TerminalBuilder.builder()
				.system(true)
				.build();
		LineReader reader = LineReaderBuilder.builder()
				.terminal(terminal)
				.parser(new DefaultParser())
				.build();

		String prompt = bundle.getString(NbSettingsCodeEnum.NB_TYPE.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
		printPrePrompt();
		while (true) {
			String data;
			try {
				data = reader.readLine(prompt);
				ProcessContext context = new ProcessContext();
				context.setData(data);
				for (TkProcessor processor : getTkProcessorList()) {
					if (processor.supports(context)) {
						processor.handle(context);
					}
				}
			} catch (UserInterruptException | EndOfFileException e) {
				return ExitCodeEnum.ERROR.getValue();
			}
		}
	}

	public void printPrePrompt() {
		if (bundle.getLocale().equals(Locale.CHINESE)) {
			System.out.format(ColorUtils.greenItalic("(1) 电信AEP * ") + "%n");
			System.out.format(ColorUtils.greenItalic("(2) 移动OneNET") + "%n");
		}
	}
}
