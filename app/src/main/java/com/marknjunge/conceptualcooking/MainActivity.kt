package com.marknjunge.conceptualcooking

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import android.view.ViewGroup
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Remove translucent status bar
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        // Set up list
        rvRecipes.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvRecipes.adapter = RecipeAdapter(listOfRecipes)

        // Use the dark vibrant color from the image as the placeholder
        val bitmap = getBitmapFromAsset(this, "sea_bass.jpg")!!
        bitmap.generatePalette { palette ->
            val colorDrawable = ColorDrawable(palette!!.getDarkVibrantColor(Color.parseColor("#000000")))
            Picasso.get().loadAsset("sea_bass.jpg").placeholder(colorDrawable).into(imgDish)
        }

        // Get the card's position on the screen
        // = it's top margin
        val cardPosPx = (recipesCard.layoutParams as ViewGroup.MarginLayoutParams).topMargin.toFloat()
        Timber.d("Card Position px: $cardPosPx")

        // Get the position of the top of the fab on the screen
        // = card's position - half the height of a fab
        val fabTopPosPx = cardPosPx - (56f / 2).toPx(resources.displayMetrics)
        Timber.d("fab Position px: $fabTopPosPx")

        val offsetPx = 72f.toPx(resources.displayMetrics)
        Timber.d("Offset px: $offsetPx")

        mainContent.viewTreeObserver.addOnScrollChangedListener {
            Timber.d("Scroll position ${mainContent.scrollY}")

            // Increase the visibility of the toolbar background based on the scroll position
            // Offset so that it is opaque before the card is at the top
            viewToolbarBackground.alpha = mainContent.scrollY / (cardPosPx - offsetPx)

            // Hide the fab when it's near the "toolbar"
            // Offset so that it is gone before the fab is at the top
            if (mainContent.scrollY > (fabTopPosPx - offsetPx)) {
                floatingActionButton.hide()
            } else {
                floatingActionButton.show()
            }

            // Change the title of the toolbar when the card is scrolled past
            if (isViewAbove(tvCardTitle, tvToolbarTitle)) {
                tvToolbarTitle.text = "Recipes"
            } else {
                tvToolbarTitle.text = "Ingredients"
            }
        }

        // Scroll to the top of the screen
        mainContent.smoothScrollTo(0, 0)
    }

    /**
     * Checks if the first view is higher than the second, in terms of the y value.
     */
    private fun isViewAbove(firstView: View, secondView: View): Boolean {
        val firstViewPos = IntArray(2)
        firstView.getLocationOnScreen(firstViewPos)

        val secondViewPos = IntArray(2)
        secondView.getLocationOnScreen(secondViewPos)

        // View1.y < View2.y
        return firstViewPos[1] < secondViewPos[1]
    }

    private fun isViewOverlapping(firstView: View, secondView: View): Boolean {
        val firstPosition = IntArray(2)
        firstView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        firstView.getLocationOnScreen(firstPosition)

        val secondPosition = IntArray(2)
        secondView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        secondView.getLocationOnScreen(secondPosition)

        return (firstPosition[0] < secondPosition[0] + secondView.measuredWidth
                && firstPosition[0] + firstView.measuredWidth > secondPosition[0]
                && firstPosition[1] < secondPosition[1] + secondView.measuredHeight
                && firstPosition[1] + firstView.measuredHeight > secondPosition[1])
    }

}
