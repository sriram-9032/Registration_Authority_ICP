package ug.daes.ra.restcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ug.daes.ra.dto.ApiResponses;
import ug.daes.ra.service.iface.VisitorCompleteDetailsIface;

@RestController
public class VisitorCompleteDetailsController {

	/** The Constant logger. */
	private static final Logger logger =
			LoggerFactory.getLogger(VisitorCompleteDetailsController.class);

	private final VisitorCompleteDetailsIface visitorCompleteDetailsIface;

	public VisitorCompleteDetailsController(VisitorCompleteDetailsIface visitorCompleteDetailsIface) {
		this.visitorCompleteDetailsIface = visitorCompleteDetailsIface;
	}

	@GetMapping("/api/fetch-doc-by-document-id/{documentId}")
	public ApiResponses getDocumentDetailsBydocumentId(@PathVariable String documentId) {


		String[] documentString = documentId.split("@");
		logger.info("documentString length: {}", documentString.length);
		logger.info("documentString[0]: {}, documentString[1]: {}",
				documentString[0],
				documentString.length > 1 ? documentString[1] : "null");

		return visitorCompleteDetailsIface.getDocumentDetailsBydocumentId(documentString[0],documentString[1]);
	}


}