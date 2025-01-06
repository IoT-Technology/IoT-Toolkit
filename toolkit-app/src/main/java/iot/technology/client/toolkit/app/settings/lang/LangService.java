/*
 * Copyright © 2019-2025 The Toolkit Authors
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
package iot.technology.client.toolkit.app.settings.lang;

import iot.technology.client.toolkit.common.constants.LangEnum;
import iot.technology.client.toolkit.common.constants.SystemConfigConst;
import iot.technology.client.toolkit.common.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * @author mushuwei
 */
public class LangService {

	public static void updateLocale(String locale) {
		try {
			File file = new File(SystemConfigConst.CONFIG_LANG_FILE_NAME);
			String ls = "locale=" + locale;
			Path path = Paths.get(SystemConfigConst.CONFIG_LANG_FILE_NAME);
			if (!file.exists()) {
				File fileParent = file.getParentFile();
				if (!fileParent.exists()) {
					fileParent.mkdirs();
				}
				if (!file.exists()) {
					file.createNewFile();
					Files.write(path, ls.getBytes(StandardCharsets.UTF_8));
				}
			}
			Files.write(path, ls.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String currentLocale() {
		File file = new File(SystemConfigConst.CONFIG_LANG_FILE_NAME);
		if (file.exists()) {
			try {
				Path path = Paths.get(SystemConfigConst.CONFIG_LANG_FILE_NAME);
				Optional<String> optional = Files.lines(path).filter(str -> str.contains("locale")).findFirst();
				if (optional.isPresent()) {
					String ls = optional.get();
					int equalIndex = ls.indexOf("=");
					String locale = ls.substring(equalIndex + 1);
					return locale;
				}
				Files.write(path, "locale=en".getBytes(StandardCharsets.UTF_8));
				return LangEnum.EN.getValue();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		FileUtils.noExistAndCreateFile(SystemConfigConst.CONFIG_LANG_FILE_NAME, "locale=en");
		return LangEnum.EN.getValue();
	}
}
