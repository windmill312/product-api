package com.sychev.product.grpc.service;

import com.sychev.product.exception.NotFoundProductException;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GRpcServiceAdvice {

    @Around(value = "@within(gRpcService)", argNames = "pjp, gRpcService")
    public Object anyServiceMethod(
            ProceedingJoinPoint pjp,
            GRpcService gRpcService)
            throws Throwable {
        try {
            return pjp.proceed(pjp.getArgs());
        } catch (Exception e) {
            return onError((StreamObserver<?>) pjp.getArgs()[1], e);
        }
    }

    private StreamObserver<?> onError(StreamObserver<?> responseObserver, Exception e) {
        Status status;

        if (e instanceof IllegalArgumentException) {
            status = Status.INVALID_ARGUMENT;
        } else if (e instanceof NotFoundProductException) {
            status = Status.NOT_FOUND;
        } else {
            status = Status.INTERNAL;
        }

        responseObserver.onError(status
                .withDescription(e.getMessage())
                .asRuntimeException());

        return responseObserver;
    }
}
