package com.glmmawla.bottomdialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getColorStateList
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.glmmawla.bottomdialog.databinding.BottomActionDialogBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.Serializable

class BottomActionViewBottom : BottomSheetDialogFragment() {

    private lateinit var binding : BottomActionDialogBinding

    private var clickListener: BottomDialogClickListener? = null
    private var positiveClickListener: BottomDialogPositiveClickListener? = null
    private var negativeClickListener: BottomDialogNegativeClickListener? = null
    private var rateSelectionListener: BottomDialogRateSelectionListener? = null

    private var data: BottomActionDialog.DialogBuilder.Build? = null
    private var bottomSheetBehavior : BottomSheetBehavior<ConstraintLayout>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.bottom_action_dialog, container, false)
        binding = BottomActionDialogBinding.bind(view)
        if (data?.isShowFullScreen == true){

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        super.onViewCreated(view, savedInstanceState)

        isCancelable = false

        getIntentData()
        setupView()

        binding.btnPositive.setOnClickListener {
            positiveClickListener?.let {
                it.onPositiveButtonClick(dialog)
            }
        }

        binding.btnNegative.setOnClickListener {
            if (negativeClickListener == null) {
                dismiss()
            }
            negativeClickListener?.let {
                it.onNegativeButtonClick(dialog)
            }
        }

        /*binding.ratingBar?.getRating(object : RatingBarWidget.GetRatingSelectionListener {
            override fun onRatingSelected(position: Int, selected: Boolean) {
                rateSelectionListener?.onSelection(dialog, position)
            }
        })*/
    }

    override fun onPause() {
        dismiss()
        super.onPause()
    }

    private fun getIntentData() {
        if (arguments?.containsKey(DIALOG_DATA) == true) {
            data = arguments?.getSerializable(DIALOG_DATA) as BottomActionDialog.DialogBuilder.Build
        }
    }

    private fun setupView() {
        data?.apply {
            isShowPositiveButton.apply {
                binding.btnPositive.visibility = if (this) View.VISIBLE else View.GONE
            }

            title.let { dialogTitle ->
                var title: String
                if (dialogTitle is Int) {
                    title = getString(dialogTitle)
                } else {
                    title = dialogTitle.toString()
                }
                binding.tvDialogTitle.text = title
            }

            subTitle?.apply {
                var subTitle: String
                if (this is Int) {
                    subTitle = getString(this)
                } else {
                    subTitle = this.toString()
                }
                binding.tvDialogMsg.visibility = View.VISIBLE
                binding.tvDialogMsg.text = subTitle
            }

            setCancelable(isCancelable)

            positiveButtonName?.let { binding.btnPositive.setText(it) }
            positiveButtonTitle?.let { binding.btnPositive.setText(it) }
            negativeButtonName?.let {
                binding.btnNegative.setText(it)
                binding.btnNegative.show()
            }

            negativeButtonTitle?.let {
                binding.btnNegative.setText(it)
                binding.btnNegative.show()
            }

            positiveClickListener?.let {
                setPositiveButtonListener(it)
            }

            negativeClickListener?.let {
                setNegativeButtonListener(it)
            }

            isShowRating.apply {
//                binding.ratingBar.visibility = if (this) View.VISIBLE else View.GONE
                data?.ratingSelectionListener?.let { setRateSelectionListener(it) }
            }

            data?.topBannerUrl?.let{
                setTopBanner(isShowBanner, data?.topBannerUrl)
            }

            data?.topBannerRes?.let{
                setTopBanner(isShowBanner, data?.topBannerRes)
            }

            circularBannerUrl?.apply {
                setCircuralBanner(this)
            }
            circularBannerRes?.apply {
                binding.ivCircularBanner.visibility = View.VISIBLE

                binding.ivCircularBanner.setImageResource(this)
            }

            data?.negativeButtonColor?.let {
                binding.btnNegative?.apply {
                    setTextColor(getColor(this.context, R.color.white))
                    strokeColor = getColorStateList(this.context, it)
                    backgroundTintList = getColorStateList(this.context, it)
                }
            }

            data?.positiveButtonColor?.let {
                binding.btnPositive?.apply {
                    setTextColor(getColor(this.context, R.color.white))
                    strokeColor = getColorStateList(this.context, it)
                    backgroundTintList = getColorStateList(this.context, it)
                }
            }

            data?.customView?.let {
                binding.llCustomView.show()
                binding.llCustomView.addView(it)
            }
        }
    }

    private fun setCircuralBanner(imageUrl: String) {
        context?.let {
            val glide = Glide.with(it)
                .load(imageUrl)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.color.gray)


            val requestOptions = RequestOptions()
                .transform(
                    CenterCrop(),
                    RoundedCorners(resources.getDimensionPixelOffset(R.dimen.size_16dp))
                )

            glide.into(binding.ivCircularBanner)

            binding.ivCircularBanner.visibility = View.VISIBLE
        }
    }

    private fun setTopBanner(isShown: Boolean, imageUrl: String? = null) {
        binding.ivDialogBanner.visibility = if (isShown) View.VISIBLE else View.GONE

        context?.let {
            val glide = Glide.with(it)
                .load(imageUrl)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.color.gray)


            val requestOptions = RequestOptions()
                .transform(
                    CenterCrop(),
                    RoundedCorners(resources.getDimensionPixelOffset(R.dimen.size_16dp))
                )

            glide.apply(requestOptions)
            glide.into(binding.ivDialogBanner)
        }
    }

    private fun setTopBanner(isShown: Boolean, @DrawableRes imageRes: Int? = null) {
        binding.ivDialogBanner.visibility = if (isShown) View.VISIBLE else View.GONE

        context?.let {
            val glide = Glide.with(it)
                .load(imageRes)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.color.gray)


            val requestOptions = RequestOptions()
                .transform(
                    CenterCrop(),
                    RoundedCorners(resources.getDimensionPixelOffset(R.dimen.size_16dp))
                )

            glide.apply(requestOptions)
            glide.into(binding.ivDialogBanner)
        }
    }

    private fun setDialogButtonListener(listener: BottomDialogClickListener) {
        clickListener = listener
    }

    private fun setPositiveButtonListener(listener: BottomDialogPositiveClickListener) {
        positiveClickListener = listener
    }

    private fun setNegativeButtonListener(listener: BottomDialogNegativeClickListener) {
        negativeClickListener = listener
    }

    private fun setRateSelectionListener(listener: BottomDialogRateSelectionListener) {
        rateSelectionListener = listener
    }

    companion object {

        const val DIALOG_DATA = "data"

        fun newInstance(data: BottomActionDialog.DialogBuilder.Build): BottomActionViewBottom {
            val fragment = BottomActionViewBottom()
            val bundle = Bundle()
            bundle.putSerializable(DIALOG_DATA, data)
            fragment.arguments = bundle

            return fragment
        }
    }

    interface BottomDialogClickListener : Serializable {
        fun onPositiveButtonClick(dialog: Dialog?)
        fun onNegativeButtonClick(dialog: Dialog?)
    }

    interface BottomDialogPositiveClickListener : Serializable {
        fun onPositiveButtonClick(dialog: Dialog?)
    }

    interface BottomDialogNegativeClickListener : Serializable {
        fun onNegativeButtonClick(dialog: Dialog?)
    }

    interface BottomDialogRateSelectionListener : Serializable {
        fun onSelection(dialog: Dialog?, rate: Int)
    }
}