package io.ecommerce.userservice.generator.docs;

import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

import static io.ecommerce.userservice.config.docs.RestDocsConfiguration.createDocument;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;

/**
 * @author : choi-ys
 * @date : 2022/01/18 7:54 오후
 */
public class UserQueryDocument {
    public static RestDocumentationResultHandler searchUserDocument() {
        return createDocument(
                requestHeaders(
                        headerWithName(HttpHeaders.ACCEPT).description("accept type header"),
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                ),
                requestParameters(
                        parameterWithName("email").description("이메일"),
                        parameterWithName("name").description("이름"),
                        parameterWithName("page").description("요청 페이지 번호"),
                        parameterWithName("size").description("페이지당 항목 수"),
                        parameterWithName("sort").description("정렬 기준 : 항목"),
                        parameterWithName("direction").description("정렬 기준 : 순서")
                ),
                responseHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("Response content type")
                ),
                responseFields(
                        fieldWithPath("totalPages").description("전페 페이지 수"),
                        fieldWithPath("totalElementCount").description("전체 요소 수"),
                        fieldWithPath("currentPage").description("현제 페이지 번호"),
                        fieldWithPath("currentElementCount").description("현재 페이지의 요수 수"),
                        fieldWithPath("perPageNumber").description("페이지당 요소 수"),
                        fieldWithPath("firstPage").description("첫 페이지 여부"),
                        fieldWithPath("lastPage").description("마지막 페이지 여부"),
                        fieldWithPath("hasNextPage").description("다음 페이지 존재 여부"),
                        fieldWithPath("hasPrevious").description("이전 페이지 존재 여부"),
                        fieldWithPath("embedded").description("응답 본문 배열"),
                        fieldWithPath("embedded[0].userId").description("사용자 ID"),
                        fieldWithPath("embedded[0].email").description("이메일"),
                        fieldWithPath("embedded[0].name").description("이름")
                )
        );
    }

    public static RestDocumentationResultHandler getUserDocument() {
        return createDocument(
                requestHeaders(
                        headerWithName(HttpHeaders.ACCEPT).description("accept type header"),
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                ),
                pathParameters(
                        parameterWithName("id").description("사용자 ID")
                ),
                responseHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("Response content type")
                ),
                responseFields(
                        fieldWithPath("userId").description("사용자 ID"),
                        fieldWithPath("email").description("이메일"),
                        fieldWithPath("name").description("이름"),
                        fieldWithPath("orderList[0].id").description("주문 번호"),
                        fieldWithPath("orderList[0].userId").description("사용자 ID"),
                        fieldWithPath("orderList[0].productId").description("상품 ID"),
                        fieldWithPath("orderList[0].quantity").description("주문 수량"),
                        fieldWithPath("orderList[0].unitPrice").description("상품 가격"),
                        fieldWithPath("orderList[0].totalPrice").description("전체 주문 금액"),
                        fieldWithPath("orderList[0].createdAt").description("주문일시")
                )
        );
    }
}
