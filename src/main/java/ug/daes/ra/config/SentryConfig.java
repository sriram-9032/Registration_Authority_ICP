package ug.daes.ra.config;

import io.sentry.Sentry;
import io.sentry.SentryOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SentryConfig {

	@Bean
	public Sentry.OptionsConfiguration<SentryOptions> sentryOptions() {
		return options -> {
			options.setDsn("https://aa960d7a068a43ad808a148e929be532@monitor.digitaltrusttech.com/27");
			options.setDebug(false); // Enable debug mode to log Sentry activity
			options.setTracesSampleRate(1.0); // Capture 20% of transactions
		};
	}
}
