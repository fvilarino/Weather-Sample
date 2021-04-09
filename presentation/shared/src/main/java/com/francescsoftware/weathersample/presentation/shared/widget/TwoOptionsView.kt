package com.francescsoftware.weathersample.presentation.shared.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.withStyledAttributes
import androidx.core.view.doOnPreDraw
import androidx.core.view.updateLayoutParams
import com.francescsoftware.weathersample.presentation.shared.R
import com.francescsoftware.weathersample.presentation.shared.databinding.TwoOptionsViewBinding
import com.google.android.material.card.MaterialCardView

enum class ToggleOptions {
    LEFT,
    RIGHT;

    companion object {
        fun fromOrdinal(ordinal: Int) = values().getOrNull(ordinal) ?: LEFT
    }
}

typealias ToggleCallback = (ToggleOptions) -> Unit

class TwoOptionsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val binding = TwoOptionsViewBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    private var callback: ToggleCallback? = null
    private var option: ToggleOptions? = null

    init {
        var initialSelected = ToggleOptions.LEFT
        var leftLabel: String? = null
        var rightLabel: String? = null
        if (attrs != null) {
            context.withStyledAttributes(attrs, R.styleable.TwoOptionsView) {
                val ordinal = getInteger(
                    R.styleable.TwoOptionsView_selectedOption,
                    ToggleOptions.LEFT.ordinal
                )
                initialSelected = ToggleOptions.fromOrdinal(ordinal)
                leftLabel = getString(R.styleable.TwoOptionsView_leftLabel)
                rightLabel = getString(R.styleable.TwoOptionsView_rightLabel)
            }
        }
        check (leftLabel != null && rightLabel != null) {
            "Labels must be provided"
        }
        binding.left.text = leftLabel
        binding.right.text = rightLabel
        binding.left.isSelected = initialSelected == ToggleOptions.LEFT
        binding.right.isSelected = initialSelected == ToggleOptions.RIGHT
        binding.left.setOnClickListener {
            binding.left.isSelected = true
            binding.right.isSelected = false
            if (option != ToggleOptions.LEFT) {
                option = ToggleOptions.LEFT
                callback?.invoke(ToggleOptions.LEFT)
            }
        }
        binding.right.setOnClickListener {
            binding.left.isSelected = false
            binding.right.isSelected = true
            if (option != ToggleOptions.RIGHT) {
                option = ToggleOptions.RIGHT
                callback?.invoke(ToggleOptions.RIGHT)
            }
        }
    }

    fun setCallback(toggleCallback: ToggleCallback?) {
        callback = toggleCallback
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.root.doOnPreDraw {
            radius = height.toFloat() / 2f
            val leftWidth = binding.left.width
            val rightWidth = binding.right.width
            if (leftWidth > rightWidth) {
                binding.right.updateLayoutParams<ViewGroup.LayoutParams> {
                    width = leftWidth
                }
            } else {
                binding.left.updateLayoutParams<ViewGroup.LayoutParams> {
                    width = rightWidth
                }
            }
        }
    }
}
