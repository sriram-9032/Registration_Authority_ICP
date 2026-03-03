package ug.daes.ra.service.iface.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ug.daes.ra.dto.ApiResponses;
import ug.daes.ra.dto.DocumentDto;
import ug.daes.ra.exception.ErrorCodes;
import ug.daes.ra.exception.RAServiceException;
import ug.daes.ra.model.VisitorCompleteDetails;
import ug.daes.ra.repository.iface.VisitorCompleteDetailsRepository;
import ug.daes.ra.service.iface.VisitorCompleteDetailsIface;
import ug.daes.ra.utils.AppUtil;

import java.util.Locale;

@Service
public class VisitorCompleteDetailsImpl implements VisitorCompleteDetailsIface {

	private final VisitorCompleteDetailsRepository visitorCompleteDetailsRepository;
	private final MessageSource messageSource;

	public VisitorCompleteDetailsImpl(
			VisitorCompleteDetailsRepository visitorCompleteDetailsRepository,
			MessageSource messageSource) {
		this.visitorCompleteDetailsRepository = visitorCompleteDetailsRepository;
		this.messageSource = messageSource;
	}

	private static final Logger logger =
			LoggerFactory.getLogger(VisitorCompleteDetailsImpl.class);

	@Override
	public ApiResponses getDocumentDetailsBydocumentId(String documentNumber, String documentType) {
		try {
			DocumentDto documentDto = new DocumentDto();
			VisitorCompleteDetails visitorCompleteDetails = visitorCompleteDetailsRepository
					.idDocNumber(documentNumber);
			if (visitorCompleteDetails != null) {
				switch (documentType) {
					case "Visa_Card":
						documentDto.setDocument(visitorCompleteDetails.getSignedVisaDocument());
						documentDto.setDocumentName("Visa_Card");
						break;
					case "Non_Resident_Card":
						documentDto.setDocument(visitorCompleteDetails.getNonResidentIdDocumnet());
						documentDto.setDocumentName("Non_Resident_Card");
						break;
					case "Visitor_Card":
						documentDto.setDocument(visitorCompleteDetails.getVisitorCardPdf());
						documentDto.setDocumentName("Visitor_Card");
						break;
					case "Establishment_Card":
						documentDto.setDocument(visitorCompleteDetails.getEstablishmentCard());
						documentDto.setDocumentName("Establishment_Card");
						break;
					case "Trade_Card":
						documentDto.setDocument(visitorCompleteDetails.getTradelicenseDocumentCard());
						documentDto.setDocumentName("Trade_Card");
						break;
					case "Resident_Card":
						documentDto.setDocument(visitorCompleteDetails.getResidenceIdDocument());
						documentDto.setDocumentName("Resident_Card");
						break;
					default:
						throw new RAServiceException(ErrorCodes.E_INVALID_REQUEST);
				}
				return AppUtil.createApiResponse(true, messageSource.getMessage(
						"api.response.data.found",
						null, Locale.ENGLISH
				), documentDto);

			} else {
				return AppUtil.createApiResponse(false,    messageSource.getMessage(
						"api.error.data.not.found",
						null, Locale.ENGLISH
				), null);
			}
		} catch (Exception e) {
			logger.error("Unexpected exception", e);
			return AppUtil.createApiResponse(false,    messageSource.getMessage(
					"api.error.something.went.wrong.please.try.after.sometime",
					null, Locale.ENGLISH
			), null);
		}
	}

}