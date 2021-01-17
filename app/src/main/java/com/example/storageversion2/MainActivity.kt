package com.example.storageversion2

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.room.Room
import com.example.storageversion2.dataBase.Note
import com.example.storageversion2.dataBase.NotesDataBase
import com.example.storageversion2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val PREFS_KEY = "PREFS_KEY"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getPreferences(MODE_PRIVATE)

        //save to preferences
        binding.saveToPrefs.setOnClickListener {
            val text = binding.editTextNote.editableText.toString()
            prefs.edit().apply {
                putString(PREFS_KEY, text)
                apply()
                binding.editTextNote.editableText.clear()
            }
        }

        //load from preferences
        binding.loadFromPrefs.setOnClickListener {
            val text = prefs.getString(PREFS_KEY, "")
            binding.textView.text = text
        }

        val internalStorage = InternalStorage(this)

        //save to internal storage
        binding.saveInternal.setOnClickListener {
            val text = binding.editTextNote.editableText.toString()
            internalStorage.write(text)
            binding.editTextNote.editableText.clear()
        }

        //load from internal storage
        binding.loadInternal.setOnClickListener {
            val text = internalStorage.read()
            binding.textView.text = text
        }

        val externalStorage = ExternalStorage(this)

        //save to external storage
        binding.saveExternal.setOnClickListener {
            //askPermission()
            val text = binding.editTextNote.editableText.toString()
            externalStorage.write(text)
            binding.editTextNote.editableText.clear()
        }

        //load from external storage
        binding.loadExternal.setOnClickListener {
            val text = externalStorage.read()
            binding.textView.text = text
        }

        val db = Room.databaseBuilder(this, NotesDataBase::class.java, "dataBase")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()

        // save to dataBase
        binding.saveToBD.setOnClickListener {
            val text = binding.editTextNote.editableText.toString()
            db.getNotesDao().insertNote(Note(name = text))
            binding.editTextNote.editableText.clear()
        }

        //load from dataBase
        binding.loadFromBD.setOnClickListener {
            val text = db.getNotesDao().getLastNote()
            binding.textView.text = text
        }
    }
}






