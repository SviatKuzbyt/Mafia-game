package com.sviatkuzbyt.mafia.ui.game.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sviatkuzbyt.mafia.databinding.FragmentSettingGameBinding
import com.sviatkuzbyt.mafia.ui.game.elements.PlayersSettingAdapter
import com.sviatkuzbyt.mafia.ui.game.elements.RolesSettingAdapter

class SettingGameFragment : Fragment() {
    private var _binding: FragmentSettingGameBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SettingGameViewModel>()
    lateinit var playersSettingAdapter: PlayersSettingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rolesSettingRecycler.layoutManager = LinearLayoutManager(activity)
        viewModel.rolesArray.observe(viewLifecycleOwner){
            binding.rolesSettingRecycler.adapter = RolesSettingAdapter(it, viewModel)
        }

        binding.playersSettingRecycler.layoutManager = LinearLayoutManager(activity)
        playersSettingAdapter = PlayersSettingAdapter(mutableListOf(), viewModel)
        binding.playersSettingRecycler.adapter = playersSettingAdapter

        viewModel.playersList.observe(viewLifecycleOwner){
            when(viewModel.playersListSettingChange){
                PlayersListSettingChange.LoadAll -> playersSettingAdapter.addAll(it)
                PlayersListSettingChange.AddItem -> playersSettingAdapter.addPlayer(it.last())
                PlayersListSettingChange.RemoveItem -> playersSettingAdapter.removePlayer()
            }
            viewModel.playersListSettingChange = PlayersListSettingChange.LoadAll
        }

        viewModel.playersCount.observe(viewLifecycleOwner){
            binding.sumRoles.text = it
        }

        viewModel.error.observe(viewLifecycleOwner){
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}