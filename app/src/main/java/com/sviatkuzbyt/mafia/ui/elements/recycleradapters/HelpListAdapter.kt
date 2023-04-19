package com.sviatkuzbyt.mafia.ui.elements.recycleradapters

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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val helpListText: TextView = itemView.findViewById(R.id.helpListText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.help_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]
        holder.helpListText.text = item.name

        holder.itemView.setOnClickListener {
            val intent = Intent(context, HelpActivity::class.java)
            intent.putExtra("id", item.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = dataSet.size
}