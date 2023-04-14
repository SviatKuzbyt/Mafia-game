package com.sviatkuzbyt.mafia.ui.game.elements.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.data.game.PlayerPanelData
import com.sviatkuzbyt.mafia.ui.game.playerpanel.PlayerPanelViewModel

class PlayerPanelAdapter(private var dataSet: MutableList<PlayerPanelData>, private val viewModel: PlayerPanelViewModel) :
    RecyclerView.Adapter<PlayerPanelAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imagePlayer: ImageView
        val seeRoleButton: Button
        val playerName: TextView

        init {
            imagePlayer = view.findViewById(R.id.imagePlayer)
            seeRoleButton = view.findViewById(R.id.seeRoleButton)
            playerName = view.findViewById(R.id.playerName)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.player_panel_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.imagePlayer.setImageResource(dataSet[position].icon)
        viewHolder.playerName.text = dataSet[position].name

        val background = if(dataSet[position].isSelected) R.drawable.select_background
            else R.color.transparent
        setBackground(viewHolder.itemView,background)

        if (dataSet[position].isSelected) viewHolder.itemView.setBackgroundResource(R.drawable.select_background)

        viewHolder.itemView.setOnClickListener {
            viewModel.updateSelectElement(position, dataSet[position].isSelected)
            dataSet[position].isSelected = !dataSet[position].isSelected
            notifyItemChanged(position)
        }
    }

    private fun setBackground(itemView: View, resource: Int){
        itemView.setBackgroundResource(resource)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: MutableList<PlayerPanelData>){
        dataSet = list
        notifyDataSetChanged()
    }

    fun removeItems(removeIndexes: List<Int>){
        removeIndexes.forEach {
            notifyItemRemoved(it)
            notifyItemRangeChanged(it, itemCount)
        }
    }

    fun addPlayer(){
        notifyItemInserted(dataSet.size)
    }
    override fun getItemCount() = dataSet.size
}