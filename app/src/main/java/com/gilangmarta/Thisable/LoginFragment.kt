package com.gilangmarta.Thisable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.gilangmarta.Thisable.databinding.FragmentLoginBinding

class LoginFragment: Fragment() {
    private var fragmentLoginBinding: FragmentLoginBinding? = null
    private val binding get() = fragmentLoginBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater,container,false)
        return fragmentLoginBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // digunain kalo trigger button to page
        binding.tvKetentuanLayanan.setOnClickListener{
            it.findNavController().navigate(R.id.action_loginFragment_to_ketentuanLayananFragment)
        }
    }

}