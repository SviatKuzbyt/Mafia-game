package com.sviatkuzbyt.mafia.ui.game.elements.adapters

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
        val editTextPlayers: EditText
        val editButtonPlayers: Button
        init {
            editTextPlayers = view.findViewById(R.id.editTextPlayers)
            editButtonPlayers = view.findViewById(R.id.editButtonPlayers)
        }
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

    fun addAll(list: MutableList<String>){
        if(dataSet.size > 0){
            notifyItemRangeRemoved(0, dataSet.size)
            dataSet.clear()
        }

        dataSet.addAll(list)
        notifyItemRangeInserted(0, dataSet.size)
    }

    override fun getItemCount() = dataSet.size
}