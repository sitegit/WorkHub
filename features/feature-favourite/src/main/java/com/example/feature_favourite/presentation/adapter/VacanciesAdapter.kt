package com.example.feature_favourite.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core_ui.R.plurals
import com.example.core_ui.R.string
import com.example.feature_favourite.databinding.VacancyItemBinding
import com.example.feature_favourite.domain.Vacancy
import java.text.SimpleDateFormat
import java.util.Locale

class VacanciesAdapter(
    private val onFavoriteClick: (Vacancy) -> Unit,
    private val onRespondClick: () -> Unit
) : ListAdapter<Vacancy, VacanciesAdapter.VacancyViewHolder>(VacancyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        return VacancyViewHolder(
            VacancyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    inner class VacancyViewHolder(
        private val binding: VacancyItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(vacancy: Vacancy) {
            binding.apply {
                vacancy.lookingNumber?.let { number ->
                    candidatesTv.apply {
                        visibility = View.VISIBLE
                        text = root.context.resources.getQuantityString(
                            plurals.people_count, number, number
                        )
                    }
                } ?: run { candidatesTv.visibility = View.GONE }

                likeIcon.apply {
                    setImageResource(
                        if (vacancy.isFavorite) com.example.core_ui.R.drawable.icon_favourite_active
                        else com.example.core_ui.R.drawable.icon_favourite
                    )
                    setOnClickListener { onFavoriteClick(vacancy) }
                }

                positionTv.text = vacancy.title
                cityTv.text = vacancy.address
                companyTv.text = vacancy.company
                workExperienceTv.text = vacancy.experience
                dateOfPublicationTv.text = formatPublishDate(vacancy.publishedDate)

                vacancyItem.setOnClickListener { onRespondClick() }
            }
        }

        private fun formatPublishDate(dateString: String): String {
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .parse(dateString) ?: return dateString

            val day = SimpleDateFormat("d", Locale.getDefault()).format(date)
            val month = SimpleDateFormat("MMMM", Locale("ru")).format(date)

            return binding.root.context.getString(string.published, day, month)
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