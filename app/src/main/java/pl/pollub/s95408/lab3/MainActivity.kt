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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val phoneViewModel: PhoneViewModel by viewModels {
        PhoneViewModelFactory((application as PhoneApplication).repository)
    }
    val adapter = PhoneListAdapter()

    val itemTouchHelperCallback =
        object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                phoneViewModel.delete(adapter.currentList[viewHolder.adapterPosition])
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.deletedPhone),
                    Toast.LENGTH_SHORT
                ).show()
            }

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
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

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