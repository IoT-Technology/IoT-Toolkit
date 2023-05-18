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
package iot.technology.client.toolkit.nb.command.sub;

import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ObjectUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
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
		name = "call",
		requiredOptionMarker = '*',
		description = "${bundle:nb.call.desc}",
		optionListHeading = "%n${bundle:general.option}:%n",
		sortOptions = false,
		footerHeading = "%nCopyright (c) 2019-2023, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei")
public class NbCallCommand implements Callable<Integer> {

	private final NbBizService bizService = new NbBizService();
	private final NbRuleChainProcessor ruleChain = new NbRuleChainProcessor();
	private final Map<String, String> processor = ruleChain.getNbRuleChainProcessor();

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
		StringUtils.toolkitPromptText();
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
				if (data.equals("exit")) {
					break;
				}
				tkNode.check(context);
				bizService.printValueToConsole(code, context);
				ObjectUtils.setValue(domain, code, tkNode.getValue(context));
				boolean processLogicResult = bizService.nbProcessorAfterLogic(code, domain, context, terminal);
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
