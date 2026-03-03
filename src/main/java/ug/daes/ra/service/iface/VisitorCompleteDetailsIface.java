package ug.daes.ra.service.iface;

import ug.daes.ra.dto.ApiResponses;

public interface VisitorCompleteDetailsIface {
	ApiResponses getDocumentDetailsBydocumentId(String documentNumber,String documentType);

}
