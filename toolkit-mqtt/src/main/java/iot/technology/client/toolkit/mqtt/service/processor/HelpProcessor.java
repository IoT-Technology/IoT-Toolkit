package iot.technology.client.toolkit.mqtt.service.processor;

import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class HelpProcessor implements TkProcessor {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().startsWith("help");
	}

	@Override
	public void handle(ProcessContext context) {
		System.out.format(ColorUtils.blueAnnotation("list: " + bundle.getString("mqtt.settings.list.desc")));
		System.out.format("    usage: list" + "%n");
		System.out.format(" " + "%n");
		System.out.format(ColorUtils.blueAnnotation("show: " + bundle.getString("mqtt.settings.show.desc")));
		System.out.format("    usage: show serial" + "%n");
		System.out.format(" " + "%n");
		System.out.format(ColorUtils.blueAnnotation("del:  " + bundle.getString("mqtt.settings.del.desc")));
		System.out.format("    usage: del serial" + "%n");
		System.out.format(" " + "%n");
		System.out.format(ColorUtils.blueAnnotation("add:  " + bundle.getString("mqtt.settings.add.desc")));
		System.out.format("    usage: add" + "%n");
		System.out.format(" " + "%n");
	}
}
