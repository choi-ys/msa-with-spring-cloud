package io.ecommerce.productgservice.config.docs

import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.snippet.Attributes.Attribute
import org.springframework.restdocs.snippet.Snippet

/**
 * @author : choi-ys
 * @date : 2021-10-17 오전 4:18
 */
interface RestDocsConfiguration {

    companion object {
        const val HTTP: String = "http"
        const val HTTPS: String = "https"
        const val BASE_HOST: String = "whypie.me"
        const val DEV_HOST: String = "dev-project-api.$BASE_HOST"
        const val STG_HOST: String = "stg-project-api.$BASE_HOST"
        const val PRD_HOST: String = "prd-project-api.$BASE_HOST"

        fun format(value: String): Attribute = Attribute("format", value)

        fun createDocument(vararg snippets: Snippet): RestDocumentationResultHandler {
            return document("{class-name}/{method-name}",
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