import android.animation.ObjectAnimator
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class SwipeToRemoveHelper(private val recyclerView: RecyclerView) : RecyclerView.OnItemTouchListener {

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
        val child = recyclerView.findChildViewUnder(event.x, event.y)
        val removeText = child?.findViewById<TextView>(R.id.remove_text)

        removeText?.let {
            val swipeMinToShow = 200f
            val swipeMax = child.width / 3.0f
            val swipeDestinationX = child.width - event.x
            Log.d("PBK - swipe", "event.x: " + event.x + ", left: " + (child?.left ?: "null") + ", distanceX: " + distanceX)

            if (distanceX < 0) {
                it.visibility = View.GONE
            } else if (event.x > swipeMinToShow) {
                it.visibility = View.VISIBLE
            }
        }
    }
}
