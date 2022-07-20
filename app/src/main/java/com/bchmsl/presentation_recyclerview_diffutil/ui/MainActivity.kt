package com.bchmsl.presentation_recyclerview_diffutil.ui

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bchmsl.presentation_recyclerview_diffutil.adapters.PersonsAdapter
import com.bchmsl.presentation_recyclerview_diffutil.adapters.PersonsListAdapter
import com.bchmsl.presentation_recyclerview_diffutil.data.personsData
import com.bchmsl.presentation_recyclerview_diffutil.databinding.ActivityMainBinding
import com.bchmsl.presentation_recyclerview_diffutil.model.Person

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val personsAdapter by lazy { PersonsListAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun onRestart() {
        super.onRestart()
        personsAdapter.submitList(personsData.toList())
    }

    private fun init() {
        setupRecycler()
        listeners()
    }

    private fun setupRecycler() {
        binding.rvPersons.apply {
            adapter = personsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        personsAdapter.submitList(personsData)
        setupTouchHelper()
    }

    private fun setupTouchHelper() {
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val currentItem = personsData[viewHolder.adapterPosition]
                personsData.remove(currentItem)
                personsAdapter.submitList(personsData.toList())
            }
        }

        val helper = ItemTouchHelper(itemTouchCallback)
        helper.attachToRecyclerView(binding.rvPersons)
    }

    private fun listeners() {
        binding.fbAdd.setOnClickListener {
            addUser()
        }
        personsAdapter.itemClick = {
            editUser(it)
        }
        binding.srLayout.setOnRefreshListener {
            setupRecycler()
            binding.srLayout.isRefreshing = false
        }
    }

    private fun addUser() {
        val intent = Intent(this@MainActivity, EditPersonActivity::class.java)
        startActivity(intent)
    }

    private fun editUser(person: Person) {
        val intent = Intent(this@MainActivity, EditPersonActivity::class.java)
        intent.putExtra("userId", person.id)
        startActivity(intent)
    }

}