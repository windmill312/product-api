package com.sychev.product.converter;

import com.sychev.common.grpc.model.GPage;
import com.sychev.common.grpc.model.GPageable;
import com.sychev.common.grpc.model.GUuid;
import com.sychev.product.grpc.model.v1.GProductInfo;
import com.sychev.product.model.ProductGroup;
import com.sychev.product.model.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public class ModelConverter {

    public static GPage convert(Page page) {
        return GPage.newBuilder()
                .setNumber(page.getNumber())
                .setSize(page.getSize())
                .setTotalElements(page.getTotalElements())
                .build();
    }

    public static Pageable convert(GPageable pageable) {
        return PageRequest.of(pageable.getPage(), pageable.getSize());
    }

    public static GUuid convert(UUID uuid) {
        return GUuid.newBuilder()
                .setUuid(String.valueOf(uuid))
                .build();
    }

    public static GProductInfo convert(ProductEntity entity) {
        return GProductInfo.newBuilder()
                .setName(entity.getName())
                .setDescription(entity.getDescription())
                .setPrice(entity.getPrice())
                .setProductUid(convert(entity.getProductUid()))
                .setGroup(toGProductGroup(entity.getProductGroup()))
                .build();
    }

    private static GProductInfo.GProductGroup toGProductGroup(ProductGroup productGroup) {
        return GProductInfo.GProductGroup.valueOf(productGroup.name());
    }

    public static ProductEntity convert(GProductInfo entity) {
        return new ProductEntity()
                .setName(entity.getName())
                .setDescription(entity.getDescription())
                .setPrice(entity.getPrice())
                .setProductGroup(toProductGroup(entity.getGroup()));
    }

    private static ProductGroup toProductGroup(GProductInfo.GProductGroup group) {
        return ProductGroup.valueOf(group.name());
    }

    public static UUID convert(GUuid guuid) {
        return UUID.fromString(guuid.getUuid());
    }
}
