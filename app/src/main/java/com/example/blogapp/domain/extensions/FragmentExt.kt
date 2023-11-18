package com.example.blogapp.domain.extensions

import androidx.fragment.app.Fragment

fun Fragment.onBackPressed() {
    requireActivity().onBackPressed()
}