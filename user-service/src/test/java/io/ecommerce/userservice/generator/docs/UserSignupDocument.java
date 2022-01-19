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
    public static RestDocumentationResultHandler signupDocument() {
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
                        fieldWithPath("userId").description("사용자 ID"),
                        fieldWithPath("email").description("이메일"),
                        fieldWithPath("name").description("이름")
                )
        );
    }

    public static RestDocumentationResultHandler signupFailDocument() {
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
                        fieldWithPath("timestamp").description("오류 일시"),
                        fieldWithPath("code").description("오류 코드"),
                        fieldWithPath("message").description("오류 사유"),
                        fieldWithPath("method").description("요청 HTTP Method"),
                        fieldWithPath("path").description("요청 Request URI"),
                        fieldWithPath("errorDetails").description("오류 항목 상세 배열"),
                        fieldWithPath("errorDetails[0].object").description("요청 객체명"),
                        fieldWithPath("errorDetails[0].field").description("오류 발생 항목명"),
                        fieldWithPath("errorDetails[0].code").description("오류 상세 코드"),
                        fieldWithPath("errorDetails[0].rejectMessage").description("오류 상세 사유"),
                        fieldWithPath("errorDetails[0].rejectedValue").description("오류 발생 항목 값")
                )
        );
    }
}
