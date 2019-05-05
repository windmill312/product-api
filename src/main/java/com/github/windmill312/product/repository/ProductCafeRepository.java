package com.github.windmill312.product.repository;

import com.github.windmill312.product.model.entity.ProductCafeEntity;
import com.github.windmill312.product.model.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductCafeRepository extends JpaRepository<ProductCafeEntity, Integer> {

    Page<ProductCafeEntity> findAllByCafeUid(UUID cafeUid, Pageable pageable);

    ProductCafeEntity findProductCafeEntityByCafeUidAndProduct(UUID cafeUid, ProductEntity product);

    void deleteByProductAndCafeUid(ProductEntity product, UUID cafeUid);

    void deleteAllByProduct(ProductEntity product);

    void deleteAllByCafeUid(UUID cafeUid);

}
