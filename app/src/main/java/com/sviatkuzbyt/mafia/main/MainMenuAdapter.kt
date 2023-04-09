package com.sviatkuzbyt.mafia.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sviatkuzbyt.mafia.R


class MainMenuAdapter(private val dataSet: Array<MainMenuData>, private val context: Context) :
    RecyclerView.Adapter<MainMenuAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mainMenuIcon: ImageView
        val mainMenuText: TextView
        init {
            mainMenuIcon = view.findViewById(R.id.mainMenuIcon)
            mainMenuText = view.findViewById(R.id.mainMenuText)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.main_menu_recycler, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.mainMenuIcon.setImageResource(dataSet[position].icon)
        viewHolder.mainMenuText.text = context.getString(dataSet[position].name)

        viewHolder.itemView.setOnClickListener {
            val intent = dataSet[position].intent
            if(intent == null)
                Toast.makeText(context, "Soon", Toast.LENGTH_SHORT).show()
            else
                context.startActivity(intent)
        }
    }

    override fun getItemCount() = dataSet.size

}
