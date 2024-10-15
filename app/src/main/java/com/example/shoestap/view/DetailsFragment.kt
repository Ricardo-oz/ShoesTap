package com.example.shoestap.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.shoestap.MainActivity
import com.example.shoestap.R
import com.example.shoestap.databinding.FragmentDetailsBinding
import com.example.shoestap.model.CartItem
import com.example.shoestap.model.Item

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var item: Item

    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = requireActivity() as MainActivity

        arguments?.let {
            item = it.getParcelable(ARG_ITEM) ?: Item("", "", "", 0.0f)
        }

        binding.tvTitulo.text = item.name
        binding.tvDetalle.text = item.des
        binding.tvPrice.text = item.price.toString()

        Glide.with(requireContext())
            .load(item.img)
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.imgDet)

        binding.btnVolver.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.btnAdd.setOnClickListener {
            mainActivity.addToCart(item) // Agregar el item al carrito en MainActivity
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_ITEM = "Productos"

        fun newInstance(item: Item): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle().apply {
                putParcelable(ARG_ITEM, item)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
