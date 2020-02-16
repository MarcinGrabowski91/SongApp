package eu.gitcode.core.domain.controller

import io.reactivex.Single

interface FileController {
    fun getStringFromFile(fileName: String): Single<String>
}