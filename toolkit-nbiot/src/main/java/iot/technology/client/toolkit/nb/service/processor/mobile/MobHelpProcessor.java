package iot.technology.client.toolkit.nb.service.processor.mobile;

import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class MobHelpProcessor implements TkProcessor {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().startsWith("help");
	}

	@Override
	public void handle(ProcessContext context) {
		System.out.format(ColorUtils.blueAnnotation("list: " + bundle.getString("nb.operation.list.desc")));
		System.out.format("    usage: list pageNo searchValue" + "%n");
		System.out.format(" " + "%n");
		System.out.format(ColorUtils.blueAnnotation("get: " + bundle.getString("nb.operation.get.desc")));
		System.out.format("    usage: get imei" + "%n");
		System.out.format(" " + "%n");
		System.out.format(ColorUtils.blueAnnotation("del:  " + bundle.getString("nb.operation.del.desc")));
		System.out.format("    usage: del imei" + "%n");
		System.out.format(" " + "%n");
		System.out.format(ColorUtils.blueAnnotation("add:  " + bundle.getString("nb.operation.add.desc")));
		System.out.format("    usage: add imei name" + "%n");
		System.out.format(" " + "%n");
		System.out.format(ColorUtils.blueAnnotation("update:  " + bundle.getString("nb.operation.update.desc")));
		System.out.format("    usage: update imei name" + "%n");
		System.out.format(" " + "%n");
	}
}
