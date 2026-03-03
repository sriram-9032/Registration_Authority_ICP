package ug.daes.ra.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "organization_wrapped_key")
public class OrganizationWrappedKey implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "certificate_serial_number", nullable = false, unique = true)
	private String certificateSerialNumber;
	
	@Column(name = "wrapped_key", nullable = false, unique = true)
	private String wrappedKey;

	public String getCertificateSerialNumber() {
		return certificateSerialNumber;
	}

	public void setCertificateSerialNumber(String certificateSerialNumber) {
		this.certificateSerialNumber = certificateSerialNumber;
	}

	public String getWrappedKey() {
		return wrappedKey;
	}

	public void setWrappedKey(String wrappedKey) {
		this.wrappedKey = wrappedKey;
	}
}
