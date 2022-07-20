package com.bchmsl.presentation_recyclerview_diffutil.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.makeSnackbar(text: String){
    Snackbar.make(this, text, Snackbar.LENGTH_SHORT).show()
}