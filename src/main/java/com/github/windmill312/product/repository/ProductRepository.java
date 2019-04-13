package com.github.windmill312.product.repository;

import com.github.windmill312.product.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    Optional<ProductEntity> findByProductUid(UUID productUid);

    Boolean existsByProductUid(UUID productUid);

    void deleteByProductUid(UUID productUid);

}
