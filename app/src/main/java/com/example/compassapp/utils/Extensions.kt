package com.example.compassapp.utils

import android.widget.Toast
import androidx.fragment.app.Fragment
import es.dmoral.toasty.Toasty

fun Fragment.showErrorToastLong(str: String) {
    Toasty.error(requireContext(), str, Toast.LENGTH_LONG).show()
}