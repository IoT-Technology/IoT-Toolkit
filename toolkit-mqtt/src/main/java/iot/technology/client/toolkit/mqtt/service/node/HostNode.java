package iot.technology.client.toolkit.mqtt.service.node;

import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.MqttSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;

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
	public void check(NodeContext context) {
		if (Objects.isNull(context.getData())) {
			throw new IllegalArgumentException(bundle.getString("mqtt.host.error"));
		}
		if (isIpAddress(context.getData())) {
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
	public String nextNode(NodeContext context) {
		return MqttSettingsCodeEnum.PORT.getCode();
	}


	@Override
	public String getValue(NodeContext context) {
		String hostAddress = "";
		try {
			hostAddress = InetAddress.getByName(context.getData()).getHostAddress();
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
		return hostAddress;
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
