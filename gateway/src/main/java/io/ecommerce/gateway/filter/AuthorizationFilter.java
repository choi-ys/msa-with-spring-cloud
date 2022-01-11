package io.ecommerce.gateway.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author : choi-ys
 * @date : 2022/01/10 8:31 오후
 */
@Component
@Slf4j
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {
    @Value("${token.signature}")
    private String SIGNATURE;

    private static final String BEARER = "Bearer";

    public AuthorizationFilter() {
        super(Config.class);
    }

    public static class Config {

    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            String token = resolve(exchange);
            isValidToken(exchange, token);

            return chain.filter(exchange);
        };
    }

    public String resolve(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String bearerToken = null;
        try {
            bearerToken = request
                    .getHeaders()
                    .get(HttpHeaders.AUTHORIZATION)
                    .get(0);
        } catch (Exception exception) {
            onError(exchange, "No authorization Header", HttpStatus.UNAUTHORIZED);
        }

        if (!StringUtils.hasText(bearerToken)) {
            onError(exchange, "No authorization Header", HttpStatus.UNAUTHORIZED);
        }

        boolean isBearerToken = bearerToken.startsWith(BEARER + " ");
        if (!isBearerToken) {
            onError(exchange, "Not Bearer type Header", HttpStatus.UNAUTHORIZED);
        }

        return bearerToken.substring(7);
    }


    private void isValidToken(ServerWebExchange exchange, String token) {
        Algorithm ALGORITHM = Algorithm.HMAC512(SIGNATURE);

        try {
            JWT.require(ALGORITHM).build().verify(token);
        } catch (TokenExpiredException e) {
            onError(exchange, "Token is expired", HttpStatus.UNAUTHORIZED);
        } catch (JWTDecodeException e) {
            onError(exchange, "Invalid format", HttpStatus.UNAUTHORIZED);
        } catch (SignatureVerificationException e) {
            onError(exchange, "Invalid signature", HttpStatus.UNAUTHORIZED);
        }
    }

    private Mono<Void> onError(
            ServerWebExchange exchange,
            String message,
            HttpStatus httpStatus
    ) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        log.error("[{}][{}][{}]", exchange.getRequest().getId(), httpStatus, message);
        return response.setComplete();
    }
}
