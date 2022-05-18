package iot.technology.client.toolkit.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author mushuwei
 */
@SpringBootApplication(scanBasePackages = "iot.technology.client.toolkit")
public class ToolKitApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToolKitApplication.class, args);
	}
}
