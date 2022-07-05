package com.maxataliyev_01.task1

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LogIn : Fragment() {
    private lateinit var email:EditText
    private lateinit var phoneNumber:EditText
    private lateinit var password:EditText
    private lateinit var fAuth:FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_log_in,container,false)
        email=view.findViewById(R.id.etLogUserName)
        phoneNumber=view.findViewById(R.id.etLogUserName)
        password=view.findViewById(R.id.etLogInPassword)
        fAuth=Firebase.auth


        view.findViewById<AppCompatButton>(R.id.btnSignUp).setOnClickListener{
            findNavController().navigate(R.id.action_logIn_to_signUp)
        }
        view.findViewById<AppCompatButton>(R.id.btnLogIn).setOnClickListener{
            validateForm()

        }
        return view
    }


       private fun firebaseLogIn(){
           fAuth.signInWithEmailAndPassword(email.text.toString(),password.text.toString()).addOnCompleteListener {
               task->
               if (task.isSuccessful){
                   findNavController().navigate((R.id.action_logIn_to_mainActivity))
               }else{
                   Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
               }

           }








       }
       private fun validateForm(){

           val icon= AppCompatResources.getDrawable(requireContext(),
               R.drawable.ic_shield)
           icon?.setBounds(0,0,icon.intrinsicWidth,icon.intrinsicHeight)

           when{
               TextUtils.isEmpty(phoneNumber.text.toString().trim())->{
                   phoneNumber.setError("Please enter phonenumber",icon)
               }
               TextUtils.isEmpty(email.text.toString().trim())->{
                   email.setError("Please enter email",icon)
               }
               TextUtils.isEmpty(password.text.toString().trim())->{
                   password.setError("Please enter password",icon)
               }

               (phoneNumber.text.toString().isNotEmpty()||email.text.toString().isNotEmpty())&&password.text.toString().isNotEmpty()->{
                if (email.text.toString().matches(Regex("^[A-Za-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$"))){
                    firebaseLogIn()
               }else{
                    email.setError("Please validate mail",icon)
                }
               }

               }

           }
       }



