/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021,
 * All rights reserved.
 */
package ug.daes.ra.service.iface;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import ug.daes.ra.dto.ApiResponses;

import ug.daes.ra.dto.RARequestDTO;
import ug.daes.ra.exception.RAServiceException;
import ug.daes.ra.request.entity.QrData;
import ug.daes.ra.response.entity.ApiResponse_CertCount;
import ug.daes.ra.response.entity.Response;


/**
 * The Interface RASubscriberServiceIface.
 */
public interface SubscriberRAServiceIface {
	/**
	 * Gets the subscriber details by search type.
	 *
	 * @param searchType  the search type
	 * @param searchValue the search value
	 * @return the subscriber details by search type
	 * @throws RAServiceException      the RA service exception
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	String getSubscriberDetailsBySearchType(int searchType, String searchValue)
			throws RAServiceException, JsonMappingException, JsonProcessingException,Exception;

	/**
	 * Gets the count of all subscribers.
	 *
	 * @return the count of all subscribers
	 * @throws RAServiceException the RA service exception
	 * @throws
	 */
	String getCountOfAllSubscribers() throws RAServiceException,Exception;

	/**
	 * Update subscriber status.
	 *
	 * @param detailsRequest the details request
	 * @return the string
	 * @throws RAServiceException the RA service exception
	 */
	String updateSubscriberStatus(RARequestDTO detailsRequest) throws RAServiceException,Exception;

	/**
	 * Update device status and subscriber status.
	 *
	 * @param subscriberUniqueId the subscriber unique id
	 * @return the string
	 * @throws RAServiceException
	 * @throws Exception
	 */
	String updateDeviceStatusAndSubscriberStatus(String subscriberUniqueId) throws RAServiceException,Exception;

	/**
	 * Gets the subscriber list.
	 *
	 * @param type  the type
	 * @param value the value
	 * @return the subscriber list
	 * @throws RAServiceException the RA service exception
	 */
	String getSubscriberList(int type, String value) throws RAServiceException,Exception;


	ApiResponse_CertCount getCountOfAllCertificates() throws Exception;

	ApiResponse_CertCount getSubscriberAndCertCount();

	String getSubscriberName(String subscriberUniqueId) throws RAServiceException,Exception;

	ApiResponses compareImage(String suid , String capturedImage) throws Exception;

	ApiResponses getFeatures(String suid , String capturedImage) throws Exception;

	ApiResponses getSubscriberPhoto(String suid) throws Exception;

	public Response getSignForQr(QrData qrData) throws Exception;
}
