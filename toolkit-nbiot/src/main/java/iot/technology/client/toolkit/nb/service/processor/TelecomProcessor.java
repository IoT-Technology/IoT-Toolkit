package iot.technology.client.toolkit.nb.service.processor;

import iot.technology.client.toolkit.common.constants.NBTypeEnum;
import iot.technology.client.toolkit.common.constants.SystemConfigConst;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.FileUtils;
import iot.technology.client.toolkit.common.utils.ObjectUtils;
import iot.technology.client.toolkit.nb.service.NbRuleChainProcessor;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.util.Map;
import java.util.Objects;

/**
 * @author mushuwei
 */
public class TelecomProcessor implements TkProcessor {

	private final NbRuleChainProcessor ruleChain = new NbRuleChainProcessor();
	private final Map<String, String> processor = ruleChain.getNbRuleChainProcessor();

	@Override

	public boolean supports(ProcessContext context) {
		return Objects.requireNonNull(context.getData()).equals(NBTypeEnum.TELECOM.getCode());
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
			boolean isNew = FileUtils.notExistOrContents(SystemConfigConst.SYS_NB_TELECOM_APP_FILE_NAME);
			String code = isNew ? ruleChain.getNbTelecomNewRootNode() : ruleChain.getNbTelecomSelectRootNode();

			NodeContext nodeContext = new NodeContext();
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

				code = tkNode.nextNode(nodeContext);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
