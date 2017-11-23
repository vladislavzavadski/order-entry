package com.netcracker.orderentry.processor.domain.to;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class Tag {

    private Integer id;
    private String value;
    @JsonIgnore
    private List<Offer> offerList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Offer> getOfferList() {
        return offerList;
    }

    public void setOfferList(List<Offer> offerList) {
        this.offerList = offerList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        return tag.getId().equals(id);
    }

    @Override
    public int hashCode() {
        return id;
    }
}
