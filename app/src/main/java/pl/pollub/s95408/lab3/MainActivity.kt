package pl.pollub.s95408.lab3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val phoneViewModel: PhoneViewModel by viewModels {
        PhoneViewModelFactory((application as PhoneApplication).repository)
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                phoneViewModel.insert(
                    Phone(
                        0,
                        intent?.getStringExtra("manufacturer")!!,
                        intent.getStringExtra("model")!!,
                        intent.getStringExtra("androidVersion")!!,
                        intent.getStringExtra("website")!!
                    )
                )
            } else {
                if (result.data?.getStringExtra("message") != null)
                    Toast.makeText(
                        applicationContext,
                        result.data?.getStringExtra("message")!!,
                        Toast.LENGTH_LONG
                    ).show()
                else
                    Toast.makeText(
                        applicationContext,
                        R.string.emptyData,
                        Toast.LENGTH_LONG
                    ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = PhoneListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        phoneViewModel.allElements.observe(this) { phones ->
            phones?.let { adapter.submitList(it) }
        }

        if(intent?.getStringExtra("message") == "update")
        {
            Log.d("TestingIntent", "MainActivity: ${intent.getStringExtra("manufacturer")} ${intent.getStringExtra("model")} ${intent.getStringExtra("androidVersion")} ${intent.getStringExtra("website")}")
            phoneViewModel.update(
                Phone(
                    intent.getLongExtra("id", 0),
                    intent.getStringExtra("manufacturer")!!,
                    intent.getStringExtra("model")!!,
                    intent.getStringExtra("androidVersion")!!,
                    intent.getStringExtra("website")!!
                )
            )
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewPhoneActivity::class.java)
            getContent.launch(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mainActivityDeleteAllPhones) {
            phoneViewModel.deleteAll()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}