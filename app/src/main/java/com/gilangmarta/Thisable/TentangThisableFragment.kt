package com.gilangmarta.Thisable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.gilangmarta.Thisable.databinding.FragmentTentangthisableBinding

class TentangThisableFragment: Fragment() {
    private var fragmentTentangthisableBinding: FragmentTentangthisableBinding? = null
    private val binding get() = fragmentTentangthisableBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentTentangthisableBinding = FragmentTentangthisableBinding.inflate(inflater,container,false)
        return fragmentTentangthisableBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tbtentangThisable.setNavigationOnClickListener {
            it.findNavController().popBackStack()
        }
    }
}