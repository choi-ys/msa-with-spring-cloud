package io.ecommerce.productgservice.error

/**
 * @author : choi-ys
 * @date : 2022-01-21 오전 12:41
 */
enum class ErrorCode(val status: Int, val code: String, val message: String) {
    // 400 : Client Error -> Error Code prefix = C*
    HTTP_MESSAGE_NOT_READABLE(400, "C001", "요청값을 확인 할 수 없습니다. 요청값을 확인해주세요."), METHOD_ARGUMENT_TYPE_MISMATCH(400,
        "C002",
        "요청값의 자료형이 잘못되었습니다. 요청값을 확인해주세요."),
    METHOD_ARGUMENT_NOT_VALID(400,
        "C003",
        "잘못된 요청입니다. 요청값을 확인해주세요."),  // 401 : Authentication Error -> Error Code prefix = A*
    BAD_CREDENTIALS(401, "A001", "잘못된 자격 증명입니다."), AUTHENTICATION_CREDENTIALS_NOT_FOUND(401,
        "A002",
        "자격 증명 정보를 찾을 수 없습니다."),  // 403 : Authorization Error -> Error Code prefix = A*
    UNAUTHORIZED(403, "A003", "유효한 자격 증명이 아닙니다."), ACCESS_DENIED(403,
        "A004",
        "요청에 필요한 권한이 부족합니다."),  // 404 : Client Error -> Error Code prefix = C*
    RESOURCE_NOT_FOUND(404, "C004", "요청에 해당하는 자원을 찾을 수 없습니다."),  // 405 : Client Error -> Error Code prefix = C*
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(405,
        "C005",
        "허용하지 않는 Http Method 요청입니다."),  // 406 : Client Error -> Error Code prefix = C*
    HTTP_MEDIA_TYPE_NOT_ACCEPTABLE(406,
        "C006",
        "지원하지 않는 Accept Type 입니다."),  // 415 : Client Error -> Error Code prefix = C*
    HTTP_MEDIA_TYPE_NOT_SUPPORTED(415,
        "C007",
        "지원하지 않는 Media Type 입니다."),  // 500 : Server Error -> Error Code prefix = S*
    SERVER_ERROR(500, "S001", "알 수 없는 오류가 발생하였습니다.");
}
