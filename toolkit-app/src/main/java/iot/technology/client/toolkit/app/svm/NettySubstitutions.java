package iot.technology.client.toolkit.app.svm;

/**
 * @author mushuwei
 */

import com.oracle.svm.core.annotate.Substitute;
import com.oracle.svm.core.annotate.TargetClass;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.JdkLoggerFactory;

/**
 * This substitution avoid having loggers added to the build
 */
@TargetClass(className = "io.netty.util.internal.logging.InternalLoggerFactory")
final class Target_io_netty_util_internal_logging_InternalLoggerFactory {

	@Substitute
	private static InternalLoggerFactory newDefaultFactory(String name) {
		return JdkLoggerFactory.INSTANCE;
	}
}

public class NettySubstitutions {
}
