package com.gilangmarta.Thisable

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.gilangmarta.Thisable.databinding.FragmentPendeteksitulistanganBinding
import java.io.File

class PendeteksiTulisTanganFragment:  Fragment() {
    private var fragmentPendeteksiTulisTanganFragment: FragmentPendeteksitulistanganBinding? = null
    private val binding get() = fragmentPendeteksiTulisTanganFragment!!

    private var inputFile: File? = null

    companion object {
        val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

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
        initAction()
    }

    private fun initAction() {
        //button ambil foto & deteksi ulang
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        binding.btnTulisTanganAmbilFoto.setOnClickListener{
            startActivityForResult(intent, 101)
        }
        // button back to homepage
        binding.tbPendeteksiTulisTangan.setNavigationOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            var picture: Bitmap? = data?.getParcelableExtra("data")
            binding.imgTulisTanganInput.setImageBitmap(picture)
            binding.btnTulisTanganAmbilFoto.text = "Deteksi Ulang"
            binding.tvTulisTanganTerdeteksi.text = "Teks terdeteksi:"
        }
    }

}