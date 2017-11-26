package com.okhrymovych_kalandyak.services;

import com.google.gson.Gson;
import com.okhrymovych_kalandyak.constants.URLConstants;
import com.okhrymovych_kalandyak.dto.User;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class AuthenticationService{

    private Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private CustomHttpClient customHttpClient;

    public String auth(final User user) throws IOException, URISyntaxException, AuthenticationException {

        StringEntity stringEntity;
        stringEntity = new StringEntity(new Gson().toJson(user));
        stringEntity.setContentType("application/json");

        HttpResponse httpResponse = customHttpClient
                .doPost(URLConstants.LOGIN_URL, stringEntity);

        String token = "";

        try {

            Header[] headers = httpResponse.getAllHeaders();

            if (headers != null && headers.length != 0) {
                for (Header header : headers) {
                    if (header.getName().equals("Authorization")) {
                        token = header.getValue();
                        break;
                    }
                }
            }

            if (token == null || token.isEmpty()) {

                throw new AuthenticationException("cannot authenticate: bad credentials: username " +
                        user.getUserName() + " password: " + user.getPassword());
            }

        } catch (RuntimeException e) {
            logger.error("cannot retrieve token");

            throw new AuthenticationException("cannot authenticate, cannot get server connection");
        }

        return token;
    }
}

