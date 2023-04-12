package com.sviatkuzbyt.mafia.ui.game.roles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.databinding.FragmentInformationBinding
import com.sviatkuzbyt.mafia.databinding.FragmentRolesBinding
import com.sviatkuzbyt.mafia.databinding.FragmentSettingGameBinding
import com.sviatkuzbyt.mafia.ui.game.activity.GameViewModel
import com.sviatkuzbyt.mafia.ui.game.elements.GameInterface


class RolesFragment : Fragment(), GameInterface {

    private val activityViewModel by activityViewModels<GameViewModel>()
    private var _binding: FragmentRolesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RolesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRolesBinding.inflate(inflater, container, false)
        viewModel =
            ViewModelProvider(this,
                RolesViewModelFactory(requireActivity().application, activityViewModel.gameArray)
            )[RolesViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.player.observe(viewLifecycleOwner){
            binding.playerTextRole.text = it
        }

        viewModel.role.observe(viewLifecycleOwner){
            binding.roleText.text = it
        }

        viewModel.image.observe(viewLifecycleOwner){
            binding.roleImage.setImageDrawable(it)
        }
    }

    override fun nextButtonClick() {
        viewModel.nextPlayer()
    }

    override fun backButtonClick() {
       viewModel.previousPlayer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}