package com.sviatkuzbyt.mafia.ui.game.roles

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.sviatkuzbyt.mafia.databinding.FragmentRolesBinding
import com.sviatkuzbyt.mafia.ui.elements.CardAnimation
import com.sviatkuzbyt.mafia.ui.game.activity.GameViewModel
import com.sviatkuzbyt.mafia.ui.elements.CardGestureListener
import com.sviatkuzbyt.mafia.ui.elements.GameInterface

class RolesFragment : Fragment(), GameInterface {

    private val activityViewModel by activityViewModels<GameViewModel>()
    private var _binding: FragmentRolesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RolesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRolesBinding.inflate(inflater, container, false)
        viewModel =
            ViewModelProvider(
                requireActivity(),
                RolesViewModelFactory(requireActivity().application, activityViewModel.gameArray, activityViewModel)
            )[RolesViewModel::class.java]
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val cardAnimation = CardAnimation(binding, screenWidth.toFloat())

        viewModel.player.observe(viewLifecycleOwner){
            cardAnimation.playAnimation(it, viewModel.isNextAnimation)
        }

        val gestureDetector = GestureDetector(context, CardGestureListener(viewModel))
        view.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
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