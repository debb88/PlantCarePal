package com.example.plant.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.plant.ui.Treatment.TreatmentFragment
import com.example.plant.ui.causes.CausesFragment
import com.example.plant.ui.description.DescriptionFragment

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = DescriptionFragment()
            1 -> fragment = CausesFragment()
            2 -> fragment = TreatmentFragment()
        }
        return fragment as Fragment
    }
}