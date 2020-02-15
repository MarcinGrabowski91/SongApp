package eu.gitcode.songapp.app

import io.reactivex.exceptions.UndeliverableException
import io.reactivex.functions.Consumer
import timber.log.Timber

/**
 * [RxJava2 error handling](https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0#error-handling)
 */
class RxJavaErrorHandler : Consumer<Throwable> {

    override fun accept(throwable: Throwable) =
        when (throwable) {
            is UndeliverableException -> {
                // We log such exceptions but avoid app crash for release as we can't do much in such case.
                Timber.e(throwable)
            }
            else -> {
                // We crash the app else - this is a bug
                throwable.printStackTrace()
                uncaught(throwable)
            }
        }

    private fun uncaught(throwable: Throwable) {
        val currentThread = Thread.currentThread()
        val handler = currentThread.uncaughtExceptionHandler
        handler?.uncaughtException(currentThread, throwable)
    }
}
