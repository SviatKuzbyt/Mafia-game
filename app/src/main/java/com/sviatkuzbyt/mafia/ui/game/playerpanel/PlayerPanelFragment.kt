package com.sviatkuzbyt.mafia.ui.game.playerpanel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sviatkuzbyt.mafia.databinding.FragmentPlayerPanelBinding
import com.sviatkuzbyt.mafia.ui.game.activity.GameViewModel
import com.sviatkuzbyt.mafia.ui.elements.GameInterface
import com.sviatkuzbyt.mafia.ui.elements.adapters.PlayerPanelAdapter

class PlayerPanelFragment : Fragment(), GameInterface {

    private var _binding: FragmentPlayerPanelBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PlayerPanelViewModel
    private val activityViewModel by activityViewModels<GameViewModel>()
    private lateinit var adapter: PlayerPanelAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerPanelBinding.inflate(inflater, container, false)
        viewModel =
            ViewModelProvider(requireActivity(),
                PlayerPanelViewModelFactory(requireActivity().application, activityViewModel.gameArray, activityViewModel)
            )[PlayerPanelViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.playerPanelRecycler.layoutManager = LinearLayoutManager(activity)
        adapter = PlayerPanelAdapter(mutableListOf(), viewModel, requireContext())
        binding.playerPanelRecycler.adapter = adapter

        viewModel.playerList.observe(viewLifecycleOwner){

            when(viewModel.mode){
                0 ->{
                    adapter.updateList(it)
                }
                1 -> adapter.removeItems(viewModel.removedItems)
                2 -> adapter.addPlayer()
            }
            viewModel.mode = 0
        }
    }

    override fun nextButtonClick() {
        viewModel.removeItems()
    }

    override fun backButtonClick() {
        viewModel.getRemovedPlayer()
    }
}