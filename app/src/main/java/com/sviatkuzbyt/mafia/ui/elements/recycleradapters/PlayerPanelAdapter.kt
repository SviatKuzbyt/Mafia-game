package com.sviatkuzbyt.mafia.ui.elements.recycleradapters

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
import com.sviatkuzbyt.mafia.data.game.elements.PlayerPanelData
import com.sviatkuzbyt.mafia.ui.game.PlayerRoleActivity
import com.sviatkuzbyt.mafia.ui.game.playerpanel.PlayerPanelViewModel

class PlayerPanelAdapter(
    private var dataSet: MutableList<PlayerPanelData>,
    private val viewModel: PlayerPanelViewModel,
    private val context: Context
) :
    RecyclerView.Adapter<PlayerPanelAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imagePlayer: ImageView = view.findViewById(R.id.imagePlayer)
        val seeRoleButton: Button = view.findViewById(R.id.seeRoleButton)
        val playerName: TextView = view.findViewById(R.id.playerName)

        fun setBackground(resource: Int) {
            itemView.setBackgroundResource(resource)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.player_panel_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]
        viewHolder.imagePlayer.setImageResource(item.icon)
        viewHolder.playerName.text = item.name
        viewHolder.setBackground(if (item.isSelected) R.drawable.select_background else R.color.transparent)

        viewHolder.itemView.setOnClickListener {
            selectItem(position, item)
        }

        viewHolder.seeRoleButton.setOnClickListener {
            seeRole(position)
        }
    }

    private fun selectItem(position: Int, item: PlayerPanelData){
        viewModel.updateSelectElement(position, item.isSelected)
        item.isSelected = !item.isSelected
        notifyItemChanged(position)
    }

    private fun seeRole(position: Int) {
        val item = dataSet[position]
        val intent = Intent(context, PlayerRoleActivity::class.java).apply {
            putExtra("roleText", item.roleName)
            putExtra("player", item.name)
            putExtra("roleType", item.typeRole)
        }
        context.startActivity(intent)
    }

    override fun getItemCount() = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: MutableList<PlayerPanelData>) {
        dataSet = list
        notifyDataSetChanged()
    }

    fun removeItems(removeIndexes: List<Int>) {
        removeIndexes.forEach {
            notifyItemRemoved(it)
            notifyItemRangeChanged(it, itemCount)
        }
    }

    fun addPlayer() {
        notifyItemRangeInserted(dataSet.size, 1)
    }
}