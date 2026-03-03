/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021,
 * All rights reserved.
 */
package ug.daes.ra.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * The Class PropertiesConstants.
 */
@Component
public class PropertiesConstants implements CommandLineRunner {

	private final PropertiesConfiguration propertiesConfiguration;

	public PropertiesConstants(PropertiesConfiguration propertiesConfiguration) {
		this.propertiesConfiguration = propertiesConfiguration;
	}

	// 1. Rename to camelCase
	// 2. Change to private so they don't have to be final
	private static String pkiUrl;

	private static String issueCertificateCallbackUrl;

	private static String notification;

	private static String status;

	// 3. Provide Public Static Getters for access
	public static String getPkiUrl() {
		return pkiUrl;
	}

	public static String getIssueCertificateCallbackUrl() {
		return issueCertificateCallbackUrl;
	}

	public static String getNotification() {
		return notification;
	}

	public static String getStatus() {
		return status;
	}

	@Override
	public void run(String... args) throws Exception {
		status = propertiesConfiguration.getStatus();
		pkiUrl = propertiesConfiguration.getPki();
		issueCertificateCallbackUrl = propertiesConfiguration.getIssuecertificatecallbackurl();
		notification = propertiesConfiguration.getNotification();
	}
}