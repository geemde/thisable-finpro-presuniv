package com.gilangmarta.thisable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.gilangmarta.thisable.databinding.FragmentHomepageBinding
import com.gilangmarta.thisable.utils.SharedPrefManager
import com.gilangmarta.thisable.utils.setImageUrl

class HomepageFragment : Fragment() {

    private lateinit var pref: SharedPrefManager

    private var fragmentHomepageBinding: FragmentHomepageBinding? = null
    private val binding get() = fragmentHomepageBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomepageBinding = FragmentHomepageBinding.inflate(inflater, container, false)
        return fragmentHomepageBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = SharedPrefManager(requireContext())

        initUI()
        initAction()
    }

    private fun initUI() {
        binding.imgProfileHome.setImageUrl(pref.getPhotoUrl!!)
    }

    private fun initAction() {
        //button profile
        binding.imgProfileHome.setOnClickListener {
            it.findNavController().navigate(R.id.action_homepageFragment_to_profileFragment)
        }
        //button pendeteksi teks
        binding.btnPendeteksiTeks.setOnClickListener {
            it.findNavController()
                .navigate((R.id.action_homepageFragment_to_pendeteksiTeksFragment))
        }
        //button pendeteksi tulis tangan
        binding.btnPendeteksiTulisTangan.setOnClickListener {
            it.findNavController()
                .navigate((R.id.action_homepageFragment_to_pendeteksiTulisTanganFragment))
        }
    }


}