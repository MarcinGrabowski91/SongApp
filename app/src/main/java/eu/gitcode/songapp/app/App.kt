package eu.gitcode.songapp.app

import android.app.Application
import android.content.Context
import eu.gitcode.core.di.ContextModule
import eu.gitcode.core.di.CoreComponent
import eu.gitcode.core.di.DaggerCoreComponent
import eu.gitcode.songapp.app.di.DaggerAppComponent
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber

class App : Application() {

    lateinit var coreComponent: CoreComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
        RxJavaPlugins.setErrorHandler(RxJavaErrorHandler())
        Timber.plant(Timber.DebugTree())
    }

    private fun initDagger() {
        initCoreComponent()
        initAppComponent()
    }

    private fun initAppComponent() {
        DaggerAppComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)
    }

    private fun initCoreComponent() {
        coreComponent = DaggerCoreComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()
    }

    companion object {

        @JvmStatic
        fun coreComponent(context: Context) =
            (context.applicationContext as? App)?.coreComponent
    }
}