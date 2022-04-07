package com.cmoney.photoeditorsampleapplication

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * @param editText 需要為狀態 VISIBLE 的 EditText
 */
fun showKeyboard(editText: EditText){
    val imm = editText.context.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    editText.requestFocus()
    imm?.showSoftInput(editText, 0)
}