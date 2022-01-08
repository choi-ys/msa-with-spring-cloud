package io.ecommerce.catalogservice.core.repository

import io.ecommerce.catalogservice.core.domain.Catalog
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author : choi-ys
 * @date : 2022/01/08 7:15 오후
 */
interface CatalogRepo : JpaRepository<Catalog, Long> {
}