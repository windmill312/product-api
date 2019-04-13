package com.github.windmill312.product.repository;

import com.github.windmill312.product.model.entity.ProductCafeEntity;
import com.github.windmill312.product.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductCafeRepository extends JpaRepository<ProductCafeEntity, Integer> {

    List<ProductCafeEntity> findAllByCafeUid(UUID cafeUid);

    ProductCafeEntity findProductCafeEntityByCafeUidAndProduct(UUID cafeUid, ProductEntity product);

    void deleteByProductAndCafeUid(ProductEntity product, UUID cafeUid);

    void deleteAllByProduct(ProductEntity product);

    void deleteAllByCafeUid(UUID cafeUid);

}
