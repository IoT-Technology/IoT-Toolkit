package iot.technology.client.toolkit.common.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

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
}
