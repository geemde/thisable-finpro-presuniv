package com.gilangmarta.Thisable

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gilangmarta.Thisable.databinding.FragmentSplashBinding
import com.gilangmarta.Thisable.utils.ConstVal.SPLASH_DELAY_TIME

class SplashFragment: Fragment() {
    private var fragmentSplashBinding: FragmentSplashBinding? = null  //setiap buat layout, kalo pake viewbinding akan generate...
    private val binding get() = fragmentSplashBinding!! // fungsi get untuk ngambil isi fragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSplashBinding = FragmentSplashBinding.inflate(inflater,container,false) //udah create view, tinggal return
        return fragmentSplashBinding?.root //return
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }, SPLASH_DELAY_TIME)
    }
}