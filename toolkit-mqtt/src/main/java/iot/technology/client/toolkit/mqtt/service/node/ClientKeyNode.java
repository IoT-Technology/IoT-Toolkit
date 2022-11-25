package iot.technology.client.toolkit.mqtt.service.node;

import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.MqttSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.FileUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class ClientKeyNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public boolean check(NodeContext context) {
		if (StringUtils.isBlank(context.getData())) {
			System.out.format(ColorUtils.redError(bundle.getString("param.error")));
			return false;
		}
		if (!FileUtils.isExist(context.getData())) {
			System.out.format(ColorUtils.redError(bundle.getString("file.error")));
			return false;
		}
		return true;
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.CLIENT_KEY.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}


	@Override
	public String nextNode(NodeContext context) {
		return MqttSettingsCodeEnum.ADVANCED.getCode();
	}


	@Override
	public String getValue(NodeContext context) {
		return context.getData();
	}

	@Override
	public void prePrompt(NodeContext context) {

	}
}
