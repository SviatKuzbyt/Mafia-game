package com.sviatkuzbyt.mafia.ui.game.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sviatkuzbyt.mafia.databinding.FragmentSettingGameBinding
import com.sviatkuzbyt.mafia.ui.game.activity.GameViewModel
import com.sviatkuzbyt.mafia.ui.game.elements.GameInterface
import com.sviatkuzbyt.mafia.ui.game.elements.adapters.PlayersSettingAdapter
import com.sviatkuzbyt.mafia.ui.game.elements.adapters.RolesSettingAdapter

class SettingGameFragment : Fragment(), GameInterface {
    private var _binding: FragmentSettingGameBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SettingGameViewModel>()
    private val activityViewModel by activityViewModels<GameViewModel>()
    private lateinit var playersSettingAdapter: PlayersSettingAdapter
    private lateinit var rolesSettingAdapter: RolesSettingAdapter

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
        rolesSettingAdapter = RolesSettingAdapter(arrayOf(), viewModel)
        binding.rolesSettingRecycler.adapter = rolesSettingAdapter
        viewModel.rolesArray.observe(viewLifecycleOwner){
            when(viewModel.rolesChangeMode){
                RecycleChangeMode.LoadAll -> rolesSettingAdapter.addAll(it)
                else -> rolesSettingAdapter.changeCount(viewModel.getCount(), viewModel.getPosition())
            }
            viewModel.rolesChangeMode = RecycleChangeMode.LoadAll
        }

        binding.playersSettingRecycler.layoutManager = LinearLayoutManager(activity)
        playersSettingAdapter = PlayersSettingAdapter(mutableListOf(), viewModel)
        binding.playersSettingRecycler.adapter = playersSettingAdapter

        viewModel.playersList.observe(viewLifecycleOwner){
            when(viewModel.playersChangeMode){
                RecycleChangeMode.LoadAll -> playersSettingAdapter.addAll(it)
                RecycleChangeMode.AddItem -> playersSettingAdapter.addPlayer(it.last())
                else -> playersSettingAdapter.removePlayer()
            }
            viewModel.playersChangeMode = RecycleChangeMode.LoadAll
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

    override fun nextButtonClick() {
        val gameList = viewModel.createGameList()
        activityViewModel.gameArray = gameList
        activityViewModel.setStartGameStep()
    }

    override fun backButtonClick() {}
}