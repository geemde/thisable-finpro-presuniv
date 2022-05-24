package com.gilangmarta.Thisable

import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.gilangmarta.Thisable.databinding.FragmentPendeteksiteksBinding
import com.gilangmarta.Thisable.utils.ConstVal
import com.gilangmarta.Thisable.utils.rotateBitmap
import java.io.File

class PendeteksiTeksFragment: Fragment() {
    private var fragmentPendeteksiTeksFragment: FragmentPendeteksiteksBinding? = null
    private val binding get() = fragmentPendeteksiTeksFragment!!

    private var inputFile: File? = null

    companion object {
        val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

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

        initAction()
    }

    private fun initAction() {
        //button ambil foto & deteksi ulang
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        binding.btnTeksAmbilFoto.setOnClickListener{
            val intent = Intent(context, CameraActivity::class.java)
            launchIntentCamera.launch(intent)
        }
        // button back to homepage
        binding.tbPendeteksiTeks.setNavigationOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    private val launchIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == ConstVal.CAMERA_X_RESULT) {
            val file = it?.data?.getSerializableExtra(ConstVal.KEY_PICTURE) as File
            // yang dikirim ke API input file
            inputFile = file
            val result = rotateBitmap(BitmapFactory.decodeFile(file.path))

            binding.imgTeksInput.setImageBitmap(result)
            binding.btnTeksAmbilFoto.text = "Deteksi Ulang"
            binding.tvTeksTerdeteksi.text = "Teks terdeteksi:"
        }
    }


}