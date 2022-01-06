package io.ecommerce.gateway.filter;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author : choi-ys
 * @date : 2022-01-07 오전 1:08
 */
@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    public GlobalFilter() {
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

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            if (config.isPreLogger()) {
                log.info("[GlobalFilter][PRE][Request ID -> {}][Base Message -> {}]", request.getId(), config.getBaseMessage());
            }

            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        if (config.isPostLogger()) {
                            log.info("[GlobalFilter][POST][Request ID -> {}][Response Code -> {}]", request.getId(), response.getStatusCode());
                        }
                    }));
        };
    }
}
