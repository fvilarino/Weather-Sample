package com.francescsoftware.weathersample.testing

import com.francescsoftware.weathersample.dispather.DispatcherProvider
import com.francescsoftware.weathersample.dispather.DispatcherProviderInstance
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

val testDispatcherProvider = object : DispatcherProvider {
    private var dispatcher: CoroutineDispatcher? = null

    override val main: CoroutineDispatcher
        get() = dispatcher ?: Dispatchers.Main
    override val io: CoroutineDispatcher
        get() = dispatcher ?: Dispatchers.Main
    override val default: CoroutineDispatcher
        get() = dispatcher ?: Dispatchers.Main
    override val unconfined: CoroutineDispatcher
        get() = dispatcher ?: Dispatchers.Main

    override fun setDispatcher(dispatcher: CoroutineDispatcher?) {
        this.dispatcher = dispatcher
    }
}

@ExperimentalCoroutinesApi
fun MainCoroutineRule.runBlockingTest(block: suspend () -> Unit) {
    testDispatcher.runBlockingTest { block() }
}

@ExperimentalCoroutinesApi
class MainCoroutineRule(
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
        DispatcherProviderInstance.setDispatcher(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
        DispatcherProviderInstance.setDispatcher(null)
    }
}
