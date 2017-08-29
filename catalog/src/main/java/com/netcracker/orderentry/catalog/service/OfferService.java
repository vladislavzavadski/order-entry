package com.netcracker.orderentry.catalog.service;

import com.netcracker.orderentry.catalog.domain.Category;
import com.netcracker.orderentry.catalog.domain.Filter;
import com.netcracker.orderentry.catalog.domain.Offer;
import com.netcracker.orderentry.catalog.domain.Tag;
import com.netcracker.orderentry.catalog.service.exception.NotFoundException;

import java.util.List;

/**
 * Created by ulza1116 on 8/21/2017.
 */
public interface OfferService {
    Offer createOffer(Offer offer);

    List<Offer> findAllOffers(int limit, int startFrom);

    Offer setTag(Tag tag, int offerId) throws NotFoundException;

    void deleteTag(Tag tag, int offerId) throws NotFoundException;

    Offer changeCategory(Category category, int offerId) throws NotFoundException;

    Offer getOffer(int offerId) throws NotFoundException;

    Offer updateOffer(Offer offer, int offerId) throws NotFoundException;

    void deleteOffer(int offerId) throws NotFoundException;

    List<Offer> searchOffers(Filter filter);
}
