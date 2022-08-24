package iot.technology.client.toolkit.mqtt.service.node;

import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.MqttSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.TkNode;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * @author mushuwei
 */
public class HostNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public void check(String data) {
		if (Objects.isNull(data)) {
			throw new IllegalArgumentException(bundle.getString("mqtt.host.error"));
		}
		data = data.equals("localhost") ? "127.0.0.1" : data;
		if (isIpAddress(data)) {
			return;
		}
		boolean isHostAddress = false;
		try {
			String ip = InetAddress.getByName(data).getHostAddress();
			isHostAddress = isIpAddress(ip);
		} catch (UnknownHostException e) {
			throw new IllegalArgumentException(bundle.getString("mqtt.host.error1"));
		}
		if (isHostAddress) {
			return;
		}
		throw new IllegalArgumentException(bundle.getString("mqtt.host.error1"));
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.HOST.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(String data) {
		return MqttSettingsCodeEnum.PORT.getCode();
	}

	@Override
	public String getValue(String data) {
		return data;
	}

	private boolean isIpAddress(String value) {
		Pattern pattern = Pattern.compile("^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]"
				+ "|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$");
		return pattern.matcher(value).matches();
	}
}
