# The Good Parts of Application Security

*The Good Parts of Application Security* is a project to learn how to develop and test secure web applications following a Test Driven Development (TDD) process. It is made up of a set of Java modules defined according to [OWASP Application Security Verification Standard 4.0](https://github.com/OWASP/ASVS/blob/master/4.0/OWASP%20Application%20Security%20Verification%20Standard%204.0-en.json) (ASVS). Each module contains an example of vulnerable webapp in the [`challenges`](../../tree/challenges) branch that have to be fixed in order to pass all the tests that have already been defined for that module in that branch. On the other hand, the [`master`](../../tree/master) branch contains working examples on how to fix the different security vulnerabilities and make the modules safe.

## Modules

There is a total of 14 modules as defined in the [ASVS](https://github.com/OWASP/ASVS/blob/master/4.0/OWASP%20Application%20Security%20Verification%20Standard%204.0-en.json) project, but bear in mind that for the moment just some of them have been finished (those with a link to the module):

- appsec-access: Access Control Verification Requirements
- appsec-api: API and Web Service Verification Requirements
- appsec-architecture: Architecture, Design and Threat Modeling Requirements
- appsec-authentication: Authentication Verification Requirements
- appsec-buslogic: Business Logic Verification Requirements
- appsec-communications: Communications Verification Requirements
- appsec-config: Configuration Verification Requirements
- appsec-cryptography: Stored Cryptography Verification Requirements
- appsec-data: Data Protection Verification Requirements
- appsec-error: Error Handling and Logging Verification Requirements
- appsec-files: File and Resources Verification Requirements
- appsec-malicious: Malicious Code Verification Requirements
- appsec-session: Session Management Verification Requirements
- [`appsec-validation`](appsec-validation): Validation, Sanitization and Encoding Verification Requirements
    - [`appsec-validation-injection-sql`](appsec-validation/appsec-validation-injection-sql): SQL Injection
    - [`appsec-validation-redirect`](appsec-validation/appsec-validation-redirect): Unvalidated Redirects

## Challenges

As mentioned before, the [`challenges`](../../tree/challenges) branch contains different examples of vulnerable webapps. Each module includes a `README.md` file with detailed information about the webapp as well as some other relevant information such as a step-by-step guide on how to exploit each type of vulnerability.

For instance, consider the following piece of code taken from [appsec-validation-redirect](appsec-validation/appsec-validation-redirect). It shows an unvalidated redirect vulnerability. that is caused by a REST controller redirecting to a non-whitelisted referrer.

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

    // TODO Add proper logging categories for any suspicious behaviour
    @SneakyThrows
    private void redirectToServiceHomePage(HttpServletRequest request, HttpServletResponse response) {
        String referer = request.getHeader("referer");
        // TODO Redirect to referer only if whitelisted
        response.sendRedirect(referer);
    }

}
```

The code has been annotated with some `TODO` comments to give some clues on how to fix the vulnerability.

### Running the tests of a challenge

In order to solve a challenge, fix the vulnerable code and run the tests using Maven:


```bash
➜  appsec-validation-redirect git:(challenges) mvn clean test
```

If the code is still vulnerable, some errors will be displayed when running the tests:

```bash
...
[ERROR] Failures:
[ERROR]   AuthControllerTest.shouldLogoutAndNotRedirectToNonWhitelistedReferrers:69
Expecting:
 <302>
to be equal to:
 <200>
but was not.
[ERROR]   AuthControllerTest.shouldLogoutAndNotRedirectToNonWhitelistedReferrers:69
Expecting:
 <302>
to be equal to:
 <200>
but was not.
[ERROR]   AuthControllerTest.shouldLogoutAndNotRedirectToNonWhitelistedReferrers:69
Expecting:
 <302>
to be equal to:
 <200>
but was not.
[ERROR]   AuthControllerTest.shouldLogoutAndNotRedirectToNonWhitelistedReferrers:69
Expecting:
 <302>
to be equal to:
 <200>
but was not.
[ERROR]   AuthControllerTest.shouldLogoutAndNotRedirectToNonWhitelistedReferrers:69
Expecting:
 <302>
to be equal to:
 <200>
but was not.
[ERROR] Errors:
[ERROR]   AuthControllerTest.shouldLogoutAndNotRedirectToNonWhitelistedReferrers:67 » IllegalArgument
[INFO]
[ERROR] Tests run: 10, Failures: 5, Errors: 1, Skipped: 0
...
```

On the other hand, if the vulnerability is fixed, all tests will be green and the module will be built successfully:

```bash
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running internal.appsec.validation.redirect.controller.AuthControllerTest
19:43:00.066 [main] WARN internal.appsec.validation.redirect.controller.AuthController - [AppSec] Invalid referer header, value http://phishing.external/ does not match whitelist [wikimedia.org, wikipedia.org]
19:43:00.134 [main] WARN internal.appsec.validation.redirect.controller.AuthController - [AppSec] Invalid referer header, value http://phishing.external?referer=https://www.wikimedia.org/ does not match whitelist [wikimedia.org, wikipedia.org]
19:43:00.139 [main] WARN internal.appsec.validation.redirect.controller.AuthController - [AppSec] Invalid referer header, value http://phishing.externalwikimedia.org/ does not match whitelist [wikimedia.org, wikipedia.org]
19:43:00.144 [main] WARN internal.appsec.validation.redirect.controller.AuthController - [AppSec] Invalid referer header, value   is not a valid URL. Exception: no protocol:
19:43:00.151 [main] WARN internal.appsec.validation.redirect.controller.AuthController - [AppSec] Invalid referer header, value  is not a valid URL. Exception: no protocol:
19:43:00.155 [main] WARN internal.appsec.validation.redirect.controller.AuthController - [AppSec] Invalid referer header, value null is not a valid URL. Exception: null
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.073 s - in internal.appsec.validation.redirect.controller.AuthControllerTest
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

## How to contribute

If you would like to contribute to this project, feel free to choose any vulnerability and submit a pull request following the same structure of the existing modules:

- `REAMDE.md` with some context about the webapp, the vulnerability and how to explout it
- vulnerable webapp in the `challenges` branch
- secure webapp in the `master` branch
- set of tests to ensure that the webapp is not vulnerable anymore
