package eu.gitcode.core.data.controller

import android.content.Context
import eu.gitcode.core.domain.controller.FileController
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileControllerImpl
@Inject constructor(
    private val context: Context
) : FileController {

    override fun getStringFromFile(fileName: String): Single<String> {
        return Single.fromCallable {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        }
    }
}