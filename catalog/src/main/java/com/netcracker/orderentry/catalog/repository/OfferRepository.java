package com.netcracker.orderentry.catalog.repository;

import com.netcracker.orderentry.catalog.domain.Offer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ulza1116 on 8/21/2017.
 */
@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer>{

    @Query("select o from Offer o join o.tags t where t.id = ?1 and o.category.id = ?2 and o.price >= ?3 and o.price <= ?4")
    List<Offer> findOffers(int tagId, int categoryId, double minPrice, double maxPrice, Pageable pageable);
}
