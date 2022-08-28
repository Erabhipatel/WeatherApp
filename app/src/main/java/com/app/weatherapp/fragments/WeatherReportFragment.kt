package com.app.weatherapp.fragments

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.app.weatherapp.utils.MySharedPreference
import com.app.weatherapp.R
import com.app.weatherapp.databinding.FragmentWeatherReportBinding
import com.app.weatherapp.models.Result
import com.app.weatherapp.viewmodels.WeatherViewModel
//import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
class WeatherReportFragment : Fragment() {

    private var binding: FragmentWeatherReportBinding? = null
    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_weather_report,
            container,
            false
        )
        binding?.viewModel = viewModel
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initUi()

    }

    private fun initUi() {
        askForPermissions()

        viewModel.locationLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    viewModel.getWeatherData(
                        it.data.latitude,
                        it.data.longitude
                    )
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), it.exception.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        viewModel.weatherLiveData.observe(viewLifecycleOwner) {
            viewModel.isLoading.set(false)
            when (it) {
                is Result.Success -> {
                    binding?.weatherdetails = it.data
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), it.exception.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }

    private fun askForPermissions() {
        if (checkLocationPermissions()) {
            viewModel.getLocation()
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            AlertDialog.Builder(context)
                .setTitle(getString(R.string.location_permission))
                .setMessage(getString(R.string.permission_msg))
                .setPositiveButton(
                    getString(R.string.ok)
                ) { p0, p1 ->
                    p0.dismiss()
                    requestPermissions()
                }
                .setNegativeButton(getString(R.string.cancel))
                { p0, p1 ->
                    p0.dismiss()
                }.show()
        } else {
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun checkLocationPermissions(): Boolean {
        return (ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
                == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED)
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true -> {
                viewModel.getLocation()
            }
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true -> {
                viewModel.getLocation()
            }
            else -> {

            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.weather_screen_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            MySharedPreference.setLoginStatus(false)
            NavHostFragment.findNavController(this).popBackStack(R.id.usersListFragment, true)
            NavHostFragment.findNavController(this).navigate(R.id.onBoardFragment)

        }
        return super.onOptionsItemSelected(item)
    }


}