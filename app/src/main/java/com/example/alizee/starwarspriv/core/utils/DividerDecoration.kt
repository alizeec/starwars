package com.example.alizee.starwarspriv.core.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import com.example.alizee.starwarspriv.R

class DividerDecoration(
    context: Context
) : RecyclerView.ItemDecoration() {

    private var mDivider: Drawable? = null
    private val paddingLeft = 0
    private val paddingRight = 0

    init {
        mDivider = ContextCompat.getDrawable(context, R.drawable.divider_center)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft + paddingLeft
        val right = parent.width - parent.paddingRight + paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + (mDivider?.intrinsicHeight ?: 0)

            mDivider?.let {
                it.setBounds(left, top, right, bottom)
                it.draw(c)
            }
        }
    }
}