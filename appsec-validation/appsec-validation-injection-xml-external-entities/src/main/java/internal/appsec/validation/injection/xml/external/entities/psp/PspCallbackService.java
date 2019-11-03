package internal.appsec.validation.injection.xml.external.entities.psp;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Service
public class PspCallbackService {

    public void processPspResponse(String requestBody) throws IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.parse(new InputSource(new ByteArrayInputStream(requestBody.getBytes(UTF_8))));

        document.getDocumentElement().normalize();

        // TODO map to PspResponse
        PspResponse pspResponse = mapToPspResponse(document);

        processResponse(pspResponse);
    }

    private PspResponse mapToPspResponse(Document document) {
        return PspResponse.builder()
                .transactionId(UUID.randomUUID().toString())
                .build();
    }

    private void processResponse(PspResponse pspResponse) {
        // Nothing to do
    }

}
