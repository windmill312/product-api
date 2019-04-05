package com.sychev.product.repository;

import com.sychev.product.model.entity.ProductCafeEntity;
import com.sychev.product.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductCafeRepository extends JpaRepository<ProductCafeEntity, Integer> {

    @Query(value = "select product from supplies.product_cafe where cafeUid = ?1", nativeQuery = true)
    List<ProductEntity> findProductsByCafeUid(UUID cafeUid);

    ProductCafeEntity findProductCafeEntityByCafeUidAndProductUid(UUID cafeUid, UUID productUid);

    void deleteByProductUidAndCafeUid(UUID productUid, UUID cafeUid);

    void deleteAllByProductUid(UUID productUid);

    void deleteAllByCafeUid(UUID cafeUid);

}
