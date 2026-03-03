/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.request.entity;

import java.io.Serializable;

/**
 * The Class PostRequest.
 */
public class PostRequest implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The request body. */
	private String requestBody;

	/** The callback URI. */
	private String callbackURI;

	/** The pki key ID. */
	private String pkiKeyID;

	/** The certificate type. */
	private String certificateType;

	/** The certificate id. */
	private int certificateId;

	/** The hashdata. */
	private int hashdata;
	
	/**The Agent FcmToken*/
	private String agentsFCMToken;

	/**
	 * Gets the request body.
	 *
	 * @return the request body
	 */
	public String getRequestBody() {
		return requestBody;
	}

	/**
	 * Sets the request body.
	 *
	 * @param requestBody
	 *            the new request body
	 */
	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	/**
	 * Gets the callback URI.
	 *
	 * @return the callback URI
	 */
	public String getCallbackURI() {
		return callbackURI;
	}

	/**
	 * Sets the callback URI.
	 *
	 * @param callbackURI
	 *            the new callback URI
	 */
	public void setCallbackURI(String callbackURI) {
		this.callbackURI = callbackURI;
	}

	/**
	 * Gets the hashdata.
	 *
	 * @return the hashdata
	 */
	public int getHashdata() {
		return hashdata;
	}

	/**
	 * Gets the pki key ID.
	 *
	 * @return the pki key ID
	 */
	public String getPkiKeyID() {
		return pkiKeyID;
	}

	/**
	 * Sets the pki key ID.
	 *
	 * @param pkiKeyID
	 *            the new pki key ID
	 */
	public void setPkiKeyID(String pkiKeyID) {
		this.pkiKeyID = pkiKeyID;
	}

	/**
	 * Gets the certificate type.
	 *
	 * @return the certificate type
	 */
	public String getCertificateType() {
		return certificateType;
	}

	/**
	 * Sets the certificate type.
	 *
	 * @param certificateType
	 *            the new certificate type
	 */
	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	/**
	 * Sets the hashdata.
	 *
	 * @param hashdata
	 *            the new hashdata
	 */
	public void setHashdata(int hashdata) {
		this.hashdata = hashdata;
	}

	/**
	 * Gets the certificate id.
	 *
	 * @return the certificate id
	 */
	public int getCertificateId() {
		return certificateId;
	}

	/**
	 * Sets the certificate id.
	 *
	 * @param certificateId
	 *            the new certificate id
	 */
	public void setCertificateId(int certificateId) {
		this.certificateId = certificateId;
	}
	
	

	public String getAgentsFCMToken() {
		return agentsFCMToken;
	}

	public void setAgentsFCMToken(String agentsFCMToken) {
		this.agentsFCMToken = agentsFCMToken;
	}

	@Override
	public String toString() {
		return "PostRequest [requestBody=" + requestBody + ", callbackURI=" + callbackURI + ", pkiKeyID=" + pkiKeyID
				+ ", certificateType=" + certificateType + ", certificateId=" + certificateId + ", hashdata=" + hashdata
				+ ", agentsFCMToken=" + agentsFCMToken + "]";
	}

}

// + "\"HSMPlugin\"" + ":" + "\"" + HSMPlugin + "\","
// + "\"CAPlugin\"" + ":" + "\"" + CAPlugin + "\""
