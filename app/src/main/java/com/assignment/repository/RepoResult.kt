package com.assignment.repository

/**
 *
 */
data class RepoResult<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR
    }

    companion object {
        fun <T> success(data: T): RepoResult<T> {
            return RepoResult(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(message: String, data: T? = null): RepoResult<T> {
            return RepoResult(
                Status.ERROR,
                data,
                message
            )
        }
    }
}