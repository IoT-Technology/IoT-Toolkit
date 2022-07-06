package iot.technology.client.toolkit.mqtt.service.domain;

/**
 * @author mushuwei
 */
public class ChannelClosedException extends RuntimeException {

	private static final long serialVersionUID = 6266638352424706909L;

	public ChannelClosedException() {
	}

	public ChannelClosedException(String message) {
		super(message);
	}

	public ChannelClosedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ChannelClosedException(Throwable cause) {
		super(cause);
	}

	public ChannelClosedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
