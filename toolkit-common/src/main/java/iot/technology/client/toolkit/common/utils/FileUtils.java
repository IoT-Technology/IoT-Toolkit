/*
 * Copyright © 2019-2023 The Toolkit Authors
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
package iot.technology.client.toolkit.common.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mushuwei
 */
public class FileUtils {

	/**
	 * @param fileName
	 * @return whether the file exists
	 */
	public static Boolean isExist(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}


	public static void noExistAndCreateFile(String fileName, String body) {
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				File fileParent = file.getParentFile();
				if (!fileParent.exists()) {
					fileParent.mkdirs();
				}
				if (!file.exists()) {
					file.createNewFile();
					Files.write(Paths.get(fileName), body.getBytes(StandardCharsets.UTF_8));
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean notExistOrContents(String fileName) {
		if (!FileUtils.isExist(fileName)) {
			return true;
		}
		List<String> content;
		try {
			Path path = Paths.get(fileName);
			content = Files.lines(path).collect(Collectors.toList());
		} catch (IOException e) {
			return true;
		}
		return content.isEmpty();
	}

	public static void updateAllFileContents(String fileName, List<String> bodyList) {
		try {
			Files.write(Paths.get(fileName), bodyList, StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.format("fileName updateAllFileContents failed!!!");
		}
	}

	public static boolean writeDataToFile(String fileName, String body) {
		try {
			File file = new File(fileName);
			Path path = Paths.get(fileName);
			if (!file.exists()) {
				File fileParent = file.getParentFile();
				if (!fileParent.exists()) {
					fileParent.mkdirs();
				}
				if (!file.exists()) {
					file.createNewFile();
					Files.write(path, (body + System.getProperty("line.separator")).getBytes(StandardCharsets.UTF_8));
					return true;
				}
			}
			Files.write(path, (body + System.getProperty("line.separator")).getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public static List<String> getDataFromFile(String fileName) {
		List<String> datas = new ArrayList<>();
		try {
			File file = new File(fileName);
			Path path = Paths.get(fileName);
			if (file.exists()) {
				datas = Files.readAllLines(path, StandardCharsets.UTF_8);
			}
		} catch (IOException e) {
			System.out.format("get data from %s failed!", fileName);
		}
		return datas;
	}
}
