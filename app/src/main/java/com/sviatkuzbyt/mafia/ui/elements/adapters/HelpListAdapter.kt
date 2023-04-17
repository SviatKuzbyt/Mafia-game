package com.sviatkuzbyt.mafia.ui.elements.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.data.help.HelpListData

import com.sviatkuzbyt.mafia.ui.help.item.HelpActivity

class HelpListAdapter(private var dataSet: Array<HelpListData>, private val context: Context) :
    RecyclerView.Adapter<HelpListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val helpListText: TextView
        init {
            helpListText = view.findViewById(R.id.helpListText)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.help_list, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.helpListText.text = dataSet[position].name

        viewHolder.itemView.setOnClickListener {
            val intent = Intent(context, HelpActivity::class.java)
            intent.putExtra("id", dataSet[position].id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = dataSet.size
}