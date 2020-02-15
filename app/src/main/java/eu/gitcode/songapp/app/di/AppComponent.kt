package eu.gitcode.songapp.app.di

import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import eu.gitcode.core.di.CoreComponent
import eu.gitcode.core.di.scope.AppScope
import eu.gitcode.songapp.app.App

@AppScope
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class
    ],
    dependencies = [CoreComponent::class]
)

interface AppComponent {

    fun inject(app: App)
}