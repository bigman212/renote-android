package ru.bill.renote.common.extensions

import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Слушаем изменения у ViewState в VM и меняем значение value в LiveData
 *
 * val liveState: MutableLiveData<ProfileViewState> = MutableLiveData(createInitialState())
 * private var state: ProfileViewState by liveState.delegate()
 *
 * state = state.isFetching() // liveData меняет свое состояние на новый state
 *
 */
fun <T : Any> MutableLiveData<T>.delegate(): ReadWriteProperty<Any, T> {
  return object : ReadWriteProperty<Any, T> {
    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
      if (Thread.currentThread() == Looper.getMainLooper().thread) {
        this@delegate.value = value // или postValue? :hmm:
      } else {
        this@delegate.postValue(value)
      }
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
      return requireValue()
    }
  }
}

/**
 * The ViewModelStoreOwner controls the scope of the ViewModel.
 * It may be overridden with a different ViewModelStoreOwner,
 * such as the host Activity or the parent fragment, in order to
 * scope the lifetime of the ViewModel to the lifetime of the
 * ViewModelStoreOwner that is passed in.
 */
inline fun <reified T : ViewModel> Fragment.viewModelWithProvider(
    noinline ownerProducer: () -> ViewModelStoreOwner = { this },
    crossinline provider: () -> T
): Lazy<T> {
  return viewModels(ownerProducer) {
    object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return provider.invoke() as T
      }
    }
  }
}

private fun <T : Any> LiveData<T>.requireValue(): T = checkNotNull(value)

inline fun <T> Fragment.observe(liveData: LiveData<T>, crossinline block: (T) -> Unit) {
  liveData.observe(this) {
    block.invoke(it)
  }
}

fun <X, Y> LiveData<X>.mapDistinct(transform: (X) -> Y): LiveData<Y> {
  return map(transform)
    .distinctUntilChanged()
}
