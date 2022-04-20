package ja.burhanrashid52.photoeditor

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


/**
 * Created by Burhanuddin Rashid on 14/05/21.
 *
 * @author <https:></https:>//github.com/burhanrashid52>
 */
internal class Text(
    private val mPhotoEditorView: ViewGroup,
    private val mMultiTouchListener: MultiTouchListener,
    private val mViewState: PhotoEditorViewState,
    private val mDefaultTextTypeface: Typeface?,
    private val mGraphicManager: GraphicManager
) : Graphic(
    context = mPhotoEditorView.context,
    graphicManager = mGraphicManager,
    viewType = ViewType.TEXT,
    layoutId = R.layout.view_photo_editor_text
) {
    private var mTextView: TextView? = null
    fun buildView(text: String?, styleBuilder: TextStyleBuilder?) {
        mTextView?.apply {
            this.text = text
            styleBuilder?.applyStyle(this)
        }
    }

    private fun setupGesture() {
        val onGestureControl = buildGestureController(mPhotoEditorView, mViewState)
        mMultiTouchListener.setOnGestureControl(onGestureControl)
        val rootView = rootView
        rootView.setOnTouchListener(mMultiTouchListener)
    }

    override fun setupView(rootView: View) {
        mTextView = rootView.findViewById(R.id.tvPhotoEditorText)
        mTextView?.run {
            gravity = Gravity.CENTER
            typeface = mDefaultTextTypeface
        }
    }

    override fun updateView(view: View?) {
        val currentBackgroundColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            (mTextView?.background as? GradientDrawable)?.color?.defaultColor ?: Color.TRANSPARENT
        } else {
            // TODO : might not be the right way to get the background color from GradientDrawable under the sdk 24
            (mTextView?.background as? ColorDrawable)?.color ?: Color.TRANSPARENT
        }

        val textInput = mTextView?.text.toString()
        val currentTextColor = mTextView?.currentTextColor ?: Color.WHITE
        val photoEditorListener = mGraphicManager.onPhotoEditorListener
        photoEditorListener?.onEditTextChangeListener(
            view,
            textInput,
            currentTextColor,
            currentBackgroundColor
        )
    }

    init {
        setupGesture()
    }
}