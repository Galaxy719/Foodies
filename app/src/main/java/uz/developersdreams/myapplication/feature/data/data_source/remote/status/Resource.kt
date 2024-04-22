package uz.developersdreams.myapplication.feature.data.data_source.remote.status

import uz.developersdreams.myapplication.core.util.UiText

data class Resource<out T>(val status: Status, val data: T?, val message: UiText?) {

    companion object {

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: UiText?): Resource<T> {
            return Resource(Status.ERROR, null, message)
        }
    }
}