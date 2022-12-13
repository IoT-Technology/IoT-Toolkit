package iot.technology.client.toolkit.nb.command;

import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ObjectUtils;
import iot.technology.client.toolkit.nb.service.NbBizService;
import iot.technology.client.toolkit.nb.service.NbConfigSettingsDomain;
import iot.technology.client.toolkit.nb.service.NbRuleChainProcessor;
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

	private final NbBizService bizService = new NbBizService();
	private final NbRuleChainProcessor ruleChain = new NbRuleChainProcessor();
	private final Map<String, String> processor = ruleChain.getNbRuleChainProcessor();

	@CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "${bundle:general.help.description}")
	boolean usageHelpRequested;

	@Override
	public Integer call() throws Exception {
		Terminal terminal = TerminalBuilder.builder()
				.system(true)
				.build();
		LineReader reader = LineReaderBuilder.builder()
				.terminal(terminal)
				.parser(new DefaultParser())
				.build();

		NodeContext context = new NodeContext();
		NbConfigSettingsDomain domain = new NbConfigSettingsDomain();
		String code = ruleChain.getNbTypeNode();

		while (true) {
			try {
				String node = processor.get(code);
				TkNode tkNode = ObjectUtils.initComponent(node);
				if (tkNode == null) {
					break;
				}
				tkNode.prePrompt(context);
				String data = reader.readLine(tkNode.nodePrompt());
				context.setData(data);
				tkNode.check(context);
				bizService.printValueToConsole(code, context);
				ObjectUtils.setValue(domain, code, tkNode.getValue(context));
				bizService.nbProcessorAfterLogic(code, domain, context);
				code = tkNode.nextNode(context);
			} catch (UserInterruptException | EndOfFileException e) {
				return ExitCodeEnum.ERROR.getValue();
			}
		}
		return ExitCodeEnum.SUCCESS.getValue();
	}


}
