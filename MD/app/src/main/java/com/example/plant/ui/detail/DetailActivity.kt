package com.example.plant.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.plant.R
import com.example.plant.ViewModelFactory
import com.example.plant.databinding.ActivityDetailBinding
import com.example.plant.pref.DataStoreViewModel
import com.example.plant.pref.UserPreference
import com.example.plant.pref.dataStore
import com.example.plant.ui.SectionPagerAdapter
import com.example.plant.ui.network.ApiConfig
import com.example.plant.ui.network.response.HistoryDetailResponse
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    private var description:String? = null
    private var causes:String? = null
    private var treatment:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading(true)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val pref = UserPreference.getInstance(this.dataStore)
        val datastoreViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            DataStoreViewModel::class.java)



        val detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        val id = intent.getStringExtra(ID)
        intent.removeExtra(ID)
        datastoreViewModel.getTokenKey().observe(this){
            detailViewModel.getDetail("$it","$id")
        }


        detailViewModel.detaillist.observe(this){
            if (it != null) {
                Glide.with(this)
                    .load("${it.imageUrl}")
                    .into(binding.imgDetail)
                binding.txtNama.text = it.diseasesName
                val percentage = "${it.percentage}"
                val percentageS = percentage.split(".")
                val percentageA = percentageS.get(1).substring(0, 2)
                binding.txtPercentage.text = "(${percentageS.get(0)}.$percentageA%)"
                showLoading(false)

                val sectionsPagerAdapter = SectionPagerAdapter(this@DetailActivity, "${it.description}", "${it.causes}", "${it.treatment}")
                val viewPager: ViewPager2 = findViewById(R.id.view_pager)
                viewPager.adapter = sectionsPagerAdapter
                val tabs: TabLayout = findViewById(R.id.tabs)
                TabLayoutMediator(tabs, viewPager) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
                supportActionBar?.elevation = 0f
            }
        }



        binding.imgBack.setOnClickListener{
            onBackPressed()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object{
        const val ID = "id"

        const val TAG = "DetailActivity"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.Description,
            R.string.causes,
            R.string.treatment
        )
    }
}