/*
 * @copyright (DigitalTrust Technologies Private Limited, Hyderabad) 2021, 
 * All rights reserved.
 */
package ug.daes.ra.dto;

/**
 * The Class LogModel.
 */
public class LogModelDTO {

	/** The identifier. */
	private String identifier;

	/** The correlation id. */
	private String correlationID;

	/** The transaction id. */
	private String transactionID;

	/** The sub transaction ID. */
	private String subTransactionID;

	/** The timestamp. */
	private String timestamp;

	/** The start time. */
	private String startTime;

	/** The end time. */
	private String endTime;

	/** The geolocation. */
	private String geoLocation;

	/** The callstack. */
	private String callStack;

	/** The service name. */
	private String serviceName;

	/** The transaction type. */
	private String transactionType;

	/** The transaction sub type. */
	private String transactionSubType;

	/** The log message type. */
	private String logMessageType;

	/** The log message. */
	private String logMessage;

	/** The service provider name. */
	private String serviceProviderName;

	/** The service provider app name. */
	private String serviceProviderAppName;

	/** The signature type. */
	private String signatureType;

	/** The e seal used. */
	private boolean eSealUsed;

	/** The checksum. */
	private String checksum;

	/**
	 * Gets the identifier.
	 *
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Sets the identifier.
	 *
	 * @param identifier the new identifier
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * Gets the service name.
	 *
	 * @return the service name
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * Sets the service name.
	 *
	 * @param serviceName the new service name
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * Gets the start time.
	 *
	 * @return the start time
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * Sets the start time.
	 *
	 * @param startTime the new start time
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * Gets the end time.
	 *
	 * @return the end time
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * Sets the end time.
	 *
	 * @param endTime the new end time
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the timestamp.
	 *
	 * @param timestamp the new timestamp
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Gets the log message.
	 *
	 * @return the log message
	 */
	public String getLogMessage() {
		return logMessage;
	}

	/**
	 * Sets the log message.
	 *
	 * @param logMessage the new log message
	 */
	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}

	/**
	 * Gets the log message type.
	 *
	 * @return the log message type
	 */
	public String getLogMessageType() {
		return logMessageType;
	}

	/**
	 * Sets the log message type.
	 *
	 * @param logMessageType the new log message type
	 */
	public void setLogMessageType(String logMessageType) {
		this.logMessageType = logMessageType;
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
	 * @param transactionType the new transaction type
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * Gets the correlation ID.
	 *
	 * @return the correlation ID
	 */
	public String getCorrelationID() {
		return correlationID;
	}

	/**
	 * Sets the correlation ID.
	 *
	 * @param correlationID the new correlation ID
	 */
	public void setCorrelationID(String correlationID) {
		this.correlationID = correlationID;
	}

	/**
	 * Gets the transaction ID.
	 *
	 * @return the transaction ID
	 */
	public String getTransactionID() {
		return transactionID;
	}

	/**
	 * Sets the transaction ID.
	 *
	 * @param transactionID the new transaction ID
	 */
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	/**
	 * Gets the sub transaction ID.
	 *
	 * @return the sub transaction ID
	 */
	public String getSubTransactionID() {
		return subTransactionID;
	}

	/**
	 * Sets the sub transaction ID.
	 *
	 * @param subTransactionID the new sub transaction ID
	 */
	public void setSubTransactionID(String subTransactionID) {
		this.subTransactionID = subTransactionID;
	}

	/**
	 * Gets the geo location.
	 *
	 * @return the geo location
	 */
	public String getGeoLocation() {
		return geoLocation;
	}

	/**
	 * Sets the geo location.
	 *
	 * @param geoLocation the new geo location
	 */
	public void setGeoLocation(String geoLocation) {
		this.geoLocation = geoLocation;
	}

	/**
	 * Gets the transaction sub type.
	 *
	 * @return the transaction sub type
	 */
	public String getTransactionSubType() {
		return transactionSubType;
	}

	/**
	 * Sets the transaction sub type.
	 *
	 * @param transactionSubType the new transaction sub type
	 */
	public void setTransactionSubType(String transactionSubType) {
		this.transactionSubType = transactionSubType;
	}

	/**
	 * Gets the service provider name.
	 *
	 * @return the service provider name
	 */
	public String getServiceProviderName() {
		return serviceProviderName;
	}

	/**
	 * Sets the service provider name.
	 *
	 * @param serviceProviderName the new service provider name
	 */
	public void setServiceProviderName(String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
	}

	/**
	 * Gets the service provider app name.
	 *
	 * @return the service provider app name
	 */
	public String getServiceProviderAppName() {
		return serviceProviderAppName;
	}

	/**
	 * Sets the service provider app name.
	 *
	 * @param serviceProviderAppName the new service provider app name
	 */
	public void setServiceProviderAppName(String serviceProviderAppName) {
		this.serviceProviderAppName = serviceProviderAppName;
	}

	/**
	 * Gets the signature type.
	 *
	 * @return the signature type
	 */
	public String getSignatureType() {
		return signatureType;
	}

	/**
	 * Sets the signature type.
	 *
	 * @param signatureType the new signature type
	 */
	public void setSignatureType(String signatureType) {
		this.signatureType = signatureType;
	}

	/**
	 * Checks if is e seal used.
	 *
	 * @return true, if is e seal used
	 */
	public boolean iseSealUsed() {
		return eSealUsed;
	}

	/**
	 * Sets the e seal used.
	 *
	 * @param eSealUsed the new e seal used
	 */
	public void seteSealUsed(boolean eSealUsed) {
		this.eSealUsed = eSealUsed;
	}

	/**
	 * Gets the call stack.
	 *
	 * @return the call stack
	 */
	public String getCallStack() {
		return callStack;
	}

	/**
	 * Sets the call stack.
	 *
	 * @param callStack the new call stack
	 */
	public void setCallStack(String callStack) {
		this.callStack = callStack;
	}

	/**
	 * Gets the checksum.
	 *
	 * @return the checksum
	 */
	public String getChecksum() {
		return checksum;
	}

	/**
	 * Sets the checksum.
	 *
	 * @param checksum the new checksum
	 */
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{" + "\"identifier\":\"" + identifier + "\"," + "\"correlationID\":\"" + correlationID + "\","
				+ "\"transactionID\":\"" + transactionID + "\"," + "\"subTransactionID\":\"" + subTransactionID + "\","
				+ "\"timestamp\":\"" + timestamp + "\"," + "\"startTime\":\"" + startTime + "\"," + "\"endTime\":\""
				+ endTime + "\"," + "\"geoLocation\":\"" + geoLocation + "\"," + "\"callStack\":" + callStack + ","
				+ "\"serviceName\":\"" + serviceName + "\"," + "\"transactionType\":\"" + transactionType + "\","
				+ "\"transactionSubType\":\"" + transactionSubType + "\"," + "\"logMessageType\":\"" + logMessageType
				+ "\"," + "\"logMessage\":\"" + logMessage + "\"," + "\"serviceProviderName\":\"" + serviceProviderName
				+ "\"," + "\"serviceProviderAppName\":\"" + serviceProviderAppName + "\"," + "\"signatureType\":\""
				+ signatureType + "\"," + "\"eSealUsed\":" + eSealUsed + "," + "\"checksum\":\"" + checksum + "\""
				+ "}";
	}

}
