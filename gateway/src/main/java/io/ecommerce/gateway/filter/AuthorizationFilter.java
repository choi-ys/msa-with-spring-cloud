package io.ecommerce.gateway.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import io.ecommerce.gateway.exception.UnAuthorizedException;
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

            try {
                String token = resolve(exchange);
                isValidToken(token);
            } catch (UnAuthorizedException unAuthorizedException) {
                return onError(exchange, unAuthorizedException);
            }

            return chain.filter(exchange);
        };
    }

    private String resolve(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();

        if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
            throw new UnAuthorizedException("No authorization Header");
        }

        String bearerToken = headers.get(HttpHeaders.AUTHORIZATION).get(0);
        if (!StringUtils.hasText(bearerToken)) {
            throw new UnAuthorizedException("No authorization Header");
        }

        boolean isBearerToken = bearerToken.startsWith(BEARER + " ");
        if (!isBearerToken) {
            throw new UnAuthorizedException("Not Bearer type Header");
        }

        return bearerToken.substring(7);
    }

    private void isValidToken(String token) {
        Algorithm ALGORITHM = Algorithm.HMAC512(SIGNATURE);

        try {
            JWT.require(ALGORITHM).build().verify(token);
        } catch (TokenExpiredException e) {
            throw new UnAuthorizedException("Token is expired");
        } catch (JWTDecodeException e) {
            throw new UnAuthorizedException("Invalid format");
        } catch (SignatureVerificationException e) {
            throw new UnAuthorizedException("Invalid signature");
        }
    }

    private Mono<Void> onError(
            ServerWebExchange exchange,
            UnAuthorizedException unAuthorizedException
    ) {
        ServerHttpResponse response = exchange.getResponse();
        HttpStatus httpStatus = unAuthorizedException.getHttpStatus();
        response.setStatusCode(httpStatus);

        log.error("[{}][{}][{}]", exchange.getRequest().getId(), httpStatus, unAuthorizedException.getMessage());
        return response.setComplete();
    }
}
