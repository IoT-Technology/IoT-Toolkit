package iot.technology.client.toolkit.coap.command;

import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(name = "coap", mixinStandardHelpOptions = true,
		description = "CoAP Internet of Things protocol")
public class CoapCommand implements Callable<Integer> {

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
