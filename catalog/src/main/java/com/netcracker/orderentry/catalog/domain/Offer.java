package com.netcracker.orderentry.catalog.domain;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "offer")
public class Offer {
    @Id
    @GeneratedValue
    @Column(name = "of_id")
    private Integer id;

    @Column(name = "of_name")
    private String name;

    @Column(name = "of_price")
    private double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cat_id", nullable = false)
    private Category category;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "offer_tag", joinColumns = {@JoinColumn(name = "of_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private List<Tag> tags;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

}
