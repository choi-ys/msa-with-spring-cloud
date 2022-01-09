package io.ecommerce.orderservice.config.test

import com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorAutoConfiguration
import io.ecommerce.orderservice.config.p6spy.P6spyLogMessageFormatConfiguration
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor
import java.lang.annotation.*
import java.lang.annotation.Retention
import java.lang.annotation.Target

/**
 * @author : choi-ys
 * @date : 2022/01/09 3:30 오후
 */
@DataJpaTest(showSql = false)
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ImportAutoConfiguration(DataSourceDecoratorAutoConfiguration::class)
@Import(P6spyLogMessageFormatConfiguration::class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
annotation class DataJpaTestSupporter {
}