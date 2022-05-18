package iot.technology.client.toolkit.app;

import iot.technology.client.toolkit.coap.command.CoapConnectCommand;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * @author mushuwei
 */
@Component
public class ToolKitRunner implements CommandLineRunner, ExitCodeGenerator {

	private final CoapConnectCommand coapConnectCommand;

	private final CommandLine.IFactory factory;

	private int exitCode;

	public ToolKitRunner(CoapConnectCommand coapConnectCommand, CommandLine.IFactory factory) {
		this.coapConnectCommand = coapConnectCommand;
		this.factory = factory;
	}

	@Override
	public void run(String... args) throws Exception {
		exitCode = new CommandLine(coapConnectCommand, factory).execute(args);
	}

	@Override
	public int getExitCode() {
		return exitCode;
	}
}