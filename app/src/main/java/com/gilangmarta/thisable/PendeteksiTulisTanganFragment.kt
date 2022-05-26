package com.gilangmarta.thisable

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
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.gilangmarta.thisable.data.model.*
import com.gilangmarta.thisable.data.remote.APIResponse
import com.gilangmarta.thisable.databinding.FragmentPendeteksitulistanganBinding
import com.gilangmarta.thisable.presentation.VisionAPIViewModel
import com.gilangmarta.thisable.utils.ConstVal
import com.gilangmarta.thisable.utils.rotateBitmap
import com.gilangmarta.thisable.utils.showToast
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.gson.*
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.File
@AndroidEntryPoint
class PendeteksiTulisTanganFragment:  Fragment() {
    private var fragmentPendeteksiTulisTanganFragment: FragmentPendeteksitulistanganBinding? = null
    private val binding get() = fragmentPendeteksiTulisTanganFragment!!

    private var inputFile: File? = null

    private lateinit var functions: FirebaseFunctions

    private var base64encoded: String? = null

    private val viewModel: VisionAPIViewModel by viewModels()

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

            val textDetectionRequest = TextDetectionRequest(
                request = TextDetectionRequestItem(
                    image = ImageItem(
                        source = SourceItem(
                            imageUri = base64encoded.toString()
                        )
                    ),
                    features = listOf(
                        FeatureItem(
                            type = "DOCUMENT_TEXT_DETECTION",
                            maxResults = 1
                        )
                    )
                )
            )

            textDetection("AIzaSyB7_3Mu6S14q-ARs-lwN9OGX3KDhDYxsCU", textDetectionRequest)



            binding.imgTulisTanganInput.setImageBitmap(result)
            binding.btnTulisTanganAmbilFoto.text = "Deteksi Ulang"
            binding.tvTulisTanganTerdeteksi.text = "Teks terdeteksi:"
        }
    }

    private fun textDetection(apiKey: String, request: TextDetectionRequest) {
        viewModel.textDetection(apiKey, request).observe(viewLifecycleOwner) { response ->
            when(response) {
                is APIResponse.Loading -> {
                    context?.showToast("Loading.......")
                }
                is APIResponse.Success -> {
                    context?.showToast(response.data.responses[0].fullTextAnnotation.toString())
                }
                is APIResponse.Error -> {
                    context?.showToast("Error occured")
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {

    }
}