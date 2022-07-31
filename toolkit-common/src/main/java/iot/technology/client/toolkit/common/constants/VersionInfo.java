package iot.technology.client.toolkit.common.constants;

import picocli.CommandLine;

/**
 * @author mushuwei
 */
public class VersionInfo implements CommandLine.IVersionProvider {

	@Override
	public String[] getVersion() throws Exception {
		return new String[] {"0.0.3"};
	}
}
