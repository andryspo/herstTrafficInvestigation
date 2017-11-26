package com.okhrymovych_kalandyak.services;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractCustomHttpClient {

    protected CloseableHttpClient closeableHttpClient;

    public abstract HttpResponse doGet(String url) throws URISyntaxException, IOException;

    public abstract HttpResponse doGet(String url, String token) throws URISyntaxException, IOException;

    public abstract HttpResponse doPost(String url, HttpEntity body) throws IOException, URISyntaxException;

    public abstract HttpResponse doPost(String url, HttpEntity body, String token) throws IOException, URISyntaxException;

    public abstract HttpResponse doGetText(String url, String token) throws URISyntaxException, IOException;
}
