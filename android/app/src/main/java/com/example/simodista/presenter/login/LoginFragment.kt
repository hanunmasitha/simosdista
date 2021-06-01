package com.example.simodista.presenter.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.simodista.R
import com.example.simodista.databinding.FragmentLoginBinding
import com.example.simodista.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class LoginFragment : Fragment() {
    lateinit var binding : FragmentLoginBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore

    override fun onStart() {
        super.onStart()
        if(FirebaseAuth.getInstance().currentUser != null){
            binding.progressBar5.visibility = View.VISIBLE
            binding.backgroundDim.visibility = View.VISIBLE
            activity?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

            firestore.collection("users")
                .document(firebaseAuth.currentUser?.uid.toString())
                .get().addOnSuccessListener{
                    val user = it.toObject<User>()
                    binding.progressBar5.visibility = View.GONE
                    activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    if(user?.isAdmin == true){
                        view?.findNavController()?.navigate(R.id.action_loginFragment_to_adminHomeFragment)
                    }else{
                        view?.findNavController()?.navigate(R.id.action_loginFragment_to_userHomeFragment)
                    }
                }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        binding.registerButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.loginEmail.text.toString().trim()
            val password = binding.loginPassword.text.toString().trim()
            if(validateLogin(email, password)){
                binding.progressBar2.visibility = View.VISIBLE
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                    firestore.collection("users")
                            .document(firebaseAuth.currentUser?.uid.toString())
                            .get().addOnSuccessListener{
                                val user = it.toObject<User>()
                                if(user?.isAdmin == true){
                                    view.findNavController().navigate(R.id.action_loginFragment_to_adminHomeFragment)
                                }else{
                                    view.findNavController().navigate(R.id.action_loginFragment_to_userHomeFragment)
                                }
                            }
                }.addOnFailureListener {
                    binding.progressBar2.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateLogin(email: String, password: String) : Boolean{
        if (email.isEmpty()){
            binding.loginEmail.error = "Email is required!"
            return false
        }

        if(password.isEmpty()){
            binding.loginPassword.error = "Password is required!"
            return false
        }
        return true
    }

}