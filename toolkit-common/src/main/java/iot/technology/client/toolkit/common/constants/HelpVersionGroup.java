/*
 * Copyright © 2019-2022 The Toolkit Authors
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
package iot.technology.client.toolkit.common.constants;

import picocli.CommandLine;

/**
 * @author mushuwei
 */
public class HelpVersionGroup {

	@CommandLine.Option(names = {"-V", "--version"}, versionHelp = true, description = "${bundle:general.version.description}")
	boolean versionInfoRequested;

	@CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "${bundle:general.help.description}")
	boolean usageHelpRequested;
}
