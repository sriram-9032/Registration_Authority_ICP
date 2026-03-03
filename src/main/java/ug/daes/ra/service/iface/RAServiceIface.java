/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021,
 * All rights reserved.
 */
package ug.daes.ra.service.iface;

import java.util.Map;


import ug.daes.ra.dto.ApiResponses;
import ug.daes.ra.dto.ExpireSubscriberCertRequestDTO;
import ug.daes.ra.dto.RARequestDTO;
import ug.daes.ra.exception.RAServiceException;
import ug.daes.ra.request.entity.VerifyCertificatesPins;


/**
 * The Interface RAServiceIface.
 */
public interface RAServiceIface {

	/**
	 * Issue certificate.
	 *
	 * @param requestBody the request body
	 * @return the string
	 * @throws RAServiceException the RA service exception
	 * @throws Exception          the exception
	 */
	public String issueCertificate(RARequestDTO requestBody) throws RAServiceException, Exception;

	/**
	 * Revoke certificate.
	 *
	 * @param requestBody the request body
	 * @return the string
	 * @throws RAServiceException the RA service exception
	 * @throws Exception
	 */
	public String revokeCertificate(RARequestDTO requestBody) throws RAServiceException, Exception;

	/**
	 * @return the string
	 * @throws RAServiceException the RA service exception
	 * @throws Exception
	 */
	public String checkCertificateStatus() throws RAServiceException, Exception;

	/**
	 * Issue certificate call back.
	 *
	 * @param requestBody the request body
	 * @return the string
	 * @throws RAServiceException the RA service exception
	 * @throws Exception
	 */
	public String issueCertificateCallBack(Map<String, String> requestBody) throws RAServiceException, Exception;

	/**
	 * Gets the certificate life cycle logs by subscriber id.
	 *
	 * @param rapkiSubscriberdata the rapki subscriberdata
	 * @return the certificate life cycle logs by subscriber id
	 * @throws RAServiceException the RA service exception
	 */
	public String getCertificateLifeCycleLogsBySubscriberUniqueId(String rapkiSubscriberdata) throws RAServiceException,Exception;

	/**
	 * Gets the certificate details by subscriber unique id.
	 *
	 * @param subscriberUniqueId the rapki subscriberdata
	 * @return the certificate details by subscriber unique id
	 * @throws RAServiceException the RA service exception
	 */
	public String getCertificateDetailsBySubscriberUniqueId(String subscriberUniqueId) throws RAServiceException,Exception;

	/**
	 * Verify certficates pins.
	 *
	 * @param certificatesPins the certificates pins
	 * @return the string
	 * @throws RAServiceException the RA service exception
	 */
	public String verifyCertficatesPins(VerifyCertificatesPins certificatesPins) throws Exception;

	public String getCertificateDataByCertificateType(String subscriberUid, String certType)
			throws RAServiceException, Exception;

	public String getOrganizationCertificateDataByCertificateType(String orgId, String certType)
			throws RAServiceException, Exception;

	public ApiResponses getCertificateDataByCertificateTypeForAgent(String subscriberUid, String certType)
			throws RAServiceException, Exception;

	public ApiResponses getOrganizationCertificateDataByCertificateTypeForAgent(String orgId, String certType)
			throws RAServiceException, Exception;


	ApiResponses expireSubscriberCert(ExpireSubscriberCertRequestDTO expireSubscriberCertRequestDTO) throws Exception;

//	ApiResponse fetchVisitorDetailsBySearchType(int searchType,String searchValue);
}
