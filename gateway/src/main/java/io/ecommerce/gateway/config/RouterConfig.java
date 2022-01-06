package io.ecommerce.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : choi-ys
 * @date : 2022-01-06 오후 11:27
 * @apiNote : Java Code를 이용한 gateway forwarding router 설정
 */
@Configuration
public class RouterConfig {

    private static final String REQUEST_HEADER_NAME = "request-header-name";
    private static final String REQUEST_HEADER_VALUE = "request-header-value";
    private static final String RESPONSE_HEADER_NAME = "response-header-name";
    private static final String RESPONSE_HEADER_VALUE = "response-header-value";

    private static final String USER_SERVICE_URI = "http://localhost:9001";
    private static final String USER_SERVICE_PREDICATES_PATH = "/**";

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path(USER_SERVICE_PREDICATES_PATH)
                        .filters(f -> f.addRequestHeader(REQUEST_HEADER_NAME, REQUEST_HEADER_VALUE)
                                .addResponseHeader(RESPONSE_HEADER_NAME, RESPONSE_HEADER_VALUE)
                        )
                        .uri(USER_SERVICE_URI)
                )
                .build();
    }
}
