package io.ecommerce.gateway.filter;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author : choi-ys
 * @date : 2022-01-07 오전 2:01
 */
@Component
@Slf4j
public class CustomHttpLoggingFilter extends AbstractGatewayFilterFactory<CustomHttpLoggingFilter.Config> {

    public CustomHttpLoggingFilter() {
        super(Config.class);
    }

    @Getter
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;

        public Config(
                String baseMessage,
                boolean preLogger,
                boolean postLogger
        ) {
            this.baseMessage = baseMessage;
            this.preLogger = preLogger;
            this.postLogger = postLogger;
        }
    }

    /**
     * Ordered.HIGHEST_PRECEDENCE : Filter의 동작 우선 순위를 최우선으로 설정 -> GlobalFilter 보다 먼저 적용
     * Ordered.LOWEST_PRECEDENCE : Filter의 동작 우선 순위를 최하위로 설정 -> CustomFilter 보다 늦게 적용
     */
    @Override
    public GatewayFilter apply(Config config) {
        return new OrderedGatewayFilter((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            if (config.isPreLogger()) {
                log.info("[CustomHttpLoggingFilter][PRE][Request ID -> {}][Base Message -> {}]", request.getId(), config.getBaseMessage());
            }

            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        if (config.isPostLogger()) {
                            log.info("[CustomHttpLoggingFilter][POST][Request ID -> {}][Response Code -> {}]", request.getId(), response.getStatusCode());
                        }
                    }));
        }, Ordered.LOWEST_PRECEDENCE);
    }
}
