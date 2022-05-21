package com.gilangmarta.Thisable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.gilangmarta.Thisable.databinding.FragmentProfileBinding

class ProfileFragment: Fragment() {
    private var fragmentProfileBinding: FragmentProfileBinding? = null
    private val binding get() = fragmentProfileBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentProfileBinding = FragmentProfileBinding.inflate(inflater,container,false)
        return fragmentProfileBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tbProfile.setNavigationOnClickListener {
            it.findNavController().popBackStack()
        }

        binding.btnTentangThisable.setOnClickListener{
            it.findNavController().navigate(R.id.action_profileFragment_to_tentangThisableFragment)
        }
    }
}