/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.request.entity;

import java.io.Serializable;

/**
 * The Class RequestEntity.
 */
public class RequestEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The post request. */
	private PostRequest postRequest;

	/** The transaction type. */
	private String transactionType;

	/**
	 * Gets the post request.
	 *
	 * @return the post request
	 */
	public PostRequest getPostRequest() {
		return postRequest;
	}

	/**
	 * Sets the post request.
	 *
	 * @param postRequest
	 *            the new post request
	 */
	public void setPostRequest(PostRequest postRequest) {
		this.postRequest = postRequest;
	}

	/**
	 * Gets the transaction type.
	 *
	 * @return the transaction type
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * Sets the transaction type.
	 *
	 * @param transactionType
	 *            the new transaction type
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RequestEntity [postRequest=" + postRequest + ", transactionType=" + transactionType + "]";
	}

}
