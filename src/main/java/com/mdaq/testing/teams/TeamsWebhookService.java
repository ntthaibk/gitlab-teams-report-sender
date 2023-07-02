package com.mdaq.testing.teams;

import com.mdaq.testing.FileHandler;
import com.mdaq.testing.GsonHandler;
import com.mdaq.testing.HttpClientHandler;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TeamsWebhookService {
    private static final String WEBHOOK_URL = "https://mdaqglobal.webhook.office.com/webhookb2/56533880-4413-4929-8c69-671c04b38f5f@aba9e4aa-6a76-4ed2-bcae-721a1aa9e76b/IncomingWebhook/d4c3e1505b7940aca702fd4015eddda0/2ce7a116-21a3-4c22-972a-61b534fbb0fa";
    public void notifyTeam(AdaptiveCard adaptiveCard) throws IOException, InterruptedException {
        var response =  HttpClientHandler.getHttpClient().send(
                HttpRequest.newBuilder()
                        .uri(URI.create(WEBHOOK_URL))
                        .POST(HttpRequest.BodyPublishers.ofString(GsonHandler.getGson().toJson(adaptiveCard)))
                        .header("Content-Type", "application/json")
                        .build(),
                HttpResponse.BodyHandlers.ofString()
        );

        if(response.statusCode()!= 200 || !response.body().equals("1")){
            throw new IllegalStateException(String.format("Failed to send message to teams {%s}", response.body()));
        }

    }
}
