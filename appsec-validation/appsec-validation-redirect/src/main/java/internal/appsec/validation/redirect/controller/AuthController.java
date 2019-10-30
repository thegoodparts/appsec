package internal.appsec.validation.redirect.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.SneakyThrows;

@RestController
class AuthController {

    // FIXME Get whitelisted referers from application resources
    String[] whitelistedReferers;

    @PostMapping(value = "/logout")
    void logout(HttpServletRequest request, HttpServletResponse response) {
        invalidateSession();
        redirectToServiceHomePage(request, response);
    }

    private void invalidateSession() {
        // Do nothing
    }

    // FIXME Add proper logging messages for any suspicious behaviour
    @SneakyThrows
    private void redirectToServiceHomePage(HttpServletRequest request, HttpServletResponse response) {
        String referer = request.getHeader("referer");
        // FIXME Redirect to referer only if whitelisted
        response.sendRedirect(referer);
    }

}
