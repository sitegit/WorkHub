package com.example.core_ui.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacingItemDecorator(
    private val context: Context,
    private val spacingHorizontal: Int = 0,
    private val spacingVertical: Int = 0
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = spacingHorizontal.toPx(context)
        outRect.right = spacingHorizontal.toPx(context)
        outRect.top = spacingVertical.toPx(context)
        outRect.bottom = spacingVertical.toPx(context)
    }
}