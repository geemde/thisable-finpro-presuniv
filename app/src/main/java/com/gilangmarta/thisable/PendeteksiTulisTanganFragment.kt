package com.gilangmarta.thisable

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.gilangmarta.thisable.data.model.FeatureItem
import com.gilangmarta.thisable.data.model.ImageItem
import com.gilangmarta.thisable.data.model.TextDetectionRequest
import com.gilangmarta.thisable.data.model.TextDetectionRequestItem
import com.gilangmarta.thisable.data.remote.APIResponse
import com.gilangmarta.thisable.databinding.FragmentPendeteksitulistanganBinding
import com.gilangmarta.thisable.presentation.VisionAPIViewModel
import com.gilangmarta.thisable.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.File

@AndroidEntryPoint
class PendeteksiTulisTanganFragment : Fragment() {

    private lateinit var viewModel: VisionAPIViewModel

    private var fragmentPendeteksiTulisTanganFragment: FragmentPendeteksitulistanganBinding? = null
    private val binding get() = fragmentPendeteksiTulisTanganFragment!!

    private var inputFile: File? = null

    private var base64encoded: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentPendeteksiTulisTanganFragment = FragmentPendeteksitulistanganBinding.inflate(inflater, container, false)
        return fragmentPendeteksiTulisTanganFragment?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initAction()
    }

    private fun initUI() {
        binding.btnDetectTulisTangan.isEnabled = !base64encoded.isNullOrEmpty()
    }

    private fun initAction() {
        //button ambil foto & deteksi ulang
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        binding.btnDetectTulisTangan.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorGrey))

        binding.btnTulisTanganAmbilFoto.setOnClickListener {
            val intent = Intent(context, CameraActivity::class.java)
            launchIntentCamera.launch(intent)
        }
        // button back to homepage
        binding.tbPendeteksiTulisTangan.setNavigationOnClickListener {
            it.findNavController().popBackStack()
        }

        binding.btnDetectTulisTangan.setOnClickListener {
            val textDetectionRequest = TextDetectionRequest(
                requests = listOf(
                    TextDetectionRequestItem(
                        image = ImageItem(
                            content = base64encoded.toString()
                        ),
                        features = listOf(
                            FeatureItem(
                                type = "DOCUMENT_TEXT_DETECTION",
                                maxResults = 1
                            )
                        )
                    )
                )
            )
            textDetection("AIzaSyB7_3Mu6S14q-ARs-lwN9OGX3KDhDYxsCU", textDetectionRequest)
        }
    }

    private val launchIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == ConstVal.CAMERA_X_RESULT) {
            val file = it?.data?.getSerializableExtra(ConstVal.KEY_PICTURE) as File
            inputFile = file
            val byteArrayOutputStream = ByteArrayOutputStream()
            val result = rotateBitmap(BitmapFactory.decodeFile(file.path))
            val final = scaleBitmapDown(result, 640)

            final.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)

            // gambar dalam bentuk byte
            val imageByte = byteArrayOutputStream.toByteArray()
            base64encoded = Base64.encodeToString(imageByte, Base64.NO_WRAP)

            binding.imgTulisTanganInput.setImageBitmap(result)
            binding.btnTulisTanganAmbilFoto.text = "Ambil Gambar Ulang"
            binding.tvTulisTanganTerdeteksi.text = "Teks terdeteksi:"
            binding.btnDetectTulisTangan.isEnabled = true
            binding.btnDetectTulisTangan.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorBlue))

        }
    }

    private fun textDetection(apiKey: String, request: TextDetectionRequest) {
        viewModel = ViewModelProvider(this)[VisionAPIViewModel::class.java]
        viewModel.textDetection(apiKey, request).observe(viewLifecycleOwner) { response ->
            when (response) {
                is APIResponse.Loading -> {

                    context?.showToast("Mohon tunggu...")
                    binding.tvHasilPendeteksiTulisTangan.text = "Mohon tunggu..."
                }
                is APIResponse.Success -> {
                    val finalResult = response.data.responses[0].fullTextAnnotation.text
                    context?.showToast("Sukses!")
                    binding.tvHasilPendeteksiTulisTangan.text = finalResult

                    //copy to clipboard
                    binding.tvHasilPendeteksiTulisTangan.setOnLongClickListener {
                        copyToClipboard(requireContext(), finalResult)
                        context?.showToast("Teks berhasil disalin!")
                        true
                    }
                }
                is APIResponse.Error -> {
                    Log.e("visionapi", response.errorMessage)
                    binding.tvHasilPendeteksiTulisTangan.text = "Terjadi kesalahan."
                    context?.showToast("Terjadi kesalahan pada sistem")
                }
            }
        }
    }

}