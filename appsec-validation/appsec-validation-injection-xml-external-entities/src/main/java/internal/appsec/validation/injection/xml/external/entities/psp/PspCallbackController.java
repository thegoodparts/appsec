package internal.appsec.validation.injection.xml.external.entities.psp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PspCallbackController {

    @Autowired
    PspCallbackService pspCallbackService;

    @PostMapping
    void onPaymentServiceProviderCallbackReceived(@RequestBody String requestBody) {
        pspCallbackService.processPspResponse(requestBody);
    }

}
