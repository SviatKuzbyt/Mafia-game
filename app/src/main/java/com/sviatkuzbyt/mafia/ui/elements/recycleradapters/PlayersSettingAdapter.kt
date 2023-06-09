package com.sviatkuzbyt.mafia.ui.elements.recycleradapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.ui.game.setting.SettingGameViewModel

class PlayersSettingAdapter(private val dataSet: MutableList<String>, private val viewModel: SettingGameViewModel) :
    RecyclerView.Adapter<PlayersSettingAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val editTextPlayers: EditText = view.findViewById(R.id.editTextPlayers)
        val editButtonPlayers: Button = view.findViewById(R.id.editButtonPlayers)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.players_setting_recycler, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.editTextPlayers.setText(dataSet[position])

        viewHolder.editTextPlayers.setOnEditorActionListener { view, _, _ ->
            viewModel.changePlayerName(position, view.text.toString())
            dataSet[position] = view.text.toString()
            false
        }

        viewHolder.editButtonPlayers.setOnClickListener {
            viewHolder.editTextPlayers.setText("")
        }
    }

    fun addPlayer(player: String){
        dataSet.add(player)
        notifyItemInserted(dataSet.size - 1)
    }

    fun removePlayer() {
        if (dataSet.isNotEmpty()) {
            val removeIndex = dataSet.size-1
            dataSet.removeAt(removeIndex)
            notifyItemRemoved(removeIndex)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: MutableList<String>){
        dataSet.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataSet.size
}