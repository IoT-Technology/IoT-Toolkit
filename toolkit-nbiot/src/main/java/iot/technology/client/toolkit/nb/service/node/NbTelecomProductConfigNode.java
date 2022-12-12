package iot.technology.client.toolkit.nb.service.node;

import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.NbSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.nb.service.telecom.domain.settings.TelProjectSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author mushuwei
 */
public class NbTelecomProductConfigNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public void prePrompt(NodeContext context) {
		if (context.getPromptData() != null) {
			List<String> configList = context.getPromptData();
			Stream.iterate(0, i -> i + 1).limit(configList.size()).forEach(i -> {
				TelProjectSettings settings = JsonUtils.jsonToObject(configList.get(i), TelProjectSettings.class);
				System.out.format(ColorUtils.greenItalic(i + "   :" + Objects.requireNonNull(settings).getProjectName()) + "%n");
			});
		}
		System.out.format(ColorUtils.greenItalic("new" + " :" + bundle.getString("nb.new.config.desc")) + "%n");
	}

	@Override
	public boolean check(NodeContext context) {
		List<String> configList = Objects.isNull(context.getPromptData()) ? new ArrayList<>() : context.getPromptData();
		List<String> indexList = Stream.iterate(0, i -> i + 1)
				.limit(configList.size())
				.map(String::valueOf)
				.collect(Collectors.toList());
		boolean matchIndex = indexList.stream().anyMatch(index -> index.equals(context.getData()));
		if (matchIndex || context.getData().equals("new")) {
			context.setCheck(true);
			return true;
		}
		System.out.format(ColorUtils.redError(bundle.getString("nb.select.config.error")));
		context.setCheck(false);
		return false;
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(NbSettingsCodeEnum.NB_TELECOM_PRODUCT_CONFIG.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(NodeContext context) {
		if (!context.isCheck()) {
			return NbSettingsCodeEnum.NB_TELECOM_PRODUCT_CONFIG.getCode();
		}
		return NbSettingsCodeEnum.NB_TEL_PRODUCT_ID.getCode();

	}

	@Override
	public String getValue(NodeContext context) {
		if (!context.isCheck()) {
			return context.getData();
		}
		if (context.getData().equals("new")) {
			return context.getData();
		}
		return context.getPromptData().get(Integer.parseInt(context.getData()));
	}
}
