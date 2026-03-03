package ug.daes.ra.utils;


import java.util.List;
import java.util.Locale;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import ug.daes.ra.model.OnboardingAgents;
import ug.daes.ra.model.SubscriberDevice;
import ug.daes.ra.model.SubscriberDeviceHistory;
import ug.daes.ra.repository.iface.OnboardingAgentsRepo;
import ug.daes.ra.repository.iface.SubscriberDeviceHistoryRepoIface;
import ug.daes.ra.repository.iface.SubscriberDeviceRepository;
import ug.daes.ra.response.entity.APIResponse;

@Aspect
@Component
public class ExecutionTimeAspectPolicy {
	static Logger logger = LoggerFactory.getLogger(ExecutionTimeAspectPolicy.class);
	private static final String CLASS = "ExecutionTimeAspectPolicy";

	private final MessageSource messageSource;
	private final SubscriberDeviceRepository subscriberDeviceRepoIface;
	private final SubscriberDeviceHistoryRepoIface subscriberDeviceHistoryRepoIface;
	private final OnboardingAgentsRepo onboardingAgentsRepo;
	private static final String STATUS_ACTIVE = "ACTIVE";

	public ExecutionTimeAspectPolicy(
			MessageSource messageSource,
			SubscriberDeviceRepository subscriberDeviceRepoIface,
			SubscriberDeviceHistoryRepoIface subscriberDeviceHistoryRepoIface,
			OnboardingAgentsRepo onboardingAgentsRepo) {
		this.messageSource = messageSource;
		this.subscriberDeviceRepoIface = subscriberDeviceRepoIface;
		this.subscriberDeviceHistoryRepoIface = subscriberDeviceHistoryRepoIface;
		this.onboardingAgentsRepo = onboardingAgentsRepo;
	}
	@Pointcut("execution(* ug.daes.ra.restcontroller.PKIRestController.revokeCertificate(..))")
	private void forrevokeCertificate() {
	}

	@Pointcut("execution(* ug.daes.ra.restcontroller.PKIRestController.verifyCertficatePin(..))")
	private void forverifyCertficatePin() {
	}

	@Pointcut("execution(* ug.daes.ra.restcontroller.PKIRestController.setPin(..))")
	private void forsetPin() {
	}



	@Pointcut("execution(* ug.daes.ra.restcontroller.RARestController.getCertificateDetailsBySubscriberUniqueId(..))")
	private void forgetCertificateDetailsBySubscriberUniqueId() {
	}

	@Around("forrevokeCertificate() || forverifyCertficatePin() || forsetPin() || forgetCertificateDetailsBySubscriberUniqueId()")
	public Object controllerPolicy(ProceedingJoinPoint joinPoint) throws Throwable {
		return checkPolicy(joinPoint);
	}

private Object checkPolicy(ProceedingJoinPoint joinPoint) throws Throwable {

	DeviceContext context = extractDeviceContext(joinPoint);

	logger.debug("{} :: appVersion: {}, deviceUid: {}", CLASS,
			context.appVersion(), context.deviceUid());

	if (context.isWeb()) {
		return joinPoint.proceed();
	}

	if (context.isAppVersionEmpty()) {
		return buildError("api.error.please.update.your.app");
	}

	PolicyResult policyResult = evaluatePolicy(context.deviceUid());

	if (!policyResult.allowed()) {
		return buildPolicyFailure(policyResult);
	}

	return joinPoint.proceed();
}
	private DeviceContext extractDeviceContext(ProceedingJoinPoint joinPoint) {

		String deviceUid = "";
		String appVersion = "";

		for (Object arg : joinPoint.getArgs()) {
			if (arg instanceof HttpServletRequest request) {
				deviceUid = request.getHeader("deviceId");
				appVersion = request.getHeader("appVersion");
				break;
			}
		}

		return new DeviceContext(deviceUid, appVersion);
	}

	private record DeviceContext(String deviceUid, String appVersion) {

		boolean isWeb() {
			return "WEB".equals(deviceUid);
		}

		boolean isAppVersionEmpty() {
			return appVersion == null || appVersion.isEmpty();
		}
	}

	private PolicyResult evaluatePolicy(String deviceUid) {

		Optional<SubscriberDeviceHistory> history =
				Optional.ofNullable(subscriberDeviceHistoryRepoIface.findBydeviceUid(deviceUid));

		List<SubscriberDevice> devices =
				subscriberDeviceRepoIface.findBydeviceUid(deviceUid);

		SubscriberDevice device =
				devices.isEmpty() ? null : devices.get(0);

		List<OnboardingAgents> agents =
				onboardingAgentsRepo.findByAgentdeviceUid(deviceUid);

		SubscriberDevice historySubscriber = null;

		if (history.isPresent()) {

			historySubscriber = subscriberDeviceRepoIface
					.getSubscriber(history.get().getSubscriberUid());

			SubscriberDevice activeDevice =
					subscriberDeviceRepoIface.findBydeviceUidAndStatus(deviceUid, "ACTIVE");

			if (activeDevice == null) {
				return new PolicyResult(!agents.isEmpty(), device, historySubscriber);
			}

			if ("DISABLED".equalsIgnoreCase(activeDevice.getDeviceStatus())) {
				return new PolicyResult(false, device, historySubscriber);
			}

			return new PolicyResult(true, device, historySubscriber);
		}

		if (device == null) {
			return new PolicyResult(!agents.isEmpty(), device, historySubscriber);
		}

		if (STATUS_ACTIVE.equalsIgnoreCase(device.getDeviceStatus())) {
			return new PolicyResult(true, device, historySubscriber);
		}

		if ("DISABLED".equalsIgnoreCase(device.getDeviceStatus())) {
			return new PolicyResult(false, device, historySubscriber);
		}

		return new PolicyResult(false, device, historySubscriber);
	}

	private record PolicyResult(
			boolean allowed,
			SubscriberDevice device,
			SubscriberDevice historySubscriber) {
	}

	private Object buildError(String key) {
		return new APIResponse(
				false,
				messageSource.getMessage(key, null, Locale.ENGLISH),
				null
		).toString();
	}

	private Object buildPolicyFailure(PolicyResult result) {

		if (result.device() == null && result.historySubscriber() == null) {
			return buildError("api.error.subscriber.not.found");
		}

		return buildError(
				"api.error.account.registered.on.new.device.services.disabled.on.this.device"
		);
	}
}
