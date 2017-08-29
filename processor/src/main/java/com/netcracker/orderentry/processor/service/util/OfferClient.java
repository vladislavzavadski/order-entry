package com.netcracker.orderentry.processor.service.util;

import com.netcracker.orderentry.processor.domain.Offer;
import com.netcracker.orderentry.processor.service.impl.exception.OfferNotFoundException;

import java.util.Map;

/**
 * Created by ulza1116 on 8/22/2017.
 */
public interface OfferClient {
    Offer getSingleOffer(String stringUri, int offerId) throws OfferNotFoundException;
}
