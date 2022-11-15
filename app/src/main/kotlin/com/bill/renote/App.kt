package com.bill.renote

import android.app.Application
import com.bill.renote.data.InAppFeatureFlags
import com.bill.renote.data.repository.CategoriesRepository
import com.bill.renote.data.repository.NoteRepository
import com.bill.renote.di.AppModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber
import kotlinx.coroutines.launch

class App : Application() {

    private val inAppFeatureFlags: InAppFeatureFlags by inject()
    private val noteRepository: NoteRepository by inject()
    private val categoriesRepository: CategoriesRepository by inject()
    private val appScope: AppScope by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(this@App)
            modules(AppModule.create())
        }

        if (inAppFeatureFlags.isDebugBuild) {
            Timber.plant(Timber.DebugTree())
        }

        appScope.launch {
            if (categoriesRepository.getAllCategoriesWithNotes().isEmpty() && noteRepository.getAllNotes().isEmpty()) {
                prefillApp()
            }
        }
    }

    private suspend fun prefillApp() {
        categoriesRepository.createCategory("Math")
        val literature = categoriesRepository.createCategory("Literature")
        val todo = categoriesRepository.createCategory("To do")

        val crime = noteRepository.createNote(
            noteName = "Bulgakov",
            noteBody = buildString {
                append("Alienation is the primary theme of Crime and Punishment. At first, Raskolnikov’s pride separates ")
                append("him from society. He sees himself as superior to all other people and so cannot relate to anyone. ")
                appendLine()
                append("Within his personal philosophy, he sees other people as tools and uses them for his own ends. ")
                append("After committing the murders, ")
                append("his isolation grows because of his intense guilt and the half-delirium into which his guilt throws him. ")
                appendLine()
                append("Over and over again, Raskolnikov pushes away the people who are trying to help him, ")
                append("including Sonya, Dunya, Pulcheria Alexandrovna, Razumikhin, and even Porfiry Petrovich, ")
                append("and then suffers the consequences. ")
                appendLine()
                append("In the end, he finds the total alienation that he has brought upon himself intolerable. ")
                append("Only in the Epilogue, when he finally realizes that he loves Sonya, ")
                append("does Raskolnikov break through the wall of pride and self-centeredness that has separated him from society.")
            }
        )

        val finishApp = noteRepository.createNote("Finish app", "Let's do rock!")

        categoriesRepository.linkNoteToCategory(crime.id, literature.id)
        categoriesRepository.linkNoteToCategory(finishApp.id, todo.id)
    }
}
