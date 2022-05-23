package com.gilangmarta.Thisable

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.gilangmarta.Thisable.databinding.FragmentLoginBinding
import com.gilangmarta.Thisable.utils.ConstVal.KEY_EMAIL
import com.gilangmarta.Thisable.utils.ConstVal.KEY_IS_LOGIN
import com.gilangmarta.Thisable.utils.ConstVal.KEY_PHOTO_URL
import com.gilangmarta.Thisable.utils.ConstVal.KEY_USER_NAME
import com.gilangmarta.Thisable.utils.SharedPrefManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class LoginFragment: Fragment() {

    private lateinit var pref: SharedPrefManager
    private lateinit var oneTapClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    private var fragmentLoginBinding: FragmentLoginBinding? = null
    private val binding get() = fragmentLoginBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater,container,false)
        return fragmentLoginBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = SharedPrefManager(requireContext())

        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client))
            .requestEmail()
            .build()

        oneTapClient = GoogleSignIn.getClient(requireActivity(), gso)

        auth = Firebase.auth

        // digunain kalo trigger button to page, binding button taro paling bawah
        binding.tvKetentuanLayanan.setOnClickListener{
            it.findNavController().navigate(R.id.action_loginFragment_to_ketentuanLayananFragment)
        }

        binding.btnLoginGoogle.setOnClickListener{
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = oneTapClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Timber.d("firebaseAuthWithGoogle : ${account.id}")
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
                // Google Sign In failed, update UI appropriately
                Timber.w("Google sign in failed : $e")
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.d("signInWithCredential:success")
                    val user = auth.currentUser
                    Toast.makeText(context, "Selamat datang di Thisable!", Toast.LENGTH_SHORT).show()
                    pref.apply {
                        setStringPreference(KEY_USER_NAME, user?.displayName.toString())
                        setStringPreference(KEY_EMAIL, user?.email.toString())
                        setStringPreference(KEY_PHOTO_URL, user?.photoUrl.toString())
                        setBooleanPreference(KEY_IS_LOGIN, true)
                    }
                    findNavController().navigate(R.id.action_loginFragment_to_homepageFragment)
                } else {
                    // If sign in fails, display a message to the user.
                    Timber.w(task.exception, "signInWithCredential:failure")
                    Toast.makeText(context, "Gagal login. Coba lagi!", Toast.LENGTH_SHORT).show()
                }
            }
    }

}