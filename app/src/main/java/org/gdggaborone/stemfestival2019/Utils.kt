package org.gdggaborone.stemfestival2019

import android.text.format.DateUtils
import org.joda.time.DateTime
import java.util.*

/**
 * Created by dan on 23/06/17.
 */

object Utils {

    //val date: Date? = null

    fun getDate(date: Date): String {
        val dateTime = DateTime(date)
        return DateUtils.getRelativeTimeSpanString(dateTime.millis,
                System.currentTimeMillis(),
                DateUtils.SECOND_IN_MILLIS).toString().toLowerCase()
    }

}
