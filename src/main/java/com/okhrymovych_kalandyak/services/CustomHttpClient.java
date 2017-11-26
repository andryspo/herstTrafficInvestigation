package com.okhrymovych_kalandyak.services;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class CustomHttpClient extends AbstractCustomHttpClient {

    private HttpGet httpGet;

    private HttpPost httpPost;

    public CustomHttpClient() {
        httpGet = new HttpGet();
        httpPost = new HttpPost();
    }

    @Override
    public HttpResponse doGet(String url) throws URISyntaxException, IOException {
        httpGet.setURI(new URI(url));
        closeableHttpClient = HttpClients.createDefault();
        return closeableHttpClient.execute(httpGet);
    }

    @Override
    public HttpResponse doGetText(String url, String token) throws URISyntaxException, IOException {
        httpGet.setURI(new URI(url));
        httpGet.setHeader("accept", "text/plain");
        httpGet.setHeader("Accept-Charset", "utf-8");

        if(token != null && !token.isEmpty()) {
            httpGet.setHeader("Authorization", token);
        }

        closeableHttpClient = HttpClients.createDefault();

        return closeableHttpClient.execute(httpGet);
    }


    @Override
    public HttpResponse doGet(String url, String token) throws URISyntaxException, IOException {
        httpGet.setURI(new URI(url));
        httpGet.setHeader("accept", "application/json");
        httpGet.setHeader("Authorization", token);
        closeableHttpClient = HttpClients.createDefault();

        return closeableHttpClient.execute(httpGet);
    }

    @Override
    public HttpResponse doPost(String url, HttpEntity httpEntity) throws IOException, URISyntaxException {

        httpPost.setURI(new URI(url));
        httpPost.setEntity(httpEntity);
        closeableHttpClient = HttpClients.createDefault();
        return closeableHttpClient.execute(httpPost);
    }

    @Override
    public HttpResponse doPost(String url, HttpEntity httpEntity, String token) throws IOException, URISyntaxException {
        httpPost.setURI(new URI(url));
        httpPost.setEntity(httpEntity);

        httpPost.setHeader("accept", "application/json");
        httpPost.setHeader("Authorization", token);


        closeableHttpClient = HttpClients.createDefault();

        return closeableHttpClient.execute(httpPost);
    }

}
