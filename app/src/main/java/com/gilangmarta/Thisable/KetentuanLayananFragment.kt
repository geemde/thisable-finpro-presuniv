package com.gilangmarta.Thisable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.gilangmarta.Thisable.databinding.FragmentKetentuanlayananBinding

class KetentuanLayananFragment: Fragment() {
    private var fragmentKetentuanlayananBinding: FragmentKetentuanlayananBinding? = null
    private val binding get() = fragmentKetentuanlayananBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentKetentuanlayananBinding = FragmentKetentuanlayananBinding.inflate(inflater,container, false)
        return fragmentKetentuanlayananBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //ini code untuk kembali (toolbar)
        binding.tbKetentuanLayanan.setNavigationOnClickListener {
            it.findNavController().popBackStack()
        }
    }
}