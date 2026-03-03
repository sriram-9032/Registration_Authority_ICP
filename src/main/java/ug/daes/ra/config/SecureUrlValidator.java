package ug.daes.ra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

@Component
public class SecureUrlValidator {

    @Value("${security.allowed-hosts}")
    private List<String> allowedHosts;

    public void validate(String url) {
        try {
            URI uri = new URI(url);

            if (!"https".equalsIgnoreCase(uri.getScheme())) {
                throw new IllegalArgumentException("Only HTTPS allowed");
            }

            String host = uri.getHost();

            if (host == null || !allowedHosts.contains(host)) {
                throw new IllegalArgumentException("Host not allowed");
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid or unsafe URL", e);
        }
    }
}
