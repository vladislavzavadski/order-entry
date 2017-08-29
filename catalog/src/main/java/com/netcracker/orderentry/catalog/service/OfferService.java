package com.netcracker.orderentry.catalog.service;

import com.netcracker.orderentry.catalog.domain.Category;
import com.netcracker.orderentry.catalog.domain.Filter;
import com.netcracker.orderentry.catalog.domain.Offer;
import com.netcracker.orderentry.catalog.domain.Tag;

import java.util.List;

/**
 * Created by ulza1116 on 8/21/2017.
 */
public interface OfferService {
    Offer createOffer(Offer offer);

    List<Offer> findAllOffers(int limit, int startFrom);

    void setTag(Tag tag, int offerId);

    void deleteTag(Tag tag, int offerId);

    void changeCategory(Category category, int offerId);

    Offer getOffer(int offerId);

    void updateOffer(Offer offer, int offerId);

    void deleteOffer(int offerId);

    List<Offer> searchOffers(Filter filter);
}
