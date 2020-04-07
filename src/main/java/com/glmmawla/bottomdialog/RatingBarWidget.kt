package com.glmmawla.bottomdialog

import android.content.Context
import android.util.AttributeSet
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.widget_rating_bar.view.*

class RatingBarWidget : ConstraintLayout {

    private var ratingListener: GetRatingSelectionListener? = null

    private val rateEmosNormal = mutableListOf<Int>(
        R.drawable.ic_terrible_emo_normal,
        R.drawable.ic_bad_emo_normal,
        R.drawable.ic_disappointing_emo_normal,
        R.drawable.ic_ok_emo_normal,
        R.drawable.ic_great_emo_normal
    )

    private val rateEmosSelected = mutableListOf<Int>(
        R.drawable.ic_terrible_emo,
        R.drawable.ic_bad_emo,
        R.drawable.ic_disappointing_emo,
        R.drawable.ic_ok_emo,
        R.drawable.ic_great_emo
    )

    val list: MutableList<RatingImos> = mutableListOf<RatingImos>()

    val ratebarAdapter = RatingBarAdapter(
        rateEmosNormal,
        rateEmosSelected,
        list
    ) { position, isSelected ->
        onRate(position, isSelected)
    }

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr)

    init {
        LayoutInflater.from(context).inflate(R.layout.widget_rating_bar, this, true)
        setupView()
    }

    private fun setupView() {

        rvRatingBar.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = ratebarAdapter
        }

        for (i in 0 until 5) {
            list.add(
                RatingImos(
                    rateEmosNormal[i],
                    rateEmosSelected[i],
                    true
                )
            )
        }

//        ratebarAdapter.notifyDataSetChanged()
    }

    private fun onRate(position: Int, selected: Boolean) {
        ratingListener?.apply {
            onRatingSelected(position, selected)
        }
    }

    fun getRating(listener: GetRatingSelectionListener) {
        ratingListener = listener
    }

    fun setRating(selectedRate: Int) {
        ratebarAdapter.selecetRating(selectedRate)
    }


    interface GetRatingSelectionListener {
        fun onRatingSelected(position: Int, selected: Boolean)
    }

    open class RatingBarAdapter(
        private val emoListNormal: List<Int>,
        private val emoListSelected: List<Int>,
        private val emoList: List<RatingImos>,
        private val callback: (Int, Boolean) -> Unit
    ) :
        RecyclerView.Adapter<RatingBarAdapterViewHolder>() {

        private var previousPosition = 0
        private val sparseBooleanArray = SparseBooleanArray()

        override fun onCreateViewHolder(
            viewGroup: ViewGroup,
            i: Int
        ): RatingBarAdapterViewHolder {
            val item: View = LayoutInflater.from(
                viewGroup.context
            )
                .inflate(R.layout.vh_rating_bar, viewGroup, false)

            return RatingBarAdapterViewHolder(
                item
            )
        }

        override fun onBindViewHolder(
            ratingBarAdapterViewHolder: RatingBarAdapterViewHolder,
            position: Int
        ) {
            ratingBarAdapterViewHolder.ivImo.setImageResource(emoListNormal[position])

            ratingBarAdapterViewHolder.ivImo.setImageResource(
                if (sparseBooleanArray[position, false]) emoListSelected[position]
                else emoListNormal[position]
            )

            ratingBarAdapterViewHolder.ivImo.setOnClickListener(OnClickListener {
                if (sparseBooleanArray[position, false]) {
                    sparseBooleanArray.delete(position)
                    ratingBarAdapterViewHolder.ivImo.setImageResource(emoListNormal[position])
                } else {
                    sparseBooleanArray.put(previousPosition, false)
                    sparseBooleanArray.put(position, true)
                    ratingBarAdapterViewHolder.ivImo.setImageResource(emoListSelected[position])
                }

                previousPosition = position
                notifyDataSetChanged()
                callback.invoke(position, sparseBooleanArray[position, false])
            })
        }

        override fun getItemCount(): Int {
            return emoListNormal.size
        }

        fun selecetRating(position: Int) {
            sparseBooleanArray.put(position, true)
            previousPosition = position
            notifyDataSetChanged()

            callback.invoke(position, true)
        }
    }

    class RatingBarAdapterViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var ivImo: ImageView = itemView.findViewById(R.id.ivRateImo)
    }

    data class RatingImos(
        val defaultIcon: Int,
        val selectedIcon: Int,
        val isSelected: Boolean = false
    )
}