package com.whoisacat.screamingbutton

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScreamingButton(
    val postNumber: String,
    val isAlarmed: Boolean,
    val specification: String,
    val message: String? = null
): Parcelable {

}
