package internal.appsec.validation.redirect.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
class AuthController {

    // TODO Get whitelisted referers from application resources
    String[] whitelistedReferers;

    @PostMapping(value = "/logout")
    void logout(HttpServletRequest request, HttpServletResponse response) {
        invalidateSession();
        redirectToServiceHomePage(request, response);
    }

    private void invalidateSession() {
        // Do nothing
    }

    private void redirectToServiceHomePage(HttpServletRequest request, HttpServletResponse response) {
        // TODO Get referer header from request
        String referer = "";

        // TODO Redirect to referer only if whitelisted
        try {
            response.sendRedirect(referer);
        } catch (Exception e) {
            // TODO Add proper logging messages for any suspicious behaviour
        }
    }

}
