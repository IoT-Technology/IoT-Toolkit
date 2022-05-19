package iot.technology.client.toolkit.coap.command;

import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@Component
@CommandLine.Command(name = "echoo", mixinStandardHelpOptions = true, version = "echoo 1.0",
		description = "Echo back a message to STDOUT.")
public class CoapConnectCommand implements Callable<Integer> {

	@CommandLine.Option(names = {"-c", "--capitalize"}, description = "To capitalize (true, false)")
	private boolean capitalize = true;

	@CommandLine.Parameters(index = "0", description = "A message to echo back.")
	private String message;


	@Override
	public Integer call() {
		if (capitalize) {
			System.out.println("message: " + message.toUpperCase());
		} else {
			System.out.println("message: " + message.toLowerCase());
		}
		return 0;
	}
}
