package com.netcracker.orderentry.processor.service.util;

import com.netcracker.orderentry.processor.domain.Offer;

import java.util.Map;

/**
 * Created by ulza1116 on 8/22/2017.
 */
public interface OfferClient {
    Offer getSingleOffer(String stringUri, Map<String, String> params);
    Offer getSingleOffer(String stringUri);
}
