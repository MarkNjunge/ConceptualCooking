package com.marknjunge.conceptualcooking

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_recipe_header.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        val recipes = listOf(
            Recipe("grilled.jpg", "Grilled sea bass", "8 min | 3 servings"),
            Recipe("sous_vide.jpg", "Sous vide sea bass with mash", "8 min | 3 servings"),
            Recipe("creamed.jpg", "Creamed sea bass", "8 min | 3 servings"),

            Recipe("grilled.jpg", "Grilled sea bass", "8 min | 3 servings"),
            Recipe("sous_vide.jpg", "Sous vide sea bass with mash", "8 min | 3 servings"),
            Recipe("creamed.jpg", "Creamed sea bass", "8 min | 3 servings"),
            Recipe("grilled.jpg", "Grilled sea bass", "8 min | 3 servings"),
            Recipe("sous_vide.jpg", "Sous vide sea bass with mash", "8 min | 3 servings"),
            Recipe("creamed.jpg", "Creamed sea bass", "8 min | 3 servings"),
            Recipe("grilled.jpg", "Grilled sea bass", "8 min | 3 servings"),
            Recipe("sous_vide.jpg", "Sous vide sea bass with mash", "8 min | 3 servings"),
            Recipe("creamed.jpg", "Creamed sea bass", "8 min | 3 servings")
        )

        rvRecipes.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvRecipes.adapter = RecipeAdapter(recipes)

        val bitmap = getBitmapFromAsset(this, "sea_bass.jpg")!!
        bitmap.generatePalette { palette ->
            val colorDrawable = ColorDrawable(palette!!.getDarkVibrantColor(Color.parseColor("#000000")))
            Picasso.get().loadAsset("sea_bass.jpg").placeholder(colorDrawable).into(imgDish)
        }

        scrollView.viewTreeObserver.addOnScrollChangedListener {
            viewFakeToolbar.alpha = scrollView.scrollY / 700f
            if (scrollView.scrollY > 700) {
                floatingActionButton.hide()
            } else {
                floatingActionButton.show()
            }
            if (isViewAbove(tvCardTitle, tvTitle)) {
                tvTitle.text = "Recipes"
            } else {
                tvTitle.text = "Ingredients"
            }
            Log.d("MNK", "${scrollView.scrollY}")
        }

        scrollView.smoothScrollTo(0, 0)
    }

    data class Recipe(val image: String, val name: String, val description: String)

    private fun isViewAbove(firstView: View, secondView: View): Boolean {
        val firstPosition = IntArray(2)
        val secondPosition = IntArray(2)

        firstView.getLocationOnScreen(firstPosition)
        secondView.getLocationOnScreen(secondPosition)

        Log.d("MNK", "${firstPosition[1]} ${secondPosition[1]}")
        Log.d("MNK", "${firstPosition[0]} ${secondPosition[0]}")

        return firstPosition[1] < secondPosition[1]
    }

    private fun isViewOverlapping(firstView: View, secondView: View): Boolean {
        val firstPosition = IntArray(2)
        val secondPosition = IntArray(2)

        firstView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        firstView.getLocationOnScreen(firstPosition)
        secondView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        secondView.getLocationOnScreen(secondPosition)

        return (firstPosition[0] < secondPosition[0] + secondView.measuredWidth
                && firstPosition[0] + firstView.measuredWidth > secondPosition[0]
                && firstPosition[1] < secondPosition[1] + secondView.measuredHeight
                && firstPosition[1] + firstView.measuredHeight > secondPosition[1])
    }

}
