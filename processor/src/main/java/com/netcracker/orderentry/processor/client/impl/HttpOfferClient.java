package com.netcracker.orderentry.processor.client.impl;

import com.netcracker.orderentry.processor.domain.to.Offer;
import com.netcracker.orderentry.processor.service.impl.exception.OfferNotFoundException;
import com.netcracker.orderentry.processor.client.OfferClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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
    public Offer getSingleOffer(String stringUri, int offerId) throws OfferNotFoundException {
        URI uri = UrlUtil.buildUri(stringUri + offerId);

        try {
            ResponseEntity<Offer> responseEntity = restTemplate.exchange(new RequestEntity<>(getHttpHeaders(), HttpMethod.GET, uri), Offer.class);
            return responseEntity.getBody();
        }
        catch (HttpClientErrorException ex){

            if(ex.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new OfferNotFoundException("Offer with id = " + offerId + " was not found");
            }
            else {
                throw ex;
            }

        }
    }

    private HttpHeaders getHttpHeaders(){
        Object cookies = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().
                getSession(false).getAttribute("inventoryCookies");
        List<String> listCookies = (List<String>)cookies;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Cookie",listCookies.stream().collect(Collectors.joining(";")));
        return httpHeaders;
    }

    @Override
    public List<String> authenticate(String username, String password){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("username", username);
        map.add("password", password);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Void> responseEntity = restTemplate.postForEntity("http://localhost:8081/login", request, Void.class);
        return responseEntity.getHeaders().get("Set-Cookie");
    }

}
