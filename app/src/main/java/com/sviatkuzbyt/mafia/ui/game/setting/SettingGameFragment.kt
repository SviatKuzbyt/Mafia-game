package com.sviatkuzbyt.mafia.ui.game.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sviatkuzbyt.mafia.databinding.FragmentSettingGameBinding
import com.sviatkuzbyt.mafia.ui.game.activity.GameViewModel
import com.sviatkuzbyt.mafia.ui.elements.GameInterface
import com.sviatkuzbyt.mafia.ui.elements.recycleradapters.PlayersSettingAdapter
import com.sviatkuzbyt.mafia.ui.elements.recycleradapters.RolesSettingAdapter

class SettingGameFragment : Fragment(), GameInterface {

    private var _binding: FragmentSettingGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SettingGameViewModel
    private val activityViewModel by activityViewModels<GameViewModel>()
    private lateinit var playersSettingAdapter: PlayersSettingAdapter
    private lateinit var rolesSettingAdapter: RolesSettingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[SettingGameViewModel::class.java]
        _binding = FragmentSettingGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRolesArray()
        setPlayersList()

        viewModel.playersCount.observe(viewLifecycleOwner){
            binding.sumRoles.text = it
        }

        viewModel.error.observe(viewLifecycleOwner){
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        }
    }

    private fun setRolesArray(){
        binding.rolesSettingRecycler.layoutManager = LinearLayoutManager(activity)
        rolesSettingAdapter = RolesSettingAdapter(arrayOf(), viewModel)
        binding.rolesSettingRecycler.adapter = rolesSettingAdapter

        viewModel.rolesArray.observe(viewLifecycleOwner){
            when(viewModel.rolesChangeMode){
                RecycleChangeMode.LoadAll -> rolesSettingAdapter.setData(it)
                else -> rolesSettingAdapter.changeCount(viewModel.roleCount, viewModel.targetRolePositionChange)
            }
            viewModel.rolesChangeMode = RecycleChangeMode.LoadAll
        }
    }

    private fun setPlayersList(){
        binding.playersSettingRecycler.layoutManager = LinearLayoutManager(activity)
        playersSettingAdapter = PlayersSettingAdapter(mutableListOf(), viewModel)
        binding.playersSettingRecycler.adapter = playersSettingAdapter

        viewModel.playersList.observe(viewLifecycleOwner){
            when(viewModel.playersChangeMode){
                RecycleChangeMode.LoadAll -> playersSettingAdapter.setData(it)
                RecycleChangeMode.AddItem -> playersSettingAdapter.addPlayer(it.last())
                else -> playersSettingAdapter.removePlayer()
            }
            viewModel.playersChangeMode = RecycleChangeMode.LoadAll
        }
    }

    override fun nextButtonClick() {
        val gameList = viewModel.createGameList()
        activityViewModel.gameArray = gameList
        activityViewModel.setStartGameStep()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}