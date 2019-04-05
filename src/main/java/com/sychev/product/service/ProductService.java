package com.sychev.product.service;

import com.sychev.product.model.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    Page<ProductEntity> getAllProducts(Pageable pageable);

    List<ProductEntity> getProductsByCafe(UUID cafeUid);

    ProductEntity getProductByUid(UUID productUid);

    UUID addProduct(ProductEntity entity);

    void updateProduct(ProductEntity entity);

    void linkCafeAndProduct(UUID cafeUid, UUID productUid);

    void unlinkCafeAndProduct(UUID cafeUid, UUID productUid);

    void removeProduct(UUID productUid);

    void removeAllProductsByCafe(UUID cafeUid);
}
