package ug.daes.ra.service.iface;

import ug.daes.ra.dto.LogModelDTO;
import ug.daes.ra.exception.RAServiceException;

public interface RALocalGenrateSignatureIface {

	
	/**
	 * Generate signature. for Local Agent Subscriber
	 *
	// * @param setPin the set pin
	 * @return the string
	 * @throws RAServiceException the RA service exception
	 * @throws Exception          the exception
	 */
	public String generateSignatureForAgentSubscriber(LogModelDTO logModelDTO ) throws RAServiceException, Exception;
	
	
	/**
	 * Generate signature. for Local Agent Organization
	 *
	// * @param setPin the set pin
	 * @return the string
	 * @throws RAServiceException the RA service exception
	 * @throws Exception          the exception
	 */
	public String generateSignatureForAgentOrganization(LogModelDTO logModelDTO ) throws RAServiceException, Exception;
}
