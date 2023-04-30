package pl.pollub.s95408.lab3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener

class NewPhoneActivity : AppCompatActivity() {
    private lateinit var manufacturerEdit: EditText
    private lateinit var modelEdit: EditText
    private lateinit var androidVersionEdit: EditText
    private lateinit var websiteEdit: EditText
    private var phoneId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_phone)

        manufacturerEdit = findViewById(R.id.manufacturerEditText)
        modelEdit = findViewById(R.id.modelEditText)
        androidVersionEdit = findViewById(R.id.androidVersionEditText)
        websiteEdit = findViewById(R.id.websiteEditText)
        val fields = arrayOf(manufacturerEdit, modelEdit, androidVersionEdit, websiteEdit)

        val saveButton = findViewById<android.widget.Button>(R.id.saveButton)
        val cancelButton = findViewById<android.widget.Button>(R.id.cancelButton)
        val websiteButton = findViewById<android.widget.Button>(R.id.websiteButton)
        saveButton.setOnClickListener {
            val replyIntent: Intent = if(intent.getStringExtra("manufacturer") != null && intent.getStringExtra("model") != null && intent.getStringExtra("os_version") != null && intent.getStringExtra("website") != null) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent()
            }
            val manufacturer = manufacturerEdit.text.toString()
            val model = modelEdit.text.toString()
            val androidVersion = androidVersionEdit.text.toString()
            val website = websiteEdit.text.toString()
            Log.d("TestingIntent", "Intent Manufacturer ${intent.getStringExtra("manufacturer")}")
            Log.d("TestingIntent", "Intent Model ${intent.getStringExtra("model")}")
            Log.d("TestingIntent", "Intent OS Version ${intent.getStringExtra("os_version")}")
            Log.d("TestingIntent", "Intent Website ${intent.getStringExtra("website")}")
            if (manufacturerEdit.text.isEmpty() || modelEdit.text.isEmpty() || androidVersionEdit.text.isEmpty() || websiteEdit.text.isEmpty()) {
                setResult(RESULT_CANCELED, replyIntent)
                finish()
            }
            replyIntent.putExtra("manufacturer", manufacturer)
            replyIntent.putExtra("model", model)
            replyIntent.putExtra("androidVersion", androidVersion)
            replyIntent.putExtra("website", website)
            if (intent.getStringExtra("manufacturer") != null && intent.getStringExtra("model") != null && intent.getStringExtra("os_version") != null && intent.getStringExtra("website") != null) {
                Log.d(
                    "TestingIntent",
                    "New data: ${manufacturerEdit.text} ${modelEdit.text} ${androidVersionEdit.text} ${websiteEdit.text}"
                )
                replyIntent.putExtra("message", "update")
                replyIntent.putExtra("id", phoneId)
                startActivity(replyIntent)
            }
            setResult(RESULT_OK, replyIntent)
            finish()
        }
        cancelButton.setOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra("message", "Cancelled")
            setResult(RESULT_CANCELED, replyIntent)
            finish()
        }
        websiteButton.setOnClickListener {
            val url = if (!websiteEdit.text.toString().startsWith("http://") && !websiteEdit.text.toString().startsWith("https://")) {
                "http://${websiteEdit.text}"
            } else {
                websiteEdit.text.toString()
            }
            val startBrowser = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(startBrowser)
        }

        manufacturerEdit.setText(intent.getStringExtra("manufacturer"))
        modelEdit.setText(intent.getStringExtra("model"))
        androidVersionEdit.setText(intent.getStringExtra("os_version"))
        websiteEdit.setText(intent.getStringExtra("website"))
        phoneId = intent.getLongExtra("id", 0)

        for (field in fields)
        {
            field.addTextChangedListener { text ->
                if (text.toString().isEmpty()) {
                    field.error = "Pole nie może być puste"
                    saveButton.isEnabled = false
                } else {
                    saveButton.isEnabled = true
                }
            }
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}