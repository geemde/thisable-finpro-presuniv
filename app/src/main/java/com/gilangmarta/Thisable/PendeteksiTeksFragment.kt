package com.gilangmarta.Thisable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.gilangmarta.Thisable.databinding.FragmentPendeteksiteksBinding

class PendeteksiTeksFragment: Fragment() {
    private var fragmentPendeteksiTeksFragment: FragmentPendeteksiteksBinding? = null
    private val binding get() = fragmentPendeteksiTeksFragment!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentPendeteksiTeksFragment =  FragmentPendeteksiteksBinding.inflate(inflater,container,false)
        return fragmentPendeteksiTeksFragment?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tbPendeteksiTeks.setNavigationOnClickListener {
            it.findNavController().popBackStack()
        }
    }



}