package com.netcracker.orderentry.processor.client;

import com.netcracker.orderentry.processor.domain.to.Offer;
import com.netcracker.orderentry.processor.service.impl.exception.OfferNotFoundException;

import java.util.List;

/**
 * Created by ulza1116 on 8/22/2017.
 */
public interface OfferClient {
    Offer getSingleOffer(String stringUri, int offerId) throws OfferNotFoundException;

    List<String> authenticate(String username, String password);
}
