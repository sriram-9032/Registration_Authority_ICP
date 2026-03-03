/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.exception;

/**
 * The Class RAServiceException.
 */
public class RAServiceException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new RA service exception.
	 *
	 * @param message
	 *            the message
	 */
	public RAServiceException(String message) {
		super(message);
	}
}
