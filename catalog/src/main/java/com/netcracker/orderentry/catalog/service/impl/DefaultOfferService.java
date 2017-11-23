package com.netcracker.orderentry.catalog.service.impl;

import com.netcracker.orderentry.catalog.domain.Category;
import com.netcracker.orderentry.catalog.domain.Filter;
import com.netcracker.orderentry.catalog.domain.Offer;
import com.netcracker.orderentry.catalog.domain.Tag;
import com.netcracker.orderentry.catalog.repository.OfferRepository;
import com.netcracker.orderentry.catalog.service.OfferService;
import com.netcracker.orderentry.catalog.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ulza1116 on 8/21/2017.
 */
@Service
public class DefaultOfferService implements OfferService {

    private OfferRepository offerRepository;

    @Autowired
    public DefaultOfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    @PreAuthorize("isAuthenticated() and hasAnyRole('ROLE_ADMIN')")
    public Offer createOffer(Offer offer){
        return offerRepository.save(offer);
    }

    @Override
    public List<Offer> findAllOffers(int limit, int startFrom){
        PageRequest pageRequest = new PageRequest(startFrom, limit);
        Page<Offer> offers = offerRepository.findAll(pageRequest);
        return offers.getContent();
    }

    @Override
    @PreAuthorize("isAuthenticated() and hasAnyRole('ROLE_ADMIN')")
    public Offer setTag(Tag tag, int offerId) throws NotFoundException {
        Offer offer = offerRepository.findOne(offerId);

        if(offer == null){
            throw new NotFoundException("Offer with id = " + offerId + " was not found");
        }

        offer.getTags().add(tag);
        return offerRepository.save(offer);
    }

    @Override
    @PreAuthorize("isAuthenticated() and hasAnyRole('ROLE_ADMIN')")
    public void deleteTag(Tag tag, int offerId) throws NotFoundException {
        Offer offer = offerRepository.findOne(offerId);

        if(offer == null){
            throw new NotFoundException("Offer with id = " + offerId + " was not found");
        }

        offer.getTags().removeIf(tag1 -> tag1.getId() == tag.getId());
        offerRepository.save(offer);
    }

    @Override
    @PreAuthorize("isAuthenticated() and hasAnyRole('ROLE_ADMIN')")
    public Offer changeCategory(Category category, int offerId) throws NotFoundException {
        Offer offer = offerRepository.findOne(offerId);

        if(offer == null){
            throw new NotFoundException("Offer with id = " + offerId + " was not found");
        }

        offer.setCategory(category);
        return offerRepository.save(offer);

    }

    @Override
    public Offer getOffer(int offerId) throws NotFoundException {
        Offer offer = offerRepository.findOne(offerId);

        if(offer == null){
            throw new NotFoundException("Offer with id="+offerId+ " was not found");
        }

        return offer;
    }

    @Override
    @PreAuthorize("isAuthenticated() and hasAnyRole('ROLE_ADMIN')")
    public Offer updateOffer(Offer offer, int offerId) throws NotFoundException {
        Offer storedOffer = offerRepository.findOne(offerId);

        if(!offerRepository.exists(offerId)){
            throw new NotFoundException("Offer with id="+offerId+ " was not found");
        }

        storedOffer.setName(offer.getName());
        storedOffer.setPrice(offer.getPrice());

        return offerRepository.save(storedOffer);
    }

    @Override
    @PreAuthorize("isAuthenticated() and hasAnyRole('ROLE_ADMIN')")
    public void deleteOffer(int offerId) throws NotFoundException {

        if(!offerRepository.exists(offerId)){
            throw new NotFoundException("Offer with id="+offerId+ " was not found");
        }

        offerRepository.delete(offerId);
    }

    @Override
    public List<Offer> searchOffers(Filter filter){
        PageRequest pageRequest = new PageRequest(filter.getStartFrom(), filter.getLimit());
        return offerRepository.findOffers(filter.getTagId(), filter.getCategoryId(), filter.getMinPrice(),
                filter.getMaxPrice(), pageRequest);
    }

}
