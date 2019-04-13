package com.github.windmill312.product.service.v1.impl;

import com.github.windmill312.common.grpc.model.GPageable;
import com.github.windmill312.common.grpc.model.GUuid;
import com.github.windmill312.product.converter.ModelConverter;
import com.github.windmill312.product.grpc.model.v1.*;
import com.github.windmill312.product.grpc.service.v1.impl.ProductServiceV1GrpcImpl;
import com.github.windmill312.product.model.ProductGroup;
import com.github.windmill312.product.model.entity.ProductEntity;
import com.github.windmill312.product.service.ProductService;
import io.grpc.stub.StreamObserver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceV1GrpcImplTest {

    private static final UUID PRODUCT_UID = UUID.fromString("517df602-4ffb-4e08-9626-2a0cf2db4849");
    private static final String NAME = "Капучино";
    private String DESCRIPTION = "Классический кофе с молоком";
    private Double PRICE = 100.0;
    private ProductGroup GROUP = ProductGroup.BEVERAGES;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductServiceV1GrpcImpl productServiceV1Grpc;

    @Test
    public void getAllProducts() {

        Page<ProductEntity> serviceResponse = new PageImpl<>(Collections.singletonList(getMockObjСafeEntity()));

        when(productService.getAllProducts(any(Pageable.class))).thenReturn(serviceResponse);

        GGetAllProductsRequest request = GGetAllProductsRequest.newBuilder()
                .setPageable(GPageable.newBuilder().setPage(0).setSize(20).build())
                .build();

        StreamObserver<GGetAllProductsResponse> observer = mock(StreamObserver.class);

        productServiceV1Grpc.getAllProducts(request, observer);
        verify(observer, times(1)).onCompleted();

        ArgumentCaptor<GGetAllProductsResponse> captor = ArgumentCaptor.forClass(GGetAllProductsResponse.class);
        verify(observer, times(1)).onNext(captor.capture());

        GGetAllProductsResponse response = captor.getValue();
        assertEquals(response.getProductsList().size(), serviceResponse.getTotalElements());
        assertEquals(ModelConverter.convert(response.getProductsList().get(0).getProductUid()), serviceResponse.getContent().get(0).getProductUid());
        assertEquals(response.getProductsList().get(0).getDescription(), serviceResponse.getContent().get(0).getDescription());
        assertEquals(response.getProductsList().get(0).getGroup().name(), serviceResponse.getContent().get(0).getProductGroup().name());
        assertEquals(response.getProductsList().get(0).getName(), serviceResponse.getContent().get(0).getName());
        assertEquals(response.getProductsList().get(0).getPrice(), serviceResponse.getContent().get(0).getPrice().doubleValue(), 0.1);
    }

    @Test
    public void getProduct() {

        ProductEntity serviceResponse = getMockObjСafeEntity();

        when(productService.getProductByUid(any(UUID.class))).thenReturn(serviceResponse);

        GGetProductRequest request = GGetProductRequest.newBuilder()
                .setProductUid(GUuid.newBuilder().setUuid(PRODUCT_UID.toString()).build())
                .build();

        StreamObserver<GGetProductResponse> observer = mock(StreamObserver.class);

        productServiceV1Grpc.getProduct(request, observer);
        verify(observer, times(1)).onCompleted();

        ArgumentCaptor<GGetProductResponse> captor = ArgumentCaptor.forClass(GGetProductResponse.class);
        verify(observer, times(1)).onNext(captor.capture());

        GGetProductResponse response = captor.getValue();
        assertEquals(ModelConverter.convert(response.getProduct().getProductUid()), serviceResponse.getProductUid());
        assertEquals(response.getProduct().getDescription(), serviceResponse.getDescription());
        assertEquals(response.getProduct().getName(), serviceResponse.getName());
        assertEquals(response.getProduct().getPrice(), serviceResponse.getPrice(), 0.1);
        assertEquals(response.getProduct().getGroup().name(), serviceResponse.getProductGroup().name());

    }

    @Test
    public void addProduct() {

        when(productService.addProduct(any(ProductEntity.class))).thenReturn(PRODUCT_UID);

        GAddProductRequest request = GAddProductRequest.newBuilder()
                .setProduct(GProductInfo.newBuilder()
                        .setName(NAME)
                        .setProductUid(ModelConverter.convert(PRODUCT_UID))
                        .setDescription(DESCRIPTION)
                        .setPrice(PRICE)
                        .build())
                .build();

        StreamObserver<GAddProductResponse> observer = mock(StreamObserver.class);

        productServiceV1Grpc.addProduct(request, observer);
        verify(observer, times(1)).onCompleted();

        ArgumentCaptor<GAddProductResponse> captor = ArgumentCaptor.forClass(GAddProductResponse.class);
        verify(observer, times(1)).onNext(captor.capture());

        GAddProductResponse response = captor.getValue();
        assertEquals(response.getProductUid().getUuid(), PRODUCT_UID.toString());

    }

    private ProductEntity getMockObjСafeEntity() {
        return new ProductEntity()
                .setPrice(PRICE)
                .setProductGroup(GROUP)
                .setProductUid(PRODUCT_UID)
                .setName(NAME)
                .setDescription(DESCRIPTION);
    }
    
}


