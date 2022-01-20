package io.ecommerce.productgservice.error.exception

import io.ecommerce.productgservice.error.ErrorCode

/**
 * @author : choi-ys
 * @date : 2022-01-21 오전 12:42
 */
class ResourceNotFoundException : IllegalArgumentException(ErrorCode.RESOURCE_NOT_FOUND.message)