package com.app.weatherapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.app.weatherapp.R
import com.app.weatherapp.databinding.FragmentUserFormBinding
import com.app.weatherapp.viewmodels.MainViewModel

class UserFormFragment : Fragment() {

    private var binding: FragmentUserFormBinding? = null
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_user_form,
            container,
            false)

        binding?.viewModel = viewModel
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()


        binding?.cancelButton?.setOnClickListener {
            it.findNavController().navigateUp()
        }

        binding?.saveButton?.setOnClickListener{
            viewModel.addUser()
        }

        viewModel.addUserLiveData.observe(viewLifecycleOwner){
            if(it != null) {
                if (it == "User added successfully") {
                    NavHostFragment.findNavController(this).navigateUp()
                }
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}