package com.example.core_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteVacanciesDao {

    @Query("SELECT * FROM favourite_vacancies")
    fun getFavouriteVacancies(): Flow<List<VacancyDb>>

    @Query("SELECT EXISTS (SELECT * FROM favourite_vacancies WHERE id=:vacancyId LIMIT 1)")
    fun observeIsFavourite(vacancyId: String): Flow<Boolean>

    @Query("SELECT COUNT(*) FROM favourite_vacancies")
    fun getFavouriteVacanciesCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavouriteVacancy(vacancy: VacancyDb)

    @Query("DELETE FROM favourite_vacancies WHERE id=:vacancyId")
    suspend fun removeFromFavourite(vacancyId: String)
}