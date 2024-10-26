package com.example.feature_main.presenation.adapter

import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.feature_main.R
import com.example.feature_main.databinding.OfferItemBinding
import com.example.feature_main.domain.entity.Offer

class OffersAdapter : ListAdapter<Offer, OffersAdapter.OffersViewHolder>(ConceptTicketsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OffersViewHolder {
        return OffersViewHolder(
            OfferItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: OffersViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    inner class OffersViewHolder(
        private val binding: OfferItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val item = getItem(adapterPosition)
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))

                binding.root.context.startActivity(intent)
            }
        }


        fun bind(item: Offer) {
            binding.apply {
                if (item.id != null) {
                    offerIcon.setImageResource(getIconForId(item.id))
                    offerIcon.visibility = View.VISIBLE
                } else {
                    offerIcon.visibility = View.GONE
                }

                offerTitle.apply {
                    maxLines = if (item.button != null) 2 else 3
                    ellipsize = TextUtils.TruncateAt.END
                    text = item.title.trim()
                }

                if (item.button != null) {
                    offerButton.apply {
                        text = item.button
                        visibility = View.VISIBLE
                    }
                } else {
                    offerButton.visibility = View.GONE
                }
            }
        }

        private fun getIconForId(id: String): Int {
            return when (id) {
                "near_vacancies" -> R.drawable.near_vacancies
                "level_up_resume" -> R.drawable.level_up_resume
                "temporary_job" -> R.drawable.temporary_job
                else -> throw RuntimeException("Icon id undefined")
            }
        }
    }
}

class ConceptTicketsDiffCallback : DiffUtil.ItemCallback<Offer>() {
    override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean {
        return oldItem == newItem
    }
}