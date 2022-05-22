package com.gilangmarta.Thisable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.gilangmarta.Thisable.databinding.FragmentPendeteksitulistanganBinding
import com.gilangmarta.Thisable.databinding.FragmentSplashBinding

class PendeteksiTulisTanganFragment:  Fragment() {
    private var fragmentPendeteksiTulisTanganFragment: FragmentPendeteksitulistanganBinding? = null
    private val binding get() = fragmentPendeteksiTulisTanganFragment!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentPendeteksiTulisTanganFragment = FragmentPendeteksitulistanganBinding.inflate(inflater,container,false)
        return  fragmentPendeteksiTulisTanganFragment?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tbPendeteksiTulisTangan.setNavigationOnClickListener {
            it.findNavController().popBackStack()
        }


    }
}