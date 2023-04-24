package com.sviatkuzbyt.mafia.ui.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.sviatkuzbyt.mafia.databinding.FragmentInformationBinding
import com.sviatkuzbyt.mafia.ui.game.activity.GameViewModel
import com.sviatkuzbyt.mafia.ui.elements.GameInterface

class InformationFragment : Fragment(), GameInterface {
    private val activityViewModel by activityViewModels<GameViewModel>()
    private var _binding: FragmentInformationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(label: String, information: String, emoji: String, isFinish: Boolean): InformationFragment {
            val fragment = InformationFragment()
            val args = Bundle()
            args.putString("label", label)
            args.putString("information", information)
            args.putString("emoji", emoji)
            args.putBoolean("isFinish", isFinish)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.emojiTextView.text = arguments?.getString("emoji")
        binding.labelTextView.text = arguments?.getString("label")
        binding.informationTextView.text = arguments?.getString("information")
    }

    override fun nextButtonClick() {
        if (arguments?.getBoolean("isFinish") == true) activityViewModel.closeActivity()
        else activityViewModel.setGetCardStep()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}