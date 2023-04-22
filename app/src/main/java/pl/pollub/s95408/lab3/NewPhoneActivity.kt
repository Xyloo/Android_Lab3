package pl.pollub.s95408.lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast

class NewPhoneActivity : AppCompatActivity() {
    private lateinit var manufacturerEdit : EditText
    private lateinit var modelEdit : EditText
    private lateinit var androidVersionEdit : EditText
    private lateinit var websiteEdit : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_phone)

        manufacturerEdit = findViewById(R.id.manufacturerEditText)
        modelEdit = findViewById(R.id.modelEditText)
        androidVersionEdit = findViewById(R.id.androidVersionEditText)
        websiteEdit = findViewById(R.id.websiteEditText)

        val saveButton = findViewById<android.widget.Button>(R.id.saveButton)
        val cancelButton = findViewById<android.widget.Button>(R.id.cancelButton)
        saveButton.setOnClickListener {
            val replyIntent = Intent()
            if(manufacturerEdit.text.isEmpty() || modelEdit.text.isEmpty() || androidVersionEdit.text.isEmpty() || websiteEdit.text.isEmpty())
            {
                setResult(RESULT_CANCELED, replyIntent)
            }
            else
            {
                val manufacturer = manufacturerEdit.text.toString()
                val model = modelEdit.text.toString()
                val androidVersion = androidVersionEdit.text.toString()
                val website = websiteEdit.text.toString()
                replyIntent.putExtra("manufacturer", manufacturer)
                replyIntent.putExtra("model", model)
                replyIntent.putExtra("androidVersion", androidVersion)
                replyIntent.putExtra("website", website)
                setResult(RESULT_OK, replyIntent)
            }
            finish()
        }
        cancelButton.setOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra("message", "Cancelled")
            setResult(RESULT_CANCELED, replyIntent)
            finish()
        }
    }
    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}