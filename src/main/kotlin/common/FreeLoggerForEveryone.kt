package common

import mu.KLogger
import mu.KotlinLogging
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObject

fun <R : Any> R.logger(): Lazy<KLogger> {
    return lazy { KotlinLogging.logger(unwrapCompanionClass(this.javaClass).name) }
}

fun <T : Any> unwrapCompanionClass(ofClass: Class<T>): Class<*> {
    return ofClass.enclosingClass?.takeIf {
        ofClass.enclosingClass.kotlin.companionObject?.java == ofClass
    } ?: ofClass
}

fun <T: Any> unwrapCompanionClass(ofClass: KClass<T>): KClass<*> {
    return unwrapCompanionClass(ofClass.java).kotlin
}