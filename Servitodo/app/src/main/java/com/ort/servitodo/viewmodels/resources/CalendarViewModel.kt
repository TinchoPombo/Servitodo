package com.ort.servitodo.viewmodels.resources

import android.icu.util.Calendar
import android.icu.util.GregorianCalendar
import android.icu.util.TimeZone
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import kotlin.time.milliseconds

class CalendarViewModel : ViewModel() {

    private val calendar = Calendar.getInstance()

    //-------------------------------------------------------------------------------

    /*private fun constraint() : CalendarConstraints.Builder{
        val today = MaterialDatePicker.todayInUtcMilliseconds()
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

        calendar.timeInMillis = today
        calendar[Calendar.MONTH] = Calendar.JANUARY
        val janThisYear = calendar.timeInMillis

        calendar.timeInMillis = today
        calendar[Calendar.MONTH] = Calendar.DECEMBER
        val decThisYear = calendar.timeInMillis

        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setStart(janThisYear)
                .setEnd(decThisYear)
                .setValidator(DateValidatorPointForward.now())

        return constraintsBuilder
    }*/

    fun calendar(fm : FragmentManager) : MaterialDatePicker<Long>{

        val today = MaterialDatePicker.todayInUtcMilliseconds()

        val constraintsBuilder = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())

        /* val constraintsBuilder = constraint() */

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Selecciona una fecha")
            .setSelection(today)
            .setCalendarConstraints(constraintsBuilder.build())
            .build()
        datePicker.show(fm, "datePicker")

        return datePicker
    }



}