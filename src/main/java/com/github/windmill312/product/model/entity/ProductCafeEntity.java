package com.github.windmill312.product.model.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(
        name = "product_cafe",
        schema = "supplies")
public class ProductCafeEntity {

    private Integer id;
    private UUID cafeUid;
    private ProductEntity product;

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(
            schema = "supplies", name = "supplies.product_cafe_id_seq",
            sequenceName = "supplies.product_cafe_id_seq", allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "supplies.product_cafe_id_seq")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "cafe_uid", nullable = false)
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
