package com.digitalAcademy.customer.api;

import com.digitalAcademy.customer.model.response.GetLoanInfoResponse;
import com.digitalAcademy.customer.util.Util;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class LoanApi {
    private RestTemplate restTemplate;

    @Value("${spring.loan.host}")
    private String host;

    @Value("${spring.loan.endpoint.getInfo}")
    private String getInfo;

    public LoanApi(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public GetLoanInfoResponse getLoanInfoResponse(Long id) throws Exception{
        String data = "{}";
        try{
            RequestEntity requestEntity = RequestEntity
                    .get(URI.create(host + "/" + getInfo + "/" + id)).build();
            System.out.println("Request: " + requestEntity.getMethod() + "Url:" + requestEntity.getUrl());
            ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity,String.class);

            if(responseEntity.getStatusCode().value() == 200){
                JSONObject resp = new JSONObject(responseEntity.getBody());
                JSONObject status = new JSONObject(resp.getString("status"));
                if(status.getString("code").equals("0")){
                    data = resp.get("data").toString();
                }
            }

        }catch (final HttpClientErrorException httpClientErrorException){
            System.out.println("httpClientErrorException: " + httpClientErrorException);
            throw new Exception("httpClientErrorException");
        }catch (HttpServerErrorException httpServerErrorException){
            System.out.println("httpServerErrorException: " + httpServerErrorException);
            throw new Exception("httpServerErrorException");
        }catch (Exception e){
            throw e;
        }
        return Util.readValue(data,GetLoanInfoResponse.class);
    }
}
