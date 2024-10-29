package com.example.feature_favourite.presentation.adapter

//class HeaderVacanciesAdapter : RecyclerView.Adapter<HeaderVacanciesAdapter.HeaderViewHolder>() {
//
//    private var vacancyCount: Int = 0
//
//    fun setVacancyCount(count: Int) {
//        vacancyCount = count
//        notifyItemChanged(0)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
//        return HeaderViewHolder(
//            VacanciesHeaderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        )
//    }
//
//    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
//        holder.bind()
//    }
//
//    override fun getItemCount() = 1
//
//    inner class HeaderViewHolder(private val binding: VacanciesHeaderItemBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind() {
//            binding.vacanciesCountTv.text = binding.root.context.resources.getQuantityString(
//               com.example.feature_main.R.plurals.total_vacancies, vacancyCount, vacancyCount
//            )
//        }
//    }
//}