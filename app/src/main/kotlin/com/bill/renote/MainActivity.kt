package com.bill.renote

import androidx.fragment.app.commit
import android.os.Bundle
import com.bill.renote.base.BaseActivity
import com.bill.renote.noteList.NoteListFragment

class MainActivity : BaseActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.commit(allowStateLoss = true) {
            replace(
                R.id.rootFragment,
                NoteListFragment(),
                NoteListFragment::javaClass.name
            )
        }
    }
}
