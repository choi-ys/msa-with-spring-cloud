package io.ecommerce.userservice.config.docs;

import org.apache.http.protocol.HTTP;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.snippet.Snippet;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

/**
 * @author : choi-ys
 * @date : 2022/01/18 7:50 오후
 */
public interface RestDocsConfiguration {

    String HTTP = "http";
    String HTTPS = "https";
    String BASE_HOST = "ecommerce.io";
    String DEV_HOST = String.format("dev-user-service.%s", BASE_HOST);
    String STG_HOST = String.format("stg-user-service.%s", BASE_HOST);
    String PRD_HOST = String.format("prd-user-service.%s", BASE_HOST);

    static RestDocumentationResultHandler createDocument(Snippet... snippets) {
        return document("{class-name}/{method-name}",
                operationRequestPreprocessor(),
                operationResponsePreprocessor(),
                snippets
        );
    }

    private static OperationRequestPreprocessor operationRequestPreprocessor() {
        return preprocessRequest(
                modifyUris()
                        .scheme(HTTP)
                        .host(DEV_HOST)
                        .removePort(),
                prettyPrint());
    }

    private static OperationResponsePreprocessor operationResponsePreprocessor() {
        return preprocessResponse(
                modifyUris()
                        .scheme(HTTP)
                        .host(DEV_HOST)
                        .removePort(),
                prettyPrint());
    }
}
