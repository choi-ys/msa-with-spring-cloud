package io.ecommerce.productgservice.generator.docs

import io.ecommerce.productgservice.config.docs.RestDocsConfiguration.Companion.createDocument
import org.springframework.http.HttpHeaders
import org.springframework.restdocs.headers.HeaderDocumentation.*
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters

/**
 * @author : choi-ys
 * @date : 2022-01-20 오전 12:48
 */
class ProductQueryDocument {
    companion object {
        fun getProductDocument(): RestDocumentationResultHandler {
            return createDocument(
                requestHeaders(
                    headerWithName(HttpHeaders.ACCEPT).description("accept type header"),
                    headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                ),
                pathParameters(
                    parameterWithName("id").description("상품 ID")
                ),
                responseHeaders(
                    headerWithName(HttpHeaders.CONTENT_TYPE).description("Response content type")
                ),
                responseFields(
                    fieldWithPath("productId").description("상품 ID"),
                    fieldWithPath("name").description("상품명"),
                    fieldWithPath("productCode").description("상품 코드"),
                    fieldWithPath("price").description("상품 가격"),
                    fieldWithPath("stock").description("상품 재고")
                )
            )
        }

        fun getProductFailDocument(): RestDocumentationResultHandler {
            return createDocument(
                requestHeaders(
                    headerWithName(HttpHeaders.ACCEPT).description("accept type header"),
                    headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                ),
                pathParameters(
                    parameterWithName("id").description("상품 ID")
                ),
                responseHeaders(
                    headerWithName(HttpHeaders.CONTENT_TYPE).description("Response content type")
                ),
                responseFields(
                    fieldWithPath("timestamp").description("오류 일시"),
                    fieldWithPath("code").description("오류 코드"),
                    fieldWithPath("message").description("오류 사유"),
                    fieldWithPath("method").description("오류 HTTP Method"),
                    fieldWithPath("path").description("요청 Request URI"),
                    fieldWithPath("errorDetails").description("오류 항목 상세 배열")
                )
            )
        }

        fun getProductListDocument(): RestDocumentationResultHandler {
            return createDocument(
                requestHeaders(
                    headerWithName(HttpHeaders.ACCEPT).description("accept type header"),
                    headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                ),
                RequestDocumentation.requestParameters(
                    parameterWithName("page").description("요청 페이지 번호"),
                    parameterWithName("size").description("페이지당 항목 수"),
                    parameterWithName("sort").description("정렬 기준 : 항목"),
                    parameterWithName("direction").description("정렬 기준 : 순서")
                ),
                responseHeaders(
                    headerWithName(HttpHeaders.CONTENT_TYPE).description("Response content type")
                ),
                responseFields(
                    fieldWithPath("totalPage").description("전페 페이지 수"),
                    fieldWithPath("totalElementCount").description("전체 요소 수"),
                    fieldWithPath("currentPage").description("현제 페이지 번호"),
                    fieldWithPath("currentPageElementCount").description("현재 페이지의 요수 수"),
                    fieldWithPath("perPageNumber").description("페이지당 요소 수"),
                    fieldWithPath("firstPage").description("첫 페이지 여부"),
                    fieldWithPath("lastPage").description("마지막 페이지 여부"),
                    fieldWithPath("hasNextPage").description("다음 페이지 존재 여부"),
                    fieldWithPath("hasPrevious").description("이전 페이지 존재 여부"),
                    fieldWithPath("embedded").description("응답 본문 배열"),
                    fieldWithPath("embedded[*].id").description("상품 ID"),
                    fieldWithPath("embedded[*].name").description("상품명"),
                    fieldWithPath("embedded[*].productCode").description("상품 코드"),
                    fieldWithPath("embedded[*].price").description("상품 가격"),
                    fieldWithPath("embedded[*].stock").description("상품 재고"),
                    fieldWithPath("embedded[*].createdAt").description("생성일시"),
                    fieldWithPath("embedded[*].updatedAt").description("수정일시"),
                )
            )
        }
    }
}