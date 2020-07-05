package com.example.compassapp.utils

import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import es.dmoral.toasty.Toasty

fun Fragment.showErrorToastLong(str: String) {
    Toasty.error(requireContext(), str, Toast.LENGTH_LONG).show()
}

fun EditText.getDoubleValue(): Double {
    return text.toString().toDouble()
}

fun EditText.isEmpty(): Boolean {
    return text.toString().trim()==""
}