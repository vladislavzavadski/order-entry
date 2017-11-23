package com.netcracker.orderentry.processor.client.impl;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

/**
 * Created by ulza1116 on 8/23/2017.
 */
public class UrlUtil {

    public static URI buildUri(String stringUri, Map<String, String> params){
        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(stringUri);
        params.forEach((k, v)->uriComponentsBuilder.queryParam(k, v));
        return uriComponentsBuilder.build().toUri();
    }

    public static URI buildUri(String stringUri){
        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(stringUri);
        return uriComponentsBuilder.build().toUri();
    }

}
