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
