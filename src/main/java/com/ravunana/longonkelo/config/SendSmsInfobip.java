package com.ravunana.longonkelo.config;

import com.infobip.ApiClient;
import com.infobip.ApiException;
import com.infobip.ApiKey;
import com.infobip.BaseUrl;
import com.infobip.api.SmsApi;
import com.infobip.model.SmsAdvancedTextualRequest;
import com.infobip.model.SmsDestination;
import com.infobip.model.SmsTextualMessage;
import java.util.Collections;
import org.springframework.stereotype.Component;

@Component
public class SendSmsInfobip {

    private static final String BASE_URL = "https://k29jx8.api.infobip.com";
    private static final String API_KEY = "e4dd25ae8da5bbc4b8c2f3db18e4a7c4-a6c66e93-38d3-434a-a2a8-99c24364c117";
    private static final String RECIPIENT = "244972418829";

    public void send(String telefone, String mensagem) {
        // Create the API client and the Send SMS API instances.
        var apiClient = ApiClient.forApiKey(ApiKey.from(API_KEY)).withBaseUrl(BaseUrl.from(BASE_URL)).build();
        var sendSmsApi = new SmsApi(apiClient);

        // Create a message to send.
        var smsMessage = new SmsTextualMessage().addDestinationsItem(new SmsDestination().to("244" + telefone)).text(mensagem);

        // Create a send message request.
        var smsMessageRequest = new SmsAdvancedTextualRequest().messages(Collections.singletonList(smsMessage));

        try {
            // Send the message.
            var smsResponse = sendSmsApi.sendSmsMessage(smsMessageRequest).execute();
            System.out.println("Response body: " + smsResponse);

            // Get delivery reports. It may take a few seconds to show the above-sent message.
            var reportsResponse = sendSmsApi.getOutboundSmsMessageDeliveryReports().execute();
            System.out.println(reportsResponse.getResults());
        } catch (ApiException e) {
            System.out.println("HTTP status code: " + e.responseStatusCode());
            System.out.println("Response body: " + e.rawResponseBody());
        }
    }
}
