package com.netcracker.orderentry.processor.service.util.impl;

import com.netcracker.orderentry.processor.domain.Offer;
import com.netcracker.orderentry.processor.service.util.OfferClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

/**
 * Created by ulza1116 on 8/22/2017.
 */
@Component
public class HttpOfferClient implements OfferClient {

    private final RestTemplate restTemplate;

    @Autowired
    public HttpOfferClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Offer getSingleOffer(String stringUri, Map<String, String> params){
        URI uri = UrlUtil.buildUri(stringUri, params);
        ResponseEntity<Offer> responseEntity = restTemplate.exchange(new RequestEntity<>(HttpMethod.GET, uri), Offer.class);
        return responseEntity.getBody();
    }

    @Override
    public Offer getSingleOffer(String stringUri){
        URI uri = UrlUtil.buildUri(stringUri);
        ResponseEntity<Offer> responseEntity = restTemplate.exchange(new RequestEntity<>(HttpMethod.GET, uri), Offer.class);
        return responseEntity.getBody();
    }

}
