/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */

package ug.daes.ra.enums;

/**
 * The Enum ServiceName.
 */
public enum ServiceName {

	/** The service provider onboarded. */
	SERVICE_PROVIDER_ONBOARDED,

	/** The subscriber onboarded. */
	SUBSCRIBER_ONBOARDED,

	/** The certificate pair issued. */
	CERTIFICATE_PAIR_ISSUED,

	/** The subscriber authenticated. */
	SUBSCRIBER_AUTHENTICATED,

	/** The digitally signed. */
	DIGITALLY_SIGNED,

	/** The key pair generated. */
	KEY_PAIR_GENERATED,

	/** The csr created. */
	CSR_CREATED,

	/** The certificate generated. */
	CERTIFICATE_GENERATED,

	/** The certificate revoked. */
	CERTIFICATE_REVOKED,

	/** The pki authenticated. */
	PKI_AUTHENTICATED,

	/** The signature verified. */
	SIGNATURE_VERIFIED,

	/** The other. */
	OTHER,

	/** The digitally signing failed. */
	DIGITALLY_SIGNING_FAILED
}
