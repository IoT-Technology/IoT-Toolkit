package iot.technology.client.toolkit.nb.service.processor.settings;

import iot.technology.client.toolkit.common.constants.MqttSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.NBTypeEnum;
import iot.technology.client.toolkit.common.constants.NbSettingsCodeEnum;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ObjectUtils;
import iot.technology.client.toolkit.nb.service.NbBizService;
import iot.technology.client.toolkit.nb.service.NbConfigSettingsDomain;
import iot.technology.client.toolkit.nb.service.NbRuleChainProcessor;
import iot.technology.client.toolkit.nb.service.processor.NbSettingsContext;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.Map;

/**
 * @author mushuwei
 */
public class NbSettingsAddProcessor implements TkProcessor {

	private final NbBizService bizService = new NbBizService();
	private final NbRuleChainProcessor ruleChain = new NbRuleChainProcessor();
	private final Map<String, String> processor = ruleChain.getNbRuleChainProcessor();

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().startsWith("add");
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
			NbSettingsContext nbSettingsContext = (NbSettingsContext) context;
			String code = nbSettingsContext.getNbType().equals(NBTypeEnum.TELECOM.getValue()) ?
					NbSettingsCodeEnum.NB_TEL_APP_KEY.getCode() : NbSettingsCodeEnum.NB_MOB_PRODUCT_NAME.getCode();
			NodeContext nodeContext = new NodeContext();
			nodeContext.setType("settings");
			NbConfigSettingsDomain domain = new NbConfigSettingsDomain();
			domain.setNbType(nbSettingsContext.getNbType());
			domain.setNbTelecomAppConfig("new");
			domain.setMobAppConfig("new");
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
				bizService.printValueToConsole(code, nodeContext);
				ObjectUtils.setValue(domain, code, tkNode.getValue(nodeContext));
				bizService.nbSettingsProcessorAfterLogic(code, domain);
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
