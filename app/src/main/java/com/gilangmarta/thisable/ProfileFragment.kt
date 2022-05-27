package com.gilangmarta.thisable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.gilangmarta.thisable.databinding.FragmentProfileBinding
import com.gilangmarta.thisable.utils.ConstVal.KEY_EMAIL
import com.gilangmarta.thisable.utils.ConstVal.KEY_IS_LOGIN
import com.gilangmarta.thisable.utils.ConstVal.KEY_PHOTO_URL
import com.gilangmarta.thisable.utils.ConstVal.KEY_TOKEN
import com.gilangmarta.thisable.utils.ConstVal.KEY_USER_ID
import com.gilangmarta.thisable.utils.ConstVal.KEY_USER_NAME
import com.gilangmarta.thisable.utils.SharedPrefManager
import com.gilangmarta.thisable.utils.setImageUrl
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {
    private lateinit var pref: SharedPrefManager
    private lateinit var auth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private var fragmentProfileBinding: FragmentProfileBinding? = null
    private val binding get() = fragmentProfileBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return fragmentProfileBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = SharedPrefManager(requireContext())
        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        initUI()
        initAction()
    }

    private fun initUI() {
        binding.tvFullName.text = pref.getUserName
        binding.tvFullEmail.text = pref.getEmail
        binding.imgProfilePage.setImageUrl(pref.getPhotoUrl!!)
    }

    private fun initAction() {
        binding.tbProfile.setNavigationOnClickListener {
            it.findNavController().popBackStack()
        }

        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }

        binding.btnTentangThisable.setOnClickListener {
            it.findNavController().navigate(R.id.action_profileFragment_to_tentangThisableFragment)
        }
    }

    private fun logout() {
        auth.signOut()
        mGoogleSignInClient.signOut().addOnCompleteListener {
            pref.apply {
                clearPreferenceByKey(KEY_USER_ID)
                clearPreferenceByKey(KEY_TOKEN)
                clearPreferenceByKey(KEY_USER_NAME)
                clearPreferenceByKey(KEY_IS_LOGIN)
                clearPreferenceByKey(KEY_EMAIL)
                clearPreferenceByKey(KEY_PHOTO_URL)
            }
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(getString(R.string.message_information))
            setMessage(getString(R.string.message_logout_confirmation))
            setPositiveButton("OK") { _, _ ->
                try {
                    logout()
                } finally {
                    findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                }
            }
            setNegativeButton("Batal") { p0, _ ->
                p0.dismiss()
            }
        }.create().show()
    }
}