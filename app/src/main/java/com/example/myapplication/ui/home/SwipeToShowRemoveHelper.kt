import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class SwipeToShowRemoveHelper(private val recyclerView: RecyclerView) : RecyclerView.OnItemTouchListener {

    private var initialRemoveTextX: Float = 0.0f

    private val gestureDetector: GestureDetector = GestureDetector(recyclerView.context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            handleScroll(e2, distanceX)
            return true
        }
    })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(e)
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        // Handle touch events
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        // Not needed
    }

    private fun handleScroll(event: MotionEvent, distanceX: Float) {
        val row = recyclerView.findChildViewUnder(event.x, event.y)
        val removeText = row?.findViewById<TextView>(R.id.remove_text) ?: return
        initialRemoveTextX = removeText.x
        removeText.let {
            val swipeMinToShow = it.width.coerceAtMost(row.width)
            if (distanceX < 0) {
                animate(it, removeText.x, row.width.toFloat(), false)
            } else if (event.x > swipeMinToShow) {
                animate(it, row.width.toFloat(), (row.width - it.width).toFloat(), true)
            }
        }
    }

    private fun animate(view: TextView, startX: Float, endX: Float, isShow: Boolean) {
        view.post {
            val widthAnimator = ObjectAnimator.ofFloat(startX, endX).apply {
                duration = 300
                addUpdateListener {
                    view.x = it.animatedValue as Float
                }
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(p0: Animator) {
                        if (isShow) {
                            view.x = startX
                            view.visibility = View.VISIBLE
                        }
                    }

                    override fun onAnimationEnd(p0: Animator) {
                        if (!isShow) {
                            view.visibility = View.GONE
                            view.x = initialRemoveTextX
                        }
                    }

                    override fun onAnimationCancel(p0: Animator) {}

                    override fun onAnimationRepeat(p0: Animator) {}

                })
            }
            widthAnimator.start()
        }
    }
}
