package com.gilangmarta.Thisable

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.gilangmarta.Thisable.databinding.FragmentPendeteksitulistanganBinding
import com.gilangmarta.Thisable.utils.ConstVal
import com.gilangmarta.Thisable.utils.rotateBitmap
import com.gilangmarta.Thisable.utils.showToast
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.ktx.Firebase
import com.google.gson.*
import java.io.ByteArrayOutputStream
import java.io.File

class PendeteksiTulisTanganFragment:  Fragment() {
    private var fragmentPendeteksiTulisTanganFragment: FragmentPendeteksitulistanganBinding? = null
    private val binding get() = fragmentPendeteksiTulisTanganFragment!!

    private var inputFile: File? = null

    private lateinit var functions: FirebaseFunctions

    private var base64encoded: String? = null

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
        functions = FirebaseFunctions.getInstance()
    }

    private fun initAction() {
        //button ambil foto & deteksi ulang
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        binding.btnTulisTanganAmbilFoto.setOnClickListener{
            val intent = Intent(context, CameraActivity::class.java)
            launchIntentCamera.launch(intent)
        }
        // button back to homepage
        binding.tbPendeteksiTulisTangan.setNavigationOnClickListener {
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
            val byteArrayOutputStream = ByteArrayOutputStream()
            val result = rotateBitmap(BitmapFactory.decodeFile(file.path))
            result.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)

            // gambar dalam bentuk byte
            val imageByte = byteArrayOutputStream.toByteArray()
            base64encoded = Base64.encodeToString(imageByte, Base64.NO_WRAP)

            binding.imgTulisTanganInput.setImageBitmap(result)
            binding.btnTulisTanganAmbilFoto.text = "Deteksi Ulang"
            binding.tvTulisTanganTerdeteksi.text = "Teks terdeteksi:"
        }
    }

    private fun annotateImage(requestJson: String): Task<JsonElement> {
        return functions
            .getHttpsCallable("annotateImage")
            .call(requestJson)
            .continueWith { task ->
                // This continuation runs on either success or failure, but if the task
                // has failed then result will throw an Exception which will be
                // propagated down.
                val result = task.result?.data
                JsonParser.parseString(Gson().toJson(result))
            }
    }


}