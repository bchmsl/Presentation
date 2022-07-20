package com.bchmsl.presentation_recyclerview_diffutil.extensions

import com.bchmsl.presentation_recyclerview_diffutil.R
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView


fun CircleImageView.setMaleImage(uri: String?){
    Glide.with(this).load(uri).placeholder(R.drawable.img_placeholder_male).into(this)
}

fun CircleImageView.setFemaleImage(uri: String?){
    Glide.with(this).load(uri).placeholder(R.drawable.img_placeholder_female).into(this)
}