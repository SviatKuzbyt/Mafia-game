package com.sviatkuzbyt.mafia.ui.help.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.ui.elements.recycleradapters.HelpListAdapter

class HelpListActivity : AppCompatActivity() {
    private val buttonCloseList: Button by lazy { findViewById(R.id.buttonCloseList) }
    private val recyclerHelp: RecyclerView by lazy { findViewById(R.id.recyclerHelp) }
    private val viewModel by viewModels<HelpListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_list)

        buttonCloseList.setOnClickListener { finish() }

        recyclerHelp.layoutManager = LinearLayoutManager(this)
        viewModel.list.observe(this){
            recyclerHelp.adapter = HelpListAdapter(it, this)
        }
    }
}