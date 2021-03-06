package com.cmoney.photoeditorsampleapplication

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cmoney.photoeditorsampleapplication.databinding.ActivityMainBinding
import ja.burhanrashid52.photoeditor.OnPhotoEditorListener
import ja.burhanrashid52.photoeditor.OnSaveBitmap
import ja.burhanrashid52.photoeditor.PhotoEditor
import ja.burhanrashid52.photoeditor.ViewType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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


        photoEditor.setOnPhotoEditorListener(object : OnPhotoEditorListener {
            override fun onEditTextChangeListener(
                rootView: View?,
                text: String?,
                textColor: Int,
                backgroundColor: Int
            ) {
                binding.inputEditText.setText(text.orEmpty())
                showKeyboard(binding.inputEditText)
                binding.confirmTextView.setOnClickListener {
                    photoEditor.editText(
                        rootView ?: return@setOnClickListener,
                        binding.inputEditText.text.toString(),
                        Color.YELLOW,
                        backgroundColor
                    )
                }
            }

            override fun onAddViewListener(viewType: ViewType?, numberOfAddedViews: Int) {
//                TODO("Not yet implemented")
            }

            override fun onRemoveViewListener(viewType: ViewType?, numberOfAddedViews: Int) {
//                TODO("Not yet implemented")
            }

            override fun onRemoveTextViewListener(
                text: String?,
                textColor: Int,
                backgroundColor: Int
            ) {
                Log.d("remove","text = $text")
                Log.d("remove","textColor = $textColor")
                Log.d("remove","backgroundColor = $backgroundColor")
            }

            override fun onStartViewChangeListener(viewType: ViewType?) {
//                TODO("Not yet implemented")
            }

            override fun onStopViewChangeListener(viewType: ViewType?) {
//                TODO("Not yet implemented")
            }

            override fun onTouchSourceImage(event: MotionEvent?) {
//                TODO("Not yet implemented")
            }
        })

        lifecycleScope.launch {
            val bitmap = withContext(Dispatchers.IO) {
                getBitmapFromURL(IMAGE_URL)
            } ?: return@launch
            binding.photoEditorView.source.setImageBitmap(bitmap)
        }

        binding.addTextTextView.setOnClickListener {
            photoEditor.addText(
                binding.inputEditText.text.toString(),
                Color.GREEN,
                Color.BLUE
            )
            binding.inputEditText.text.clear()
        }

        binding.downloadTextView.setOnClickListener {
            photoEditor.saveAsBitmap(object : OnSaveBitmap {
                override fun onBitmapReady(saveBitmap: Bitmap?) {
                    binding.resultImageView.setImageBitmap(saveBitmap)
                }

                override fun onFailure(exception: Exception?) {
                    Log.e("PhotoEditor", "Failed to save Image")
                }
            })
        }
    }

    companion object {
        const val IMAGE_URL =
            "https://i.kym-cdn.com/photos/images/facebook/001/295/524/cda.jpg"
//            "https://i.pinimg.com/originals/59/54/b4/5954b408c66525ad932faa693a647e3f.jpg"
    }

}