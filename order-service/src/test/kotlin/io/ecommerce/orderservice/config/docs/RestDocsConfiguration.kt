package io.ecommerce.orderservice.config.docs

import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.snippet.Attributes
import org.springframework.restdocs.snippet.Snippet

/**
 * @author : choi-ys
 * @date : 2022-01-21 오전 2:41
 */
interface RestDocsConfiguration {

    companion object {
        const val HTTP: String = "http"
        const val HTTPS: String = "https"
        const val BASE_HOST: String = "ecommerce.io"
        const val DEV_HOST: String = "dev-order-api.$BASE_HOST"
        const val STG_HOST: String = "stg-order-api.$BASE_HOST"
        const val PRD_HOST: String = "prd-order-api.$BASE_HOST"

        fun format(value: String): Attributes.Attribute = Attributes.Attribute("format", value)

        fun createDocument(vararg snippets: Snippet): RestDocumentationResultHandler {
            return MockMvcRestDocumentation.document("{class-name}/{method-name}",
                operationRequestPreprocessor(),
                operationResponsePreprocessor(),
                *snippets
            )
        }

        private fun operationRequestPreprocessor(): OperationRequestPreprocessor {
            return Preprocessors.preprocessRequest(
                Preprocessors.modifyUris()
                    .scheme(HTTP)
                    .host(DEV_HOST)
                    .removePort(),
                Preprocessors.prettyPrint()
            )
        }

        private fun operationResponsePreprocessor(): OperationResponsePreprocessor {
            return Preprocessors.preprocessResponse(
                Preprocessors.modifyUris()
                    .scheme(HTTP)
                    .host(DEV_HOST)
                    .removePort(),
                Preprocessors.prettyPrint()
            )
        }
    }
}