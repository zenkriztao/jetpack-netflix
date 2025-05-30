package com.zenkriztao.netflix.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
val testAppDispatchers = AppDispatchers(
    main = UnconfinedTestDispatcher(),
    default = UnconfinedTestDispatcher(),
)
