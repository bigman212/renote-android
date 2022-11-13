package ru.bill.renote.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import javax.inject.Inject
import javax.inject.Provider


class AppFragmentFactory @Inject constructor(
    private val providers: @JvmSuppressWildcards Map<Class<out Fragment>, Provider<Fragment>>
) : FragmentFactory() {

  override fun instantiate(classLoader: ClassLoader, fragmentClassName: String): Fragment {
    val fragmentClass = loadFragmentClass(classLoader, fragmentClassName)

    val provider = providers[fragmentClass]
      ?: findFragmentByClass(fragmentClass)
    return provider?.get() ?: return super.instantiate(classLoader, fragmentClassName)
  }

  private fun findFragmentByClass(fragmentClassToInit: Class<out Fragment>): @JvmSuppressWildcards Provider<Fragment>? {
    return providers.asIterable()
      .find { fragmentProvider -> fragmentClassToInit.isAssignableFrom(fragmentProvider.key) }
      ?.value
  }
}
