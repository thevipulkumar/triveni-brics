package com.trivenibricks.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * One row of the price list, e.g. "First Class Bricks".
 * Every text field has an English and a Hindi version so the
 * same row can be shown in either language.
 */
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameEn;
    private String nameHi;
    private String descriptionEn;
    private String descriptionHi;

    /** Size of the brick, e.g. 9" x 4" x 3". Same in both languages. */
    private String size;

    /** Price in rupees for the unit below. */
    private Integer price;

    /** Unit the price applies to, e.g. "per 1000 bricks". */
    private String unitEn;
    private String unitHi;

    /** Emoji / icon shown on the card. */
    private String icon;

    /** Highlight this row as the most popular choice. */
    private boolean popular;

    /** Show or hide the row on the public site. */
    private boolean available = true;

    /** Controls the order rows appear in. */
    private Integer sortOrder = 0;

    public Product() {
    }

    public Product(String nameEn, String nameHi, String descriptionEn, String descriptionHi,
                   String size, Integer price, String unitEn, String unitHi,
                   String icon, boolean popular, Integer sortOrder) {
        this.nameEn = nameEn;
        this.nameHi = nameHi;
        this.descriptionEn = descriptionEn;
        this.descriptionHi = descriptionHi;
        this.size = size;
        this.price = price;
        this.unitEn = unitEn;
        this.unitHi = unitHi;
        this.icon = icon;
        this.popular = popular;
        this.sortOrder = sortOrder;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNameEn() { return nameEn; }
    public void setNameEn(String nameEn) { this.nameEn = nameEn; }

    public String getNameHi() { return nameHi; }
    public void setNameHi(String nameHi) { this.nameHi = nameHi; }

    public String getDescriptionEn() { return descriptionEn; }
    public void setDescriptionEn(String descriptionEn) { this.descriptionEn = descriptionEn; }

    public String getDescriptionHi() { return descriptionHi; }
    public void setDescriptionHi(String descriptionHi) { this.descriptionHi = descriptionHi; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }

    public String getUnitEn() { return unitEn; }
    public void setUnitEn(String unitEn) { this.unitEn = unitEn; }

    public String getUnitHi() { return unitHi; }
    public void setUnitHi(String unitHi) { this.unitHi = unitHi; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public boolean isPopular() { return popular; }
    public void setPopular(boolean popular) { this.popular = popular; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
}
