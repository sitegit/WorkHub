package com.example.core_ui.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core_ui.R
import com.example.core_ui.databinding.VacancyItemBinding
import java.text.SimpleDateFormat
import java.util.Locale

abstract class BaseVacanciesAdapter<T>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, BaseVacanciesAdapter.BaseViewHolder<T>>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val binding = VacancyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(binding, ::bindData)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    protected abstract fun bindData(binding: VacancyItemBinding, item: T)

    class BaseViewHolder<T>(
        private val binding: VacancyItemBinding,
        private val bindData: (VacancyItemBinding, T) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: T) {
            bindData(binding, item)
        }
    }

    protected fun formatPublishDate(context: Context, dateString: String): String {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateString)
            ?: return dateString
        val day = SimpleDateFormat("d", Locale.getDefault()).format(date)
        val month = SimpleDateFormat("MMMM", Locale("ru")).format(date)

        return context.getString(R.string.published, day, month)
    }
}
