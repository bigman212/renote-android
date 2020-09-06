package ru.bill.renote

import android.os.Bundle
import androidx.navigation.findNavController
import ru.bill.renote.base.BaseActivity


class MainActivity : BaseActivity(R.layout.activity_main) {

  override fun onCreate(savedInstanceState: Bundle?) {
    appComponent.inject(this)
    super.onCreate(savedInstanceState)

//
//    val host = supportFragmentManager
//      .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//
//    val navController = findNavController(R.id.nav_host_fragment)
  }

  override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()
}
