package ru.bill.renote.model

class Resource<T> private constructor(
  val status: Status,
  val data: T?,
  val error: Throwable?
) {
  private constructor(data: T) : this(Status.SUCCESS, data, null)
  private constructor(error: Throwable) : this(Status.ERROR, null, error)
  private constructor() : this(Status.LOADING, null, null)

  fun isSuccessful(): Boolean = this.status == Status.SUCCESS
  fun isError(): Boolean = this.status == Status.ERROR
  fun isLoading(): Boolean = this.status == Status.LOADING

  companion object {
    fun <T> loading(): Resource<T> {
      return Resource()
    }

    fun <T> completed(data: T): Resource<T> {
      return Resource(data)
    }

    fun <T> error(e: Throwable): Resource<T> {
      return Resource(e)
    }
  }

  enum class Status {
    SUCCESS, ERROR, LOADING
  }
}
