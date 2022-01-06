package io.ecommerce.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author : choi-ys
 * @date : 2022-01-07 오전 12:48
 */
@Component
@Slf4j
public class DefaultHttpLoggingFilter extends AbstractGatewayFilterFactory<DefaultHttpLoggingFilter.Config> {

    public DefaultHttpLoggingFilter() {
        super(Config.class);
    }

    public static class Config {

    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("[CustomFilter][PRE][Request ID -> {}]", request.getId());

            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        log.info("[CustomFilter][POST][Request ID -> {}][Response Code -> {}]", request.getId(), response.getStatusCode());
                    }));
        };
    }
}
