package com.cmoney.photoeditorsampleapplication

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cmoney.photoeditorsampleapplication.databinding.ActivityMainBinding
import ja.burhanrashid52.photoeditor.PhotoEditor

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        val photoEditor = PhotoEditor.Builder(this, binding.photoEditorView)
            .setPinchTextScalable(true) // set flag to make text scalable when pinch
            //.setDefaultTextTypeface(mTextRobotoTf)
            //.setDefaultEmojiTypeface(mEmojiTypeFace)
            .build() // build photo editor sdk


        binding.confirmTextView.setOnClickListener {
            photoEditor.addText(Typeface.DEFAULT_BOLD, binding.inputEditText.text.toString(), Color.RED)
            binding.inputEditText.text.clear()
        }
    }

}