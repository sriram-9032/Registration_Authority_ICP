package ug.daes.ra.dto;

import java.io.Serializable;

public class DocumentDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String document;
	private String documentName;

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	@Override
	public String toString() {
		return "DocumentDto [document=" + document + ", documentName=" + documentName + "]";
	}
}
