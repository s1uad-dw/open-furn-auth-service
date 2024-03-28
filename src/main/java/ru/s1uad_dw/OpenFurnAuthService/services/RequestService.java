package ru.s1uad_dw.OpenFurnAuthService.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import ru.s1uad_dw.OpenFurnAuthService.exceptions.*;

import java.io.IOException;
import java.util.UUID;

@Service
public class RequestService {
    public CloseableHttpResponse request(String url, String jsonBody) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json");

        httpPost.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));
        try {
            return client.execute(httpPost);
        } catch (ClientProtocolException e){
            throw new InvalidDataException("Invalid data");
        } catch (IOException e){
            throw new ServerNotResponseException("Server not response");
        }
    }
    public void handleError(int statusCode, HttpEntity responseEntity) {
        ObjectMapper objectMapper = new ObjectMapper();
        String errorMessage;

        try {
            String responseString = EntityUtils.toString(responseEntity, "UTF-8");
            JsonNode jsonResponse = objectMapper.readTree(responseString);
            errorMessage = jsonResponse.get("error").asText();
        } catch (IOException e) {
            throw new InternalServerErrorException("Can not parse response");
        }

        switch (statusCode) {
            case 400:
                throw new InvalidDataException(errorMessage);
            case 404:
                throw new ResourceNotFoundException(errorMessage);
            case 409:
                throw new UserAlreadyRegisteredException(errorMessage);
        }
    }

    public void checkStatusCode(int statusCode, CloseableHttpResponse response){
        if (statusCode >= 400) {
            handleError(statusCode, response.getEntity());
        }
    }

    public UUID getUserIdFromResponse(CloseableHttpResponse response) {
        String responseString = getResponseEntity(response);
        return UUID.fromString(responseString);
    }

    public String objectToJsonBody(Object o) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new InvalidDataException("Invalid data");
        }
    }

    public String getResponseEntity(CloseableHttpResponse response) {
        try {
            String responseEntityString = EntityUtils.toString(response.getEntity(), "UTF-8");
            if (responseEntityString.startsWith("\"") && responseEntityString.endsWith("\"")) {
                return responseEntityString.substring(1, responseEntityString.length() - 1);
            } else {
                return responseEntityString;
            }
        } catch (IOException e) {
            throw new InternalServerErrorException("Can not parse response from user service");
        }
    }
}
