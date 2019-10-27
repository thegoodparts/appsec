package internal.appsec.validation.redirect.controller;

import java.net.URL;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.assertj.core.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
class AuthController {

    @Value("${whitelisted-referers}")
    @VisibleForTesting
    String[] whitelistedReferers;

    @PostMapping(value = "/logout")
    void logout(HttpServletRequest request, HttpServletResponse response) {
        String referer = request.getHeader("referer");

        try {
            URL refererURL = new URL(referer);
            String host = refererURL.getHost();

            if (isReferrerHostAllowed(host)) {
                response.sendRedirect(referer);
            } else {
                log.warn("Invalid referer header, value {} does not match whitelist {}", referer, whitelistedReferers);
            }
        } catch (Exception e) {
            log.warn("Invalid referer header, value {} is not a valid URL. Exception: {}", referer, e.getMessage());
        }
    }

    private boolean isReferrerHostAllowed(String host) {
        return host != null && Arrays.stream(whitelistedReferers)
                .anyMatch(allowedReferrer -> host.equals(allowedReferrer) || host.endsWith("." + allowedReferrer));
    }

}
