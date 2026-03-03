package ug.daes.ra.config;

import io.sentry.Sentry;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;

@Service
public class SentryClientExceptions {


	public void captureExceptions(Throwable e) {
		Sentry.captureException(e);
	}

	public void captureTags(String suid,String mobileNumber ,String methodName, String controller) throws UnknownHostException {
		Sentry.setTag("subscriber_id", suid);
		Sentry.setTag("controller", controller);
		Sentry.setTag("methodName",methodName);
		Sentry.setTag("controller",controller);
	}
}
