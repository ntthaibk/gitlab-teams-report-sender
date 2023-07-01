package com.mdaq.testing;

import java.net.http.HttpClient;

public class HttpClientHandler {

    private static HttpClient httpClient;

    public static HttpClient getHttpClient(){
        if(httpClient == null){
            httpClient = HttpClient.newHttpClient();
        }
        return httpClient;
    }
}
