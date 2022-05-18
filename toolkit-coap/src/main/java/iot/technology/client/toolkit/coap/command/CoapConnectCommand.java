package iot.technology.client.toolkit.coap.command;

import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@Component
@CommandLine.Command
public class CoapConnectCommand implements Callable<Integer> {

	@CommandLine.Option(names = {"-h", "--hostname"}, description = "The URI of the server to connect to.")
	private String uri;

	@CommandLine.Parameters(index = "0", description = "")
	private String host;


	@Override
	public Integer call() throws Exception {
		return null;
	}
}
