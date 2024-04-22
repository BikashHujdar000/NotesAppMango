package com.example.notes.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.core.view.isVisible

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.notes.Model.UserRequest
import com.example.notes.R
import com.example.notes.Utils.NetworkResults
import com.example.notes.Utils.TokenManager
import com.example.notes.ViewModel.AuthViewModel
import com.example.notes.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding :FragmentRegisterBinding?= null
    private  val binding get() = _binding !!
    private  val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding=FragmentRegisterBinding.inflate(inflater, container, false)

        if (tokenManager.getToken()!=null)
        {
            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignUp.setOnClickListener{



         //   authViewModel.registerUser(getUserRequest());

            val validationResult = validateUsersInput()

            if (validationResult.first)
            {
                authViewModel.registerUser(getUserRequest())
            }
            else
            {
                binding.txtError.text= validationResult.second
            }



        }
        binding.btnLogin.setOnClickListener {

            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

       bindObserver()



    }

    private fun getUserRequest():UserRequest
    {

        val username  = binding.txtUsername.text.toString()
        val emailAddress = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()
        return UserRequest(username,emailAddress,password)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun validateUsersInput(): Pair<Boolean, String> {
     val userRequest = getUserRequest()
        return authViewModel.ValidateCredentials(userRequest.username,userRequest.email,userRequest.password,false)

    }

    private fun bindObserver() {

        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible= false
            when(it)
            {
                is NetworkResults.Success ->{
                    // token wala kaam karna hain

                    tokenManager.saveToken(it.data!!.token)

                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                }
                is NetworkResults.Error -> {

                    binding.txtError.text= it.message
                }

                is NetworkResults.Loading->{
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        // we do this because t o free from view as on destroy called view also get distroyed that's why we destroy the binding too
        _binding= null
    }


}