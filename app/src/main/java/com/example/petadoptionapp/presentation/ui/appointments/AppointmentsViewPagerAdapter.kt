package com.example.petadoptionapp.presentation.ui.appointments

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.petadoptionapp.presentation.ui.appointments.past_appointments.PastAppointmentsFragment
import com.example.petadoptionapp.presentation.ui.appointments.upcoming_appointments.UpcomingAppointmentsFragment

class AppointmentsViewPagerAdapter(
    fragment: Fragment,
    private val tabs: List<EAppointmentsTab> = listOf(
        EAppointmentsTab.TAB_UPCOMING,
        EAppointmentsTab.TAB_PAST
    ),
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = tabs.size

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> UpcomingAppointmentsFragment()
        1 -> PastAppointmentsFragment()
        else -> throw IllegalStateException("Invalid position")
    }

    fun getTitle(position: Int): Int =
        tabs.getOrNull(position)?.titleResourceId ?: throw IllegalStateException("Invalid position")
}