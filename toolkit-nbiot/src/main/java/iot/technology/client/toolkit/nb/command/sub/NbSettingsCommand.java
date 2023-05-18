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
import iot.technology.client.toolkit.common.constants.NbSettingsCodeEnum;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ObjectUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.NbBizService;
import iot.technology.client.toolkit.nb.service.NbConfigSettingsDomain;
import iot.technology.client.toolkit.nb.service.NbRuleChainProcessor;
import iot.technology.client.toolkit.nb.service.processor.NbSettingsContext;
import iot.technology.client.toolkit.nb.service.processor.settings.*;
import org.jline.reader.*;
import org.jline.reader.impl.DefaultParser;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.NullCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "settings",
		aliases = "set",
		requiredOptionMarker = '*',
		description = "${bundle:nb.settings.desc}",
		optionListHeading = "%n${bundle:general.option}:%n",
		sortOptions = false,
		footerHeading = "%nCopyright (c) 2019-2023, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei"
)
public class NbSettingsCommand implements Callable<Integer> {

	private final NbBizService bizService = new NbBizService();
	private final NbRuleChainProcessor ruleChain = new NbRuleChainProcessor();
	private final Map<String, String> processor = ruleChain.getNbRuleChainProcessor();

	Completer listCompleter = new ArgumentCompleter(new StringsCompleter("list"), NullCompleter.INSTANCE);

	Completer showCompleter = new ArgumentCompleter(new StringsCompleter("show"), NullCompleter.INSTANCE);

	Completer delCompleter = new ArgumentCompleter(new StringsCompleter("del"), NullCompleter.INSTANCE);

	Completer addCompleter = new ArgumentCompleter(new StringsCompleter("add"), NullCompleter.INSTANCE);

	Completer helpCompleter = new ArgumentCompleter(new StringsCompleter("help"), NullCompleter.INSTANCE);

	Completer nbSettingsCompleter = new AggregateCompleter(listCompleter, showCompleter, delCompleter, addCompleter, helpCompleter);


	public final List<TkProcessor> getTkProcessorList() {
		List<TkProcessor> tkProcessorList = new ArrayList<>();
		tkProcessorList.add(new NbSettingsHelpProcessor());
		tkProcessorList.add(new NbSettingsListProcessor());
		tkProcessorList.add(new NbSettingsAddProcessor());
		tkProcessorList.add(new NbSettingsShowProcessor());
		tkProcessorList.add(new NbSettingsDelProcessor());
		return tkProcessorList;
	}

	@Override
	public Integer call() throws Exception {
		Terminal terminal = TerminalBuilder.builder()
				.system(true)
				.build();
		LineReader originReader = LineReaderBuilder.builder()
				.terminal(terminal)
				.parser(new DefaultParser()).build();
		LineReader readerWithCompleter = LineReaderBuilder.builder()
				.terminal(terminal)
				.completer(nbSettingsCompleter)
				.history(new DefaultHistory())
				.parser(new DefaultParser())
				.build();

		NodeContext context = new NodeContext();
		context.setType("settings");
		NbConfigSettingsDomain domain = new NbConfigSettingsDomain();
		String code = ruleChain.getNbTypeNode();
		NbSettingsHelpProcessor.printNbSettingsHelpInfo();
		while (true) {
			boolean isNbSettings = code.equals(NbSettingsCodeEnum.NB_SETTINGS.getCode());
			LineReader reader = isNbSettings ? readerWithCompleter : originReader;
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
				if (isNbSettings) {
					NbSettingsContext nbSettingsContext = new NbSettingsContext();
					nbSettingsContext.setNbType(domain.getNbType());
					nbSettingsContext.setData(data);
					for (TkProcessor processor : getTkProcessorList()) {
						if (processor.supports(nbSettingsContext)) {
							processor.handle(nbSettingsContext);
						}
					}
				}
				tkNode.check(context);
				ObjectUtils.setValue(domain, code, tkNode.getValue(context));
				code = tkNode.nextNode(context);
			} catch (UserInterruptException | EndOfFileException e) {
				return ExitCodeEnum.ERROR.getValue();
			}
		}
		return ExitCodeEnum.SUCCESS.getValue();
	}
}
