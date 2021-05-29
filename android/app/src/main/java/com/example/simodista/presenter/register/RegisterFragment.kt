package com.example.simodista.presenter.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.simodista.R
import com.example.simodista.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment : Fragment() {
    lateinit var binding : FragmentRegisterBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterBinding.bind(view)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()

        binding.button.setOnClickListener {
            val name = binding.registerName.text.toString().trim()
            val email = binding.registerEmail.text.toString().trim()
            val phone = binding.editTextPhone.text.toString().trim()
            val password = binding.editTextTextPassword.text.toString().trim()

            if(validateRegister(name, email, phone, password)){
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener{
                    val userId = firebaseAuth.currentUser?.uid as String
                    val document = firebaseFirestore.collection("users").document(userId)

                    val user = hashMapOf(
                        "name" to name,
                        "email" to email,
                        "phone" to phone,
                    )

                    document.set(user).addOnSuccessListener {
                        Toast.makeText(requireContext(), "Register Success", Toast.LENGTH_LONG).show()
                    }

                    view.findNavController().navigate(R.id.action_registerFragment_to_userHomeFragment)
                }.addOnFailureListener {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun validateRegister(name: String, email: String, phone: String, password: String): Boolean {
        if (name.isEmpty()){
            binding.registerName.error = "Name is required!"
            return false
        }

        if(name.toCharArray().size < 4){
            binding.registerName.error = "Minimum length of name is 4"
            return false
        }

        if(email.isEmpty()){
            binding.registerEmail.error = "Email is required!"
            return false
        }

        if (phone.isEmpty()){
            binding.editTextPhone.error = "Phone number is required"
            return false
        }

        if(password.isEmpty()){
            binding.editTextTextPassword.error = "Password is required"
            return false
        }

        return true
    }

}