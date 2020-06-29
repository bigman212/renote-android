package ru.bill.renote

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import ru.bill.renote.data.dao.NotesDao
import javax.inject.Inject


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
