package iot.technology.client.toolkit.mqtt.service.node;

import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.MqttSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ColorUtils;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class HostNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public boolean check(NodeContext context) {
		if (Objects.isNull(context.getData())) {
			System.out.format(ColorUtils.redError(bundle.getString("mqtt.host.error")));
			context.setCheck(false);
			return false;
		}
		if (isIpAddress(context.getData())) {
			context.setCheck(true);
			return true;
		}
		System.out.format(ColorUtils.redError(bundle.getString("mqtt.host.error1")));
		context.setCheck(false);
		return false;
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.HOST.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(NodeContext context) {
		if (!context.isCheck()) {
			return MqttSettingsCodeEnum.HOST.getCode();
		}
		return MqttSettingsCodeEnum.PORT.getCode();
	}


	@Override
	public String getValue(NodeContext context) {
		return context.getData();
	}


	@Override
	public void prePrompt(NodeContext context) {
	}

	private boolean isIpAddress(String address) {
		if (address.isEmpty()) {
			return false;
		}
		try {
			Object res = InetAddress.getByName(address);
			return res instanceof Inet4Address || res instanceof Inet6Address;
		} catch (final UnknownHostException exception) {
			return false;
		}
	}
}
