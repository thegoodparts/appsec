# AppSec: Application Security in Practice

## Application Security Verification Standard (ASVS)

The [OWASP Application Security Verification Standard 4.0](https://github.com/OWASP/ASVS/blob/master/4.0/OWASP%20Application%20Security%20Verification%20Standard%204.0-en.json) (ASVS) Project provides a basis for testing web application technical security controls and also provides developers with a list of requirements for secure development:

- appsec-access: Access Control Verification Requirements
- appsec-api: API and Web Service Verification Requirements
- appsec-architecture: Architecture, Design and Threat Modeling Requirements
- appsec-authentication: Authentication Verification Requirements
- appsec-buslogic: Business Logic Verification Requirements
- appsec-communications: Communications Verification Requirements
- appsec-config: Configuration Verification Requirements
- appsec-cryptography: Stored Cryptography Verification Requirements
- appsec-data: Data Protection Verification Requirements
- appsec:error: Error Handling and Logging Verification Requirements
- appsec-files: File and Resources Verification Requirements
- appsec-malicious: Malicious Code Verification Requirements
- appsec-session: Session Management Verification Requirements
- [appsec-validation](appsec-validation): Validation, Sanitization and Encoding Verification Requirements

## Branches

- [master](../../tree/master): Modules with secure code for the different ASVS categories, including tests verifying the correct behavior.
- [challenges](../../tree/challenges): Modules with vulnerable code, including tests that have to be fixed to make the code secure following a Test Driven Development (TDD) approach.

## Challenges

The following piece of code taken from [appsec-validation-redirect](appsec-validation/appsec-validation-redirect) shows an unvalidated redirect vulnerability that is caused by a controller redirecting to a non-whitelisted referrer.

```java
@RestController
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

    // TODO Add proper logging messages for any suspicious behaviour
    @SneakyThrows
    private void redirectToServiceHomePage(HttpServletRequest request, HttpServletResponse response) {
        String referer = request.getHeader("referer");
        // TODO Redirect to referer only if whitelisted
        response.sendRedirect(referer);
    }

}
```

This implementation will make some tests fail when the project is compiled:

```bash
➜  appsec-validation-redirect git:(challenges) mvn clean install
```

```bash
...
[INFO] Results:
[INFO]
[ERROR] Failures:
[ERROR]   AuthControllerTest.shouldLogoutAndNotRedirectToNonWhitelistedReferrers:68
Expecting:
 <"http://phishing.external/">
to be equal to:
 <null>
but was not.
[ERROR]   AuthControllerTest.shouldLogoutAndNotRedirectToNonWhitelistedReferrers:68
Expecting:
 <"http://phishing.external?referer=https://www.wikimedia.org/">
to be equal to:
 <null>
but was not.
[ERROR]   AuthControllerTest.shouldLogoutAndNotRedirectToNonWhitelistedReferrers:68
Expecting:
 <"http://phishing.externalwikimedia.org/">
to be equal to:
 <null>
but was not.
[ERROR]   AuthControllerTest.shouldLogoutAndNotRedirectToNonWhitelistedReferrers:68
Expecting:
 <" ">
to be equal to:
 <null>
but was not.
[ERROR]   AuthControllerTest.shouldLogoutAndNotRedirectToNonWhitelistedReferrers:68
Expecting:
 <"">
to be equal to:
 <null>
but was not.
[ERROR] Errors:
[ERROR]   AuthControllerTest.shouldLogoutAndNotRedirectToNonWhitelistedReferrers:66 » IllegalArgument
[INFO]
[ERROR] Tests run: 10, Failures: 5, Errors: 1, Skipped: 0
...
```

This set of tests, together with some TODO comments scattered through each module, should help fix the vulnerability and make the code secure.
