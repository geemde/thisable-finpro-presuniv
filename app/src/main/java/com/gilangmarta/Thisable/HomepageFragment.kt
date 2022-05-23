package com.gilangmarta.Thisable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.gilangmarta.Thisable.databinding.FragmentHomepageBinding
import com.gilangmarta.Thisable.utils.SharedPrefManager
import com.gilangmarta.Thisable.utils.setImageUrl

class HomepageFragment: Fragment() {

    private lateinit var pref: SharedPrefManager

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

        pref = SharedPrefManager(requireContext())

        binding.imgProfileHome.setImageUrl(pref.getPhotoUrl!!)

        binding.imgProfileHome.setOnClickListener {
            it.findNavController().navigate(R.id.action_homepageFragment_to_profileFragment)
        }

        binding.btnPendeteksiTeks.setOnClickListener{
            it.findNavController().navigate((R.id.action_homepageFragment_to_pendeteksiTeksFragment))
        }
    }
}