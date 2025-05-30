package com.zenkriztao.netflix.util

interface Mapper<Input, Output> {
    fun map(input: Input): Output
}
