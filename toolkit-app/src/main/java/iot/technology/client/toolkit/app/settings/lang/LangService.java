package iot.technology.client.toolkit.app.settings.lang;

import iot.technology.client.toolkit.common.constants.LangEnum;

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

	public static final String userHome = System.getProperty("user.home");

	private static final String FILE_NAME = userHome + File.separator + ".toolkit" + File.separator + "config" + File.separator + "lang";


	public static void updateLocale(String locale) {
		try {
			File file = new File(FILE_NAME);
			String ls = "locale=" + locale;
			Path path = Paths.get(FILE_NAME);
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
		File file = new File(FILE_NAME);
		if (file.exists()) {
			try {
				Path path = Paths.get(FILE_NAME);
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
		noExistAndCreateFile();
		return LangEnum.EN.getValue();
	}

	public static void noExistAndCreateFile() {
		try {
			File file = new File(FILE_NAME);
			if (!file.exists()) {
				File fileParent = file.getParentFile();
				if (!fileParent.exists()) {
					fileParent.mkdirs();
				}
				if (!file.exists()) {
					file.createNewFile();
					Files.write(Paths.get(FILE_NAME), "locale=en".getBytes(StandardCharsets.UTF_8));
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
