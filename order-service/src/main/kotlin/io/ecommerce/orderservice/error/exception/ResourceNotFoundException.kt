package io.ecommerce.orderservice.error.exception

import io.ecommerce.orderservice.error.ErrorCode

/**
 * @author : choi-ys
 * @date : 2022/01/09 9:02 오후
 */
class ResourceNotFoundException : IllegalArgumentException(ErrorCode.RESOURCE_NOT_FOUND.message)