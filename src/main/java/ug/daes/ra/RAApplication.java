
package ug.daes.ra;


import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import jakarta.annotation.PostConstruct;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.DefaultClientTlsStrategy;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.ssl.TrustStrategy;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import ug.daes.DAESService;
import ug.daes.PKICoreServiceException;
import ug.daes.Result;
@SpringBootApplication
public class RAApplication extends SpringBootServletInitializer {

	private static final Logger appLogger =
			LoggerFactory.getLogger(RAApplication.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(RAApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(RAApplication.class, args);
		appLogger.info("Application started");
	}

	@Bean
	public RestTemplate restTemplate() throws Exception {
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

		SSLContext sslContext = SSLContextBuilder.create()
				.loadTrustMaterial(null, acceptingTrustStrategy)
				.build();

		var tlsStrategy = new DefaultClientTlsStrategy(sslContext, NoopHostnameVerifier.INSTANCE);

		HttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
				.setTlsSocketStrategy(tlsStrategy)
				.build();

		CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(connectionManager)
				.build();

		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

		return new RestTemplate(requestFactory);
	}

	@Bean("jasyptStringEncryptor")
	public StringEncryptor stringEncryptor() {
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword("$DttKycImplEngin@@r");
		config.setAlgorithm("PBEWithHMACSHA512AndAES_256");
		config.setKeyObtentionIterations("1000");
		config.setPoolSize("1");
		config.setProviderName("SunJCE");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
		config.setStringOutputType("base64");
		encryptor.setConfig(config);
		return encryptor;
	}
	@Component
	public static class SignatureServiceInitializer {

		private static final Logger logger =
				LoggerFactory.getLogger(SignatureServiceInitializer.class);

		@PostConstruct
		public void init() {
			try {

				Result result = DAESService.initPKINativeUtils();

				if (result.getStatus() == 0) {

					if (logger.isInfoEnabled()) {

						String message = result.getStatusMessage() != null
								? new String(result.getStatusMessage())
								: "null";

						logger.info("PKI started successfully: {}", message);
					}

				} else {

					if (logger.isErrorEnabled()) {

						String error = result.getResponse() != null
								? new String(result.getResponse())
								: "null";

						logger.error("PKI initialization failed: {}", error);
					}

					throw new IllegalStateException("PKI initialization failed");
				}
			}catch (PKICoreServiceException e) {

				logger.error("Error while initializing PKI service: {}",
						e.getMessage(), e);

				throw new RuntimeException("PKI initialization error", e);
			}
		}
	}
}