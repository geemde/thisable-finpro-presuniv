package com.gilangmarta.thisable

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.gilangmarta.thisable.data.model.FeatureItem
import com.gilangmarta.thisable.data.model.ImageItem
import com.gilangmarta.thisable.data.model.TextDetectionRequest
import com.gilangmarta.thisable.data.model.TextDetectionRequestItem
import com.gilangmarta.thisable.data.remote.APIResponse
import com.gilangmarta.thisable.databinding.FragmentPendeteksiteksBinding
import com.gilangmarta.thisable.presentation.VisionAPIViewModel
import com.gilangmarta.thisable.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.File

@AndroidEntryPoint
class PendeteksiTeksFragment : Fragment() {

    private lateinit var viewModel: VisionAPIViewModel

    private var fragmentPendeteksiTeksFragment: FragmentPendeteksiteksBinding? = null
    private val binding get() = fragmentPendeteksiTeksFragment!!

    private var inputFile: File? = null

    private var base64encoded: String? = null

    companion object {
        val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentPendeteksiTeksFragment =
            FragmentPendeteksiteksBinding.inflate(inflater, container, false)
        return fragmentPendeteksiTeksFragment?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAction()
        initUI()
    }

    private fun initUI() {
        binding.btnDetectTeks.isEnabled = !base64encoded.isNullOrEmpty()
    }

    private fun initAction() {
        //button ambil foto & deteksi ulang
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //binding.btnDetectTeks.isEnabled = false
        binding.btnDetectTeks.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorGrey
            )
        )

        binding.btnTeksAmbilFoto.setOnClickListener {
            val intent = Intent(context, CameraActivity::class.java)
            launchIntentCamera.launch(intent)
        }
        // button back to homepage
        binding.tbPendeteksiTeks.setNavigationOnClickListener {
            it.findNavController().popBackStack()
        }

        binding.btnDetectTeks.setOnClickListener {
            val textDetectionRequest = TextDetectionRequest(
                requests = listOf(
                    TextDetectionRequestItem(
                        image = ImageItem(
                            content = base64encoded.toString()
                        ),
                        features = listOf(
                            FeatureItem(
                                type = "TEXT_DETECTION",
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

                binding.imgTeksInput.setImageBitmap(result)
                binding.btnTeksAmbilFoto.text = "Ambil Gambar Ulang"
                binding.tvTeksTerdeteksi.text = "Teks terdeteksi:"
                binding.btnDetectTeks.isEnabled = true
                binding.btnDetectTeks.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorBlue
                    )
                )

            }
        }

    }

    private fun textDetection(apiKey: String, request: TextDetectionRequest) {
        viewModel = ViewModelProvider(this)[VisionAPIViewModel::class.java]
        viewModel.textDetection(apiKey, request).observe(viewLifecycleOwner) { response ->
            when (response) {
                is APIResponse.Loading -> {

                    context?.showToast("Mohon tunggu...")
                    binding.tvHasilPendeteksiTeks.text = "Mohon tunggu..."
                }
                is APIResponse.Success -> {
                    val finalResult = response.data.responses[0].fullTextAnnotation.text
                    context?.showToast("Sukses!")
                    binding.tvHasilPendeteksiTeks.text = finalResult

                    //copy to clipboard
                    binding.tvHasilPendeteksiTeks.setOnLongClickListener {
                        copyToClipboard(requireContext(), finalResult)
                        context?.showToast("Teks berhasil disalin!")
                        true
                    }
                }
                is APIResponse.Error -> {
                    Log.e("visionapi", response.errorMessage)
                    binding.tvHasilPendeteksiTeks.text = "Terjadi kesalahan."
                    context?.showToast("Terjadi kesalahan pada sistem")
                }
            }
        }
    }


}