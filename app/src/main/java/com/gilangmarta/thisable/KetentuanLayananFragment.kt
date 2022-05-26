package com.gilangmarta.thisable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.gilangmarta.thisable.databinding.FragmentKetentuanlayananBinding

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

        initAction()
    }

    private fun initAction() {
        //tombol back
        binding.tbKetentuanLayanan.setNavigationOnClickListener {
            it.findNavController().popBackStack()
        }
    }
}