package iot.technology.client.toolkit.common.rule;

import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;

/**
 * @author mushuwei
 */
public abstract class TkAbstractProcessor implements TkProcessor {

	public boolean validateLimit(String limitStr) {
		if (!StringUtils.isNumeric(limitStr)) {
			System.out.format(ColorUtils.blackBold("limit:%s is illegal"), limitStr);
			System.out.format(" " + "%n");
			return false;
		}
		int limit = Integer.parseInt(limitStr);
		if (limit > 500) {
			System.out.format(ColorUtils.blackBold("limit:%s > 500 illegal"), limitStr);
			System.out.format(" " + "%n");
			return false;
		}
		return true;
	}
}
