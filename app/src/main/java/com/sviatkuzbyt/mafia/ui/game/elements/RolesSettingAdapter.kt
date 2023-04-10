package com.sviatkuzbyt.mafia.ui.game.elements

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.data.game.Roles
import com.sviatkuzbyt.mafia.ui.game.setting.SettingGameViewModel

class RolesSettingAdapter(private val dataSet: Array<Roles>, private val viewModel: SettingGameViewModel) :
    RecyclerView.Adapter<RolesSettingAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView
        val text: TextView
        val reduceButton: Button
        val increaseButton: Button
        val count: TextView
        init {
            image = view.findViewById(R.id.imageRoleSetting)
            text = view.findViewById(R.id.textRoleSetting)
            reduceButton = view.findViewById(R.id.reduceButtonRoleSetting)
            increaseButton = view.findViewById(R.id.increaseButtonRoleSetting)
            count = view.findViewById(R.id.countRoleSetting)
        }
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
            changeRolesCount(1, position, viewHolder.count)
        }

        viewHolder.reduceButton.setOnClickListener {
            changeRolesCount( -1, position, viewHolder.count)
        }
    }

    private fun changeRolesCount(delta: Int, position: Int, textView: TextView){
        val targetCount = dataSet[position].count + delta
        if(targetCount <= dataSet[position].max && targetCount >= dataSet[position].min){
            viewModel.changeRole(position, delta)
            textView.text = targetCount.toString()
        }

    }

    override fun getItemCount() = dataSet.size
}