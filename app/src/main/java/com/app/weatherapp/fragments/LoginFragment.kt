package com.app.weatherapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.app.weatherapp.utils.MySharedPreference
import com.app.weatherapp.R
import com.app.weatherapp.databinding.FragmentLoginBinding
import com.app.weatherapp.viewmodels.MainViewModel

class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )
        binding?.viewModel = viewModel
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        binding?.userNameEditText?.addTextChangedListener(textWatcher)
        binding?.pwdEditText?.addTextChangedListener(textWatcher)

        binding?.signInButton?.setOnClickListener {
            view.isEnabled = false
            viewModel.signIn()
        }

        viewModel.loginResponse.observe(viewLifecycleOwner) { it ->
            viewModel.isLoading.set(false)
            binding?.signInButton?.isEnabled = true
            when (it.code) {
                200 -> {
                    Toast.makeText(
                        requireActivity().applicationContext,
                        it.message, Toast.LENGTH_SHORT
                    ).show()
                    MySharedPreference.setLoginStatus(true)
                    NavHostFragment.findNavController(this).popBackStack(
                        R.id.onBoardFragment,
                        true
                    )
                    NavHostFragment.findNavController(this).navigate(R.id.usersListFragment)
                }
                201 -> binding?.userNameLayout?.error = it.message

                202 -> binding?.pwdLayout?.error = it.message
                else -> {
                    Toast.makeText(
                        requireActivity().applicationContext,
                        it.message, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private var textWatcher: TextWatcher? = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            binding?.userNameLayout?.error = null
            binding?.pwdLayout?.error = null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        textWatcher = null
    }

}