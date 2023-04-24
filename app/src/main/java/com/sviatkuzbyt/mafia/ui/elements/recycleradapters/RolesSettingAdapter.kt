package com.sviatkuzbyt.mafia.ui.elements.recycleradapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.data.game.elements.Roles
import com.sviatkuzbyt.mafia.ui.game.setting.SettingGameViewModel

class RolesSettingAdapter(private var dataSet: Array<Roles>, private val viewModel: SettingGameViewModel) :
    RecyclerView.Adapter<RolesSettingAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imageRoleSetting)
        val text: TextView = view.findViewById(R.id.textRoleSetting)
        val reduceButton: Button = view.findViewById(R.id.reduceButtonRoleSetting)
        val increaseButton: Button = view.findViewById(R.id.increaseButtonRoleSetting)
        val count: TextView = view.findViewById(R.id.countRoleSetting)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.roles_settings_recycler, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.image.setImageResource(dataSet[position].icon)
        viewHolder.text.text = dataSet[position].name
        viewHolder.count.text = dataSet[position].count.toString()

        viewHolder.increaseButton.setOnClickListener {
            viewModel.changeRole(position, 1)
        }

        viewHolder.reduceButton.setOnClickListener {
            viewModel.changeRole(position, -1)

        }
    }

    fun addAll(list: Array<Roles>){
        if(dataSet.isNotEmpty()){
            notifyItemRangeRemoved(0, dataSet.size)
        }

        dataSet = list
        notifyDataSetChanged()
    }
    override fun getItemCount() = dataSet.size
    fun changeCount(count: Int, position: Int) {
        dataSet[position].count = count
        notifyItemChanged(position)
    }
}