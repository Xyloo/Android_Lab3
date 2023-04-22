package pl.pollub.s95408.lab3

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val newWordActivityRequestCode = 1
    private val phoneViewModel: PhoneViewModel by viewModels {
        PhoneViewModelFactory((application as PhoneApplication).repository)
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

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewPhoneActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            phoneViewModel.insert(Phone(0, intentData?.getStringExtra("manufacturer")!!, intentData.getStringExtra("model")!!, intentData.getStringExtra("androidVersion")!!, intentData.getStringExtra("website")!!))
        } else {
            if(intentData?.getStringExtra("message") != null)
                Toast.makeText(
                    applicationContext,
                    intentData.getStringExtra("message")!!,
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
}