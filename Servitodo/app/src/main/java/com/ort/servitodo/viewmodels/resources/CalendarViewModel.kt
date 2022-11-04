package com.ort.servitodo.viewmodels.resources

import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class CalendarViewModel : ViewModel() {

    private val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

    //-------------------------------------------------------------------------------
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

    //-------------------------------------------------------------------------------
    fun getToday() : String{
        val today = MaterialDatePicker.todayInUtcMilliseconds()

        this.calendar.timeInMillis = today
        val month = this.calendar.get(Calendar.MONTH) + 1
        val year = this.calendar.get(Calendar.YEAR)
        val day = this.calendar.get(Calendar.DAY_OF_MONTH)

        val datenow = "${day}-${month}-${year}"  //--> Trae el string con el formato "dd-mm-aaaa"

        return datenow
    }

    fun getDateInTimeInMillis(dateToParse : String) : Long{
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        val mDate: Date = dateFormat.parse(dateToParse)
        return mDate.time
    }

    fun getTodayInTimeMillis() : Long{
        return this.getDateInTimeInMillis(getToday())
    }

    fun getHourNow() : Int{
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY) //--> Obtiene la hora actual en Int
        return currentHour
    }

    fun getOnlyHour(hora : String) : Int{
        val str = hora
        val delim = ":"
        val hourParam = str.split(delim)[0].toInt() //--> Divide y se queda solo con la hora y lo parsea a Int
        return hourParam
    }
}