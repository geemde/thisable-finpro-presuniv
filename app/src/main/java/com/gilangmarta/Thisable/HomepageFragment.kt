package com.gilangmarta.Thisable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.gilangmarta.Thisable.databinding.FragmentHomepageBinding

class HomepageFragment: Fragment() {
    private var fragmentHomepageBinding: FragmentHomepageBinding? = null
    private val binding get() = fragmentHomepageBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomepageBinding = FragmentHomepageBinding.inflate(inflater,container,false)
        return fragmentHomepageBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgProfileHome.setOnClickListener {
            it.findNavController().navigate(R.id.action_homepageFragment_to_profileFragment)
        }
    }
}