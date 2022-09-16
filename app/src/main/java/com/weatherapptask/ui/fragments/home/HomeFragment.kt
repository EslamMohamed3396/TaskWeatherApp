package com.weatherapptask.ui.fragments.home


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.weatherapptask.databinding.FragmentHomeBinding
import com.weatherapptask.network.search.model.response.ResponseSearch
import com.weatherapptask.network.search.model.response.ResponseSearchItem
import com.weatherapptask.network.weatherForcast.model.response.ResponseWeatherForeCast
import com.weatherapptask.ui.base.BaseFragment
import com.weatherapptask.ui.fragments.home.adapter.searchAdapter.SearchCityAdapter
import com.weatherapptask.ui.fragments.home.adapter.weatherCastAdapter.WeatherCastAdapter
import com.weatherapptask.utilits.DialogUtil
import com.weatherapptask.utilits.convertDate
import com.weatherapptask.utilits.loadImage
import com.weatherapptask.utilits.textChanges
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject


class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewModel: HomeViewModel by inject()
    private val adapter: WeatherCastAdapter by lazy { WeatherCastAdapter() }
    private val searchAdapter: SearchCityAdapter by lazy { SearchCityAdapter(::clickOnSearchResult) }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun initClicks() {
        binding.imSearch.setOnClickListener {
            visiableLayoutSearch()
        }
        binding.searchLayout.imBack.setOnClickListener {
            hideLayoutSearch()
        }
        binding.searchLayout.imArrowUp.setOnClickListener {
            hideLayoutSearch()
        }
    }

    override fun initViewModel() {
        initWeaterViewModel("Egypt")
        listnerOnSucessWeather()
        listnerOnError()
        listnerOnLoading()
        listnerOnSucessSearch()
    }

    override fun onCreateInit() {
        initWeatherAdapter()
        initSearchAdapter()
        typingSearch()
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
        binding.tvCityName.text = response.location?.region
        binding.tvDate.text = response.location?.localtime?.convertDate()
        binding.tvKind.text = "It’s a ${response.current?.condition?.text} day."
        binding.tvDegree.text = "${response.current?.tempF} °F"
        binding.tvWind.text = "${response.current?.windDegree} %"
        binding.tvHumdity.text = "${response.current?.humidity}"
        binding.imIcon.loadImage(response.current?.condition?.icon)
    }

    private fun initWeatherAdapter() {
        binding.rcWeatherThreeDays.adapter = adapter
    }

    private fun initSearchAdapter() {
        binding.searchLayout.recyclerView.adapter = searchAdapter
    }

    private fun bindDataInAdapterWeather(response: ResponseWeatherForeCast) {
        adapter.submitList(response.forecast?.forecastday)
    }

    private fun bindDataInAdapterSearch(response: ResponseSearch) {
        searchAdapter.submitList(response)
    }

    private fun visiableLayoutSearch() {
        binding.searchLayout.root.visibility = View.VISIBLE
    }

    private fun visiableItemInLayoutSearch() {
        binding.searchLayout.recyclerView.visibility = View.VISIBLE
        binding.searchLayout.view.visibility = View.VISIBLE
        binding.searchLayout.imArrowUp.visibility = View.VISIBLE
    }

    private fun hideLayoutSearch() {
        binding.searchLayout.searchInputLayout.editText?.text?.clear()
        searchAdapter.submitList(null)
        binding.searchLayout.recyclerView.visibility = View.GONE
        binding.searchLayout.view.visibility = View.GONE
        binding.searchLayout.imArrowUp.visibility = View.GONE
        binding.searchLayout.root.visibility = View.GONE

    }

    private fun typingSearch() {
        binding.searchLayout.edtSearch?.textChanges()
            ?.debounce(2000)
            ?.onEach {
                if (it.toString().isNotEmpty()) {
                    initViewModelSearch(
                        it.toString()
                    )
                }
            }?.launchIn(lifecycleScope)

        binding.searchLayout.edtSearch?.doOnTextChanged { text, start: Int, before: Int, count: Int ->
            if (count > 0) {
                visiableLayoutSearch()
            } else {
                hideLayoutSearch()
            }
        }
    }

    private fun initViewModelSearch(search: String) {
        viewModel.search(search)
    }

    private fun listnerOnSucessSearch() {
        viewModel.search.observe(viewLifecycleOwner) {
            visiableItemInLayoutSearch()
            bindDataInAdapterSearch(it)
        }
    }

    private fun clickOnSearchResult(position: Int, item: ResponseSearchItem) {
        initWeaterViewModel(item.name!!)
        hideLayoutSearch()
    }

}