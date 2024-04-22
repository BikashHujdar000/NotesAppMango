package com.example.notes.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.notes.Model.UserRequest
import com.example.notes.R
import com.example.notes.Utils.NetworkResults
import com.example.notes.Utils.TokenManager
import com.example.notes.ViewModel.AuthViewModel
import com.example.notes.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
  private var _binding: FragmentLoginBinding?= null
   private val binding get() = _binding!!
    private  val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
      return  binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {

        super.onViewCreated(view, savedInstanceState)

            // if we dont want to validation of users than we can use this way
//        binding.btnLogin.setOnClickListener {
//            authViewModel.loginUser(getUserRequest())
//            bindObserver()
//
//        }

        binding.btnLogin.setOnClickListener {
            val validationResult = validateUsersInput()

            if (validationResult.first)
            {

                authViewModel.loginUser(getUserRequest())
            }
            else

            {
                binding.txtError.text=validationResult.second
            }

            bindObserver()
        }
        binding.btnSignUp.setOnClickListener {

            findNavController().popBackStack()
        }





    }

    private fun getUserRequest(): UserRequest
    {

        val emailAddress = binding.loginEmailAddress.text.toString()
        val password = binding.txtPassword.text.toString()
        return UserRequest(username = "",emailAddress,password)
    }

    private fun validateUsersInput(): Pair<Boolean, String> {
        val userRequest = getUserRequest()
        return authViewModel.ValidateCredentials(userRequest.username,userRequest.email,userRequest.password,true)

    }

    private fun bindObserver() {

        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible= false
            when(it)
            {
                is NetworkResults.Success ->{
                    // token wala kaam karna hain
                    tokenManager.saveToken(it.data!!.token)

                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
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
        _binding=null
    }


}
