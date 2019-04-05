package com.sychev.product.service.impl;

import com.sychev.product.exception.NotFoundProductException;
import com.sychev.product.model.entity.ProductCafeEntity;
import com.sychev.product.model.entity.ProductEntity;
import com.sychev.product.repository.ProductCafeRepository;
import com.sychev.product.repository.ProductRepository;
import com.sychev.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    private final ProductCafeRepository productCafeRepository;

    public ProductServiceImpl(
            ProductRepository productRepository,
            ProductCafeRepository productCafeRepository) {
        this.productRepository = productRepository;
        this.productCafeRepository = productCafeRepository;
    }

    @Override
    public Page<ProductEntity> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public List<ProductEntity> getProductsByCafe(UUID cafeUid) {
        return productCafeRepository.findProductsByCafeUid(cafeUid);
    }

    @Override
    public ProductEntity getProductByUid(UUID productUid) {
        return productRepository.findByProductUid(productUid)
                .orElseThrow(() -> {
                    logger.info("Not found product with uid={}", productUid);
                    return new NotFoundProductException("Not found product with uid=" + productUid);
                });
    }

    @Override
    public UUID addProduct(ProductEntity entity) {
        logger.debug("Add new product with name={} for cafe={}", entity.getName());
        return productRepository.save(entity).getProductUid();
    }

    @Override
    public void updateProduct(ProductEntity entity) {
        logger.debug("Update product with name={} for cafe={}", entity.getName());

        ProductEntity product = productRepository.findByProductUid(entity.getProductUid()).orElseThrow(() -> {
            logger.info("Not found product with uid={}", entity.getProductUid());
            return new NotFoundProductException("Not found product with uid=" + entity.getProductUid());
        });

        productRepository.save(product.copy(entity));
    }

    @Override
    public void linkCafeAndProduct(UUID cafeUid, UUID productUid) {
        ProductEntity product = productRepository.findByProductUid(productUid).orElseThrow(() -> {
            logger.info("Not found product with uid={}", productUid);
            return new NotFoundProductException("Not found product with uid=" + productUid);
        });

        ProductCafeEntity productCafe = new ProductCafeEntity()
                .setCafeUid(cafeUid)
                .setProductUid(productUid)
                .setProduct(product);

        productCafeRepository.save(productCafe);
    }

    @Override
    @Transactional
    public void unlinkCafeAndProduct(UUID cafeUid, UUID productUid) {
        productCafeRepository.deleteByProductUidAndCafeUid(productUid, cafeUid);
    }

    //todo разобраться как именно работает Transactional
    @Override
    @Transactional
    public void removeProduct(UUID productUid) {
        productCafeRepository.deleteAllByProductUid(productUid);
        productRepository.deleteByProductUid(productUid);
    }

    @Override
    @Transactional
    public void removeAllProductsByCafe(UUID cafeUid) {
        productCafeRepository.deleteAllByCafeUid(cafeUid);
    }
}
