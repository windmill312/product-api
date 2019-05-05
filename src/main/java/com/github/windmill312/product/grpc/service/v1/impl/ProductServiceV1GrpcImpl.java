package com.github.windmill312.product.grpc.service.v1.impl;

import com.github.windmill312.common.grpc.model.Empty;
import com.github.windmill312.product.converter.ModelConverter;
import com.github.windmill312.product.grpc.model.v1.*;
import com.github.windmill312.product.grpc.service.v1.ProductServiceV1Grpc;
import com.github.windmill312.product.model.entity.ProductEntity;
import com.github.windmill312.product.service.ProductService;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.UUID;
import java.util.stream.Collectors;

@GRpcService
public class ProductServiceV1GrpcImpl extends ProductServiceV1Grpc.ProductServiceV1ImplBase {

    private final ProductService productService;

    @Autowired
    public ProductServiceV1GrpcImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void getAllProducts(
            GGetAllProductsRequest request,
            StreamObserver<GGetAllProductsResponse> responseObserver) {

        Page<ProductEntity> products = productService.getAllProducts(
                ModelConverter.convert(request.getPageable()));

        responseObserver.onNext(GGetAllProductsResponse.newBuilder()
                .setPage(ModelConverter.convert(products))
                .addAllProducts(products.getContent()
                        .stream()
                        .map(ModelConverter::convert)
                        .collect(Collectors.toList()))
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getProduct(
            GGetProductRequest request,
            StreamObserver<GGetProductResponse> responseObserver) {

        ProductEntity product = productService.getProductByUid(ModelConverter.convert(request.getProductUid()));

        responseObserver.onNext(GGetProductResponse.newBuilder()
                .setProduct(ModelConverter.convert(product))
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getProductsByCafe(
            GGetProductsByCafeRequest request,
            StreamObserver<GGetProductsByCafeResponse> responseObserver) {

        Page<ProductEntity> products = productService.getProductsByCafe(
                ModelConverter.convert(request.getCafeUid()),
                ModelConverter.convert(request.getPageable()));

        responseObserver.onNext(GGetProductsByCafeResponse.newBuilder()
                .setPage(ModelConverter.convert(products))
                .addAllProducts(products
                        .stream()
                        .map(ModelConverter::convert)
                        .collect(Collectors.toList()))
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void addProduct(
            GAddProductRequest request,
            StreamObserver<GAddProductResponse> responseObserver) {

        UUID productUid = productService.addProduct(ModelConverter.convert(request.getProduct()));

        responseObserver.onNext(GAddProductResponse.newBuilder()
                .setProductUid(ModelConverter.convert(productUid))
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateProduct(
            GUpdateProductRequest request,
            StreamObserver<Empty> responseObserver) {

        productService.updateProduct(
                ModelConverter.convert(request.getProduct())
                        .setProductUid(ModelConverter.convert(request.getProduct().getProductUid())));

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void linkProductAndCafe(
            GLinkProductAndCafeRequest request,
            StreamObserver<Empty> responseObserver) {

        productService.linkCafeAndProduct(ModelConverter.convert(request.getCafeUid()), ModelConverter.convert(request.getProductUid()));

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void unlinkProductAndCafe(
            GUnlinkProductAndCafeRequest request,
            StreamObserver<Empty> responseObserver) {

        productService.unlinkCafeAndProduct(ModelConverter.convert(request.getCafeUid()), ModelConverter.convert(request.getProductUid()));

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void removeProduct(
            GRemoveProductRequest request,
            StreamObserver<Empty> responseObserver) {

        productService.removeProduct(ModelConverter.convert(request.getProductUid()));

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void removeProductsByCafe(
            GRemoveProductsByCafeRequest request,
            StreamObserver<Empty> responseObserver) {

        productService.removeAllProductsByCafe(ModelConverter.convert(request.getCafeUid()));

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

}
