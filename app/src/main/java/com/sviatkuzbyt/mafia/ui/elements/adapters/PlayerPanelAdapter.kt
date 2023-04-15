package com.sviatkuzbyt.mafia.ui.elements.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.data.game.PlayerPanelData
import com.sviatkuzbyt.mafia.ui.game.PlayerRoleActivity
import com.sviatkuzbyt.mafia.ui.game.playerpanel.PlayerPanelViewModel

class PlayerPanelAdapter(private var dataSet: MutableList<PlayerPanelData>,
                         private val viewModel: PlayerPanelViewModel,
                         private val context: Context) :
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

        viewHolder.seeRoleButton.setOnClickListener {
            seeRole(position)
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

    private fun seeRole(position: Int){
        val intent = Intent(context, PlayerRoleActivity::class.java).apply {
            putExtra("roleText", dataSet[position].roleName)
            putExtra("player", dataSet[position].name)
            putExtra("roleType", dataSet[position].typeRole)
        }
        context.startActivity(intent)
    }
    override fun getItemCount() = dataSet.size
}