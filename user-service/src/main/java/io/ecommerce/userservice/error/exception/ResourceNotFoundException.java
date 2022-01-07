package io.ecommerce.userservice.error.exception;

import io.ecommerce.userservice.error.ErrorCode;

public class ResourceNotFoundException extends IllegalArgumentException {
    public ResourceNotFoundException() {
        super(ErrorCode.RESOURCE_NOT_FOUND.getMessage());
    }
}
