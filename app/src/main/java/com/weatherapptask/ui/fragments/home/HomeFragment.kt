package com.weatherapptask.ui.fragments.home


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.weatherapptask.BuildConfig
import com.weatherapptask.databinding.FragmentHomeBinding
import com.weatherapptask.network.weatherForcast.model.response.ResponseWeatherForeCast
import com.weatherapptask.ui.base.BaseFragment
import com.weatherapptask.ui.fragments.home.adapter.weatherCastAdapter.WeatherCastAdapter
import com.weatherapptask.utilits.*
import org.koin.android.ext.android.inject


class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private var tempC: Double? = 0.0
    private var tempF: Double? = 0.0

    private var captureImageUri: Uri? = null

    private val viewModel: HomeViewModel by inject()
    private val adapter: WeatherCastAdapter by lazy { WeatherCastAdapter() }
    private var mFusedLocationClient: FusedLocationProviderClient? = null

    private val mLocationRequest by lazy {
        LocationRequest.Builder(Long.MAX_VALUE)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()
    }

    private val requestPermissionLocation =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                if (checkGPSEnabled()) {
                    getLastLocation()
                } else {
                    requestGps()
                }

            } else {
                initWeaterViewModel("Egypt")
                showSnackbar("The Default Location will be Egypt")
            }
        }


    private val openGps =
        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                getLastLocation()
            } else {
                requestGps()
            }
        }


    private val openImageFromCamera =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it) {
                getImageFromMobile(captureImageUri!!)
            }
        }


    val requestPermissionCamera =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                dispatchTakePictureIntent()
            } else {
                showSnackbar("You need to allow  the camera permission  while using this app ")
            }
        }


    //region Get Current Location
    private fun checkPermissionLocation() {
        when {
            checkSelfPermission() -> {
                if (checkGPSEnabled()) {
                    getLastLocation()
                } else {
                    requestGps()
                }

            }
            shouldShowRequestPermission() -> {
                requestPermissionLocation()
            }
            else -> {
                requestPermissionLocation()
            }
        }

    }

    private fun checkSelfPermission(): Boolean {
        return ContextCompat
            .checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun shouldShowRequestPermission(): Boolean {
        return shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) &&
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private fun requestPermissionLocation() {
        requestPermissionLocation.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

    private fun checkGPSEnabled(): Boolean {
        val locationManager =
            (requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager)
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun requestGps() {


        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(mLocationRequest)
        val task = LocationServices.getSettingsClient(requireContext())
            .checkLocationSettings(builder.build())
        task.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                try {
                    val rae = e
                    val intent = IntentSenderRequest.Builder(rae.resolution).build()
                    openGps.launch(intent)
                } catch (sendEx: IntentSender.SendIntentException) {
                    //
                }
            }
        }


    }

    private fun initFusedLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

    }

    private fun getLastLocation() {
        if (isServicesOk()) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            mFusedLocationClient?.lastLocation?.addOnCompleteListener { task: Task<Location?> ->
                val location = task.result
                if (location == null) {
                    requestNewLocationData()
                } else {
                    initWeaterViewModel("${location.latitude}, ${location.longitude}")
                }
            }

        }
    }

    private fun isServicesOk(): Boolean {
        val googleApi = GoogleApiAvailability.getInstance()
        val result = googleApi.isGooglePlayServicesAvailable(requireContext())
        if (result == ConnectionResult.SUCCESS) {
            return true
        } else if (googleApi.isUserResolvableError(result)) {
            val dialog =
                googleApi.getErrorDialog(
                    this, result, Constant.SERVICES_LOCATION.ERROR_DIALOG_REQUEST
                ) {
                    showSnackbar("Canceled")

                }
            dialog?.show()
        } else {
            showSnackbar("Play services are required by this application")
        }
        return false
    }


    private fun requestNewLocationData() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        mFusedLocationClient?.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.getMainLooper()
        )
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation = locationResult.lastLocation
            initWeaterViewModel("${mLastLocation?.latitude}, ${mLastLocation?.longitude}")
        }
    }


    //endregion


    //region capture image

    private fun getImageFromMobile(uri: Uri) {
        val imageStream = requireActivity().contentResolver.openInputStream(uri)
        val selectedImage = BitmapFactory.decodeStream(imageStream)
        val encodedImage: String? = ImageUtils.encodeImage(selectedImage)
        binding.imIcon.imageBitmap(encodedImage)
    }


    private fun dispatchTakePictureIntent() {
        ImageUtils.createImageFile(requireContext())?.also {
            captureImageUri = FileProvider.getUriForFile(
                requireContext(),
                BuildConfig.APPLICATION_ID + ".provider",
                it
            )
            openImageFromCamera.launch(captureImageUri)
        }
    }

    private fun requestPermissionCamera() {
        requestPermissionCamera.launch(
            Manifest.permission.CAMERA
        )
    }


    private fun permissionCamera() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                dispatchTakePictureIntent()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                requestPermissionCamera()
            }
            else -> {
                requestPermissionCamera()
            }
        }
    }

    //endregion


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun initClicks() {
        binding.imConvert.setOnClickListener {

            Constant.CONVERT_UNIT.IS_FAHRENHEIT_TEMPERATURE =
                !Constant.CONVERT_UNIT.IS_FAHRENHEIT_TEMPERATURE
            when (Constant.CONVERT_UNIT.IS_FAHRENHEIT_TEMPERATURE) {
                true -> {
                    binding.tvDegree.text = "${tempF} °F"
                }
                false -> {

                    binding.tvDegree.text = "${tempC} °C"
                }
            }

            adapter.clickOnConvertUnit()
        }


        binding.imIcon.setOnClickListener {
            permissionCamera()
        }
    }

    override fun initViewModel() {
        listnerOnSucessWeather()
        listnerOnError()
        listnerOnLoading()
    }

    override fun onCreateInit() {
        initFusedLocation()
        initWeatherAdapter()
    }


    private fun initWeaterViewModel(query: String) {
        viewModel.weatherForeCast(query)
    }

    private fun listnerOnSucessWeather() {
        viewModel.weatherForeCast.observe(viewLifecycleOwner) {
            bindDataOnText(it)
            bindDataInAdapterWeather(it)
        }
    }

    private fun listnerOnError() {
        viewModel.generalError.observe(viewLifecycleOwner) {
            showSnackbar(it)
        }
    }

    private fun listnerOnLoading() {
        viewModel.loading.observe(viewLifecycleOwner) {
            DialogUtil.dialog(requireContext(), it)
        }
    }

    private fun bindDataOnText(response: ResponseWeatherForeCast) {
        tempF = response.current?.tempF
        tempC = response.current?.tempC
        binding.tvCityName.text = response.location?.region
        binding.tvDate.text = response.location?.localtime?.convertDate()
        binding.tvKind.text = "It’s a ${response.current?.condition?.text} day."
        binding.tvDegree.text = "${response.current?.tempF} °F"
        binding.tvWind.text = "${response.current?.windDegree} %"
        binding.tvHumdity.text = "${response.current?.humidity}"

        if (captureImageUri == null) {
            binding.imIcon.loadImage(response.current?.condition?.icon)

        }
    }

    private fun initWeatherAdapter() {
        binding.rcWeatherThreeDays.adapter = adapter
    }


    private fun bindDataInAdapterWeather(response: ResponseWeatherForeCast) {
        adapter.submitList(response.forecast?.forecastday)
    }

    override fun onStart() {
        super.onStart()
        checkPermissionLocation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mFusedLocationClient?.removeLocationUpdates(mLocationCallback)
    }

}