package ru.bill.renote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.bill.renote.notes.list.NoteListFragment
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.fragment.app.Fragment




class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val manager = supportFragmentManager
    val transaction = manager.beginTransaction()
    transaction.add(frame_layout.id, NoteListFragment.newInstance())
    transaction.commit()
  }

  fun createAndAddFragment(tag: String, cls: Class<out Fragment>, addToBackStack: Boolean) {
    // First, check to see if the fragment with the specified tag has already been added to the layout
    var frag: Fragment? = supportFragmentManager.findFragmentByTag(tag)
    if (frag == null) {
      frag = cls.newInstance()
      val transaction = supportFragmentManager.beginTransaction()
      transaction.add(frame_layout.id, frag)
      transaction.commit()
      // Add to the target layout id
    }
  }


}
