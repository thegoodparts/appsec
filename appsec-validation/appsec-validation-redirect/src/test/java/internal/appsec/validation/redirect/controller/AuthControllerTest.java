package internal.appsec.validation.redirect.controller;

import static internal.appsec.validation.redirect.controller.AuthControllerTest.Fixture.REFERER;
import static java.util.stream.Stream.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.BDDMockito.given;

import java.io.IOException;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    AuthController authController;

    @Mock
    HttpServletRequest request;

    @Spy
    MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        authController = new AuthController();
        authController.whitelistedReferers = new String[] { "owasp.org", "wikipedia.org" };
    }

    @ParameterizedTest
    @MethodSource
    void shouldLogoutAndRedirectToWhitelistedReferrers(String referer) throws IOException {
        given(request.getHeader(REFERER)).willReturn(referer);

        authController.logout(request, response);

        assertThat(response.getRedirectedUrl()).isEqualTo(referer);
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> shouldLogoutAndRedirectToWhitelistedReferrers() {
        return of(
                arguments("https://wikimedia.org"),
                arguments("https://wikimedia.org/"),
                arguments("https://www.wikimedia.org/"),
                arguments("https://www.wikipedia.org/")
        );
    }

    @ParameterizedTest
    @MethodSource
    void shouldLogoutAndNotRedirectToNonWhitelistedReferrers(String referrer) throws IOException {
        given(request.getHeader(REFERER)).willReturn(referrer);

        authController.logout(request, response);

        assertThat(response.getRedirectedUrl()).isNull();
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> shouldLogoutAndNotRedirectToNonWhitelistedReferrers() {
        return of(
                arguments("http://phishing.external/"),
                arguments("http://phishing.external?referer=https://www.wikimedia.org/"),
                arguments("http://phishing.externalwikimedia.org/"),
                arguments(" "),
                arguments(""),
                arguments((Object) null)
        );
    }

    interface Fixture {
        String REFERER = "referer";
    }
}
