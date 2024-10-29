package com.example.feature_main.presenation.adapter

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.example.core_ui.R
import com.example.core_ui.databinding.VacancyItemBinding
import com.example.core_ui.utils.BaseVacanciesAdapter
import com.example.feature_main.domain.entity.Vacancy

class VacanciesAdapter(
    private val onFavoriteClick: (Vacancy) -> Unit,
    private val onRespondClick: () -> Unit
) : BaseVacanciesAdapter<Vacancy>(VacancyDiffCallback()) {

    override fun bindData(
        binding: VacancyItemBinding,
        item: Vacancy
    ) {
        binding.apply {
            item.lookingNumber?.let { number ->
                candidatesTv.apply {
                    visibility = View.VISIBLE
                    text = root.context.resources.getQuantityString(
                        R.plurals.people_count, number, number
                    )
                }
            } ?: run { candidatesTv.visibility = View.GONE }

            likeIcon.apply {
                setImageResource(
                    if (item.isFavorite) R.drawable.icon_favourite_active
                    else R.drawable.icon_favourite
                )
                setOnClickListener { onFavoriteClick(item) }
            }

            positionTv.text = item.title
            cityTv.text = item.address
            companyTv.text = item.company
            workExperienceTv.text = item.experience
            dateOfPublicationTv.text = formatPublishDate(binding.root.context, item.publishedDate)
            vacancyItem.setOnClickListener { onRespondClick() }
        }
    }
}

class VacancyDiffCallback : DiffUtil.ItemCallback<Vacancy>() {
    override fun areItemsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: Vacancy, newItem: Vacancy): Any? {
        return if (oldItem.isFavorite != newItem.isFavorite) true else null
    }
}