package com.ort.servitodo.viewmodels.resources

import android.icu.util.Calendar
import android.icu.util.GregorianCalendar
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import kotlin.time.milliseconds

class CalendarViewModel : ViewModel() {

    private val calendar = Calendar.getInstance()

    //-------------------------------------------------------------------------------
/*
    private fun limitRange(today : Long): CalendarConstraints.Builder {

        val constraintsBuilderRange = CalendarConstraints.Builder()

        val calendarStart: Calendar = GregorianCalendar.getInstance()
        val calendarEnd: Calendar = GregorianCalendar.getInstance()

        val year = 2022

        calendarStart.set(Calendar.YEAR, Calendar.MONTH, today.toInt())
        calendarEnd.set(Calendar.YEAR, Calendar.MONTH+2, today.toInt())

        val minDate = calendarStart.timeInMillis
        val maxDate = calendarEnd.timeInMillis

        constraintsBuilderRange.setStart(minDate)
        constraintsBuilderRange.setEnd(maxDate)
        constraintsBuilderRange.setValidator(DateValidatorPointForward.now())
        //constraintsBuilderRange.setValidator(RangeValidator(minDate, maxDate))

        return constraintsBuilderRange
    }
*/

    fun calendar(fm : FragmentManager) : MaterialDatePicker<Long>{

        val today = MaterialDatePicker.todayInUtcMilliseconds()

        val constraintsBuilder = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Selecciona una fecha")
            .setSelection(today)
            .setCalendarConstraints(constraintsBuilder.build())
            .build()
        datePicker.show(fm, "datePicker")

        return datePicker
    }



}