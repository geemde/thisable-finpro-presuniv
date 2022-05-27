package com.gilangmarta.thisable

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gilangmarta.thisable.databinding.FragmentSplashBinding
import com.gilangmarta.thisable.utils.ConstVal.SPLASH_DELAY_TIME
import com.gilangmarta.thisable.utils.SharedPrefManager

class SplashFragment : Fragment() {
    private lateinit var pref: SharedPrefManager

    private var fragmentSplashBinding: FragmentSplashBinding? =
        null  //setiap buat layout, kalo pake viewbinding akan generate...
    private val binding get() = fragmentSplashBinding!! // fungsi get untuk ngambil isi fragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSplashBinding = FragmentSplashBinding.inflate(
            inflater,
            container,
            false
        ) //udah create view, tinggal return
        return fragmentSplashBinding?.root //return
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = SharedPrefManager(requireContext())
        val isLogin = pref.isLogin

        Handler(Looper.getMainLooper()).postDelayed({
            when {
                isLogin -> {
                    findNavController().navigate(R.id.action_splashFragment_to_homepageFragment)
                }
                !isLogin -> {
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                }
            }
        }, SPLASH_DELAY_TIME)
    }
}