package ru.bill.renote

import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import ru.bill.renote.base.BaseActivity
import javax.inject.Inject


class MainActivity : BaseActivity(R.layout.activity_main) {

  @Inject
  internal lateinit var fFactory: FragmentFactory

  override fun onCreate(savedInstanceState: Bundle?) {
    appComponent.inject(this)
    supportFragmentManager.fragmentFactory = fFactory
    super.onCreate(savedInstanceState)

    val host = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

    val navController = host.navController
    val appBarConfig = AppBarConfiguration(navController.graph)
    toolbar.setupWithNavController(navController, appBarConfig)
  }

  override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()
}
