package io.ecommerce.orderservice.config.p6spy

import com.p6spy.engine.spy.P6SpyOptions
import javax.annotation.PostConstruct

/**
 * @author : choi-ys
 * @date : 2022/01/09 3:29 오후
 */
class P6spyLogMessageFormatConfiguration {
    @PostConstruct
    fun setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().logMessageFormat = P6spySqlFormatConfiguration::class.java.name
    }
}