package com.sychev.product.model.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(
        name = "product_cafe",
        schema = "supplies",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"cafeUid", "productUid"}))
public class ProductCafeEntity {

    private Integer id;
    private UUID productUid;
    private UUID cafeUid;
    private ProductEntity product;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "productUid", nullable = false)
    public UUID getProductUid() {
        return productUid;
    }

    public ProductCafeEntity setProductUid(UUID productUid) {
        this.productUid = productUid;
        return this;
    }

    @Column(name = "cafeUid", nullable = false)
    public UUID getCafeUid() {
        return cafeUid;
    }

    public ProductCafeEntity setCafeUid(UUID cafeUid) {
        this.cafeUid = cafeUid;
        return this;
    }

    @ManyToOne(targetEntity = ProductEntity.class)
    @JoinColumn(name = "product_id", nullable = false)
    public ProductEntity getProduct() {
        return product;
    }

    public ProductCafeEntity setProduct(ProductEntity product) {
        this.product = product;
        return this;
    }
}
