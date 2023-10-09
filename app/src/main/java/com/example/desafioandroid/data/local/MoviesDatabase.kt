package com.example.desafioandroid.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import com.example.desafioandroid.data.Movie
import com.example.desafioandroid.data.remote.ServerMovie
import kotlinx.coroutines.flow.Flow

/*
Se recomienda usar una entidad distinta a la obtenida del servicio remoto
si el remoto cambia entonces no cambiará la entidad local
 */
// Creamos un tabla LocalMovie en nuestra base de datos
@Database(entities = [LocalMovie:: class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieDao(): MoviesDao
}

@Dao
interface MoviesDao {
    // Con flow de courutines tenemos una base de datos reactiva que nos notifica cada vez que ocure un cambio
    @Query("SELECT * FROM LocalMovie")
    fun getMovies (): Flow<List<LocalMovie>>
    @Update
    suspend fun updateMovie(movie: LocalMovie)
    @Query("SELECT COUNT(*) FROM LocalMovie")
    suspend fun count(): Int
    @Insert
    suspend fun insertAll(serverMovieList: List<LocalMovie>)

}

@Entity
class LocalMovie(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String,
    val favorite: Boolean = false
)


fun LocalMovie.toMovie() = Movie(
    id = id,
    title = title,
    overview = overview,
    poster_path = poster_path,
    favorite = favorite
)
