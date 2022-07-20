package com.bchmsl.presentation_recyclerview_diffutil.extensions

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bchmsl.presentation_recyclerview_diffutil.R
import com.bchmsl.presentation_recyclerview_diffutil.model.Gender
import com.bchmsl.presentation_recyclerview_diffutil.model.Person
import org.xmlpull.v1.XmlPullParser


fun ViewGroup.setColor(person: Person){

    when (person.gender) {
        Gender.MALE -> this.setBackgroundResource(R.drawable.shape_rounded_rectangle_blue)
        Gender.FEMALE -> this.setBackgroundResource(R.drawable.shape_rounded_rectangle_red)
    }
}