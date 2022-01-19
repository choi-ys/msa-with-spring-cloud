package io.ecommerce.userservice.config.docs;

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
public interface ApiDocumentUtils {

    final String HTTP = "http";
    final String HTTPS = "https";
    final String DEV_HOST = "dev-user-service.ecommerce.io";
    final String STG_HOST = "stg-user-service.ecommerce.io";
    final String PRD_HOST = "prd-user-service.ecommerce.io";

    static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(
                modifyUris()
                        .scheme(HTTP)
                        .host(DEV_HOST)
                        .removePort(),
                prettyPrint());
    }

    static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(
                modifyUris()
                        .scheme(HTTP)
                        .host(DEV_HOST)
                        .removePort(),
                prettyPrint());
    }

    static RestDocumentationResultHandler createDocument(Snippet... snippets) {
        return document("{class-name}/{method-name}",
                getDocumentRequest(),
                getDocumentResponse(),
                snippets
        );
    }
}
