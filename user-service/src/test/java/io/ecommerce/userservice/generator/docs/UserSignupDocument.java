package io.ecommerce.userservice.generator.docs;

import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

import static io.ecommerce.userservice.config.docs.ApiDocumentUtils.createDocument;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

/**
 * @author : choi-ys
 * @date : 2022/01/18 7:54 오후
 */
public class UserSignupDocument {
    public static RestDocumentationResultHandler signupUserDocument() {
        return createDocument(
                requestHeaders(
                        headerWithName(HttpHeaders.ACCEPT).description("accept type header"),
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                ),
                requestFields(
                        fieldWithPath("email").description("이메일"),
                        fieldWithPath("password").description("비밀번호"),
                        fieldWithPath("name").description("이름")
                ),
                responseHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("Response content type")
                ),
                responseFields(
                        fieldWithPath("userId").description("전페 페이지 수"),
                        fieldWithPath("email").description("전체 요소 수"),
                        fieldWithPath("name").description("현제 페이지 번호")
                )
        );
    }
}
