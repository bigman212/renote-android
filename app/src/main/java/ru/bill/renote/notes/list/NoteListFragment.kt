package ru.bill.renote.notes.list


import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.Observable
import ru.bill.renote.App
import ru.bill.renote.R
import ru.bill.renote.SchedulersProvider
import ru.bill.renote.data.dao.NotesDao
import ru.bill.renote.databinding.FragmentNoteListBinding
import ru.bill.renote.di.AppComponent
import ru.bill.renote.scheduleIoToUi
import timber.log.Timber
import javax.inject.Inject

class NoteListFragment : Fragment(R.layout.fragment_note_list) {

  private val noteListAdapter = GroupAdapter<GroupieViewHolder>()

  @Inject
  internal lateinit var notesDao: NotesDao

  lateinit var binding: FragmentNoteListBinding
//  private val binding: FragmentNoteListBinding by viewBinding()

  protected val appComponent: AppComponent by lazy {
    (requireActivity().applicationContext as App).getApplicationProvider()
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    NoteListComponent.init(appComponent).inject(this)
  }

  fun <T> Observable<T>.scheduleIoToUi(schedulers: SchedulersProvider): Observable<T> {
    return subscribeOn(schedulers.io()).observeOn(schedulers.ui())
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = FragmentNoteListBinding.bind(view)
    notesDao.loadAll()
      .scheduleIoToUi(SchedulersProvider())
      .subscribe(
        { Timber.i(it.toString()) },
        { Timber.e(it) }
      )
//    binding.rvNotes.layoutManager = LinearLayoutManager(context)
//    binding.rvNotes.adapter = noteListAdapter


  }
}
