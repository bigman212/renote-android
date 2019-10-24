package ru.bill.renote.notes.create

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.bill.renote.notes.list.NoteListFragment

class NoteCreateFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() =
            NoteCreateFragment().apply {
                //                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}