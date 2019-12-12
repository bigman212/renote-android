package ru.bill.renote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val host = supportFragmentManager
      .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

    val navController = host.navController
    val appBarConfig = AppBarConfiguration(navController.graph)
    toolbar.setupWithNavController(navController, appBarConfig)
  }

  override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()
}
