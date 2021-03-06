package ja.burhanrashid52.photoeditor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import ja.burhanrashid52.photoeditor.MultiTouchListener.OnGestureControl

/**
 * Created by Burhanuddin Rashid on 14/05/21.
 *
 * @author <https:></https:>//github.com/burhanrashid52>
 */
internal abstract class Graphic(
    val context: Context,
    val layoutId: Int,
    val viewType: ViewType,
    val graphicManager: GraphicManager?) {

    val rootView: View

    open fun updateView(view: View?) {
        //Optional for subclass to override
    }

    init {
        if (layoutId == 0) {
            throw UnsupportedOperationException("Layout id cannot be zero. Please define a layout")
        }
        rootView = LayoutInflater.from(context).inflate(layoutId, null)
        setupView(rootView)
        setupRemoveView(rootView)
    }


    private fun setupRemoveView(baseView: View) {
        //We are setting tag as ViewType to identify what type of the view it is
        //when we remove the view from stack i.e onRemoveViewListener(ViewType viewType, int numberOfAddedViews);
        baseView.tag = viewType
        val imgClose = baseView.findViewById<ImageView>(R.id.imgPhotoEditorClose)
        val imgEdit = baseView.findViewById<ImageView>(R.id.imgPhotoEditorEdit)
        imgClose?.setOnClickListener { graphicManager?.removeView(this@Graphic) }
        imgEdit?.setOnClickListener { updateView(rootView) }
    }

    protected fun toggleSelection() {
        val frmBorder = rootView.findViewById<View>(R.id.frmBorder)
        val imgClose = rootView.findViewById<View>(R.id.imgPhotoEditorClose)
        val imgEdit = rootView.findViewById<View>(R.id.imgPhotoEditorEdit)
        if (frmBorder != null) {
            frmBorder.setBackgroundResource(R.drawable.square_border_tv)
            frmBorder.tag = true
        }
        if (imgClose != null) {
            imgClose.visibility = View.VISIBLE
        }
        if (imgEdit != null) {
            imgEdit.visibility = View.VISIBLE
        }
    }

    protected fun buildGestureController(
        viewGroup: ViewGroup,
        viewState: PhotoEditorViewState
    ): OnGestureControl {
        val boxHelper = BoxHelper(viewGroup, viewState)
        return object : OnGestureControl {
            override fun onClick() {
                boxHelper.clearHelperBox()
                toggleSelection()
                viewState.currentSelectedView = rootView
            }

            override fun onLongClick() {
                /* if you need long click to update view, just do it here. */
//                updateView(rootView)
            }
        }
    }

    open fun setupView(rootView: View) {}
}