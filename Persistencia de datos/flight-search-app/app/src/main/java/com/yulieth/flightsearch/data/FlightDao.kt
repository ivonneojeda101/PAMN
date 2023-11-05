package com.yulieth.flightsearch.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightDao {
    @Query(
        """
        Select * from favorite
        ORDER BY id ASC
        """
    )
    //fun getAllAirports(): Flow<List<Airport>>
    suspend fun getAllFavorites(): List<Favorite>
    @Query(
        """
        Select * from favorite
        ORDER BY id ASC
        """
    )
    fun getAllFavoritesFlow(): Flow<List<Favorite>>

    @Query(
        """
        SELECT * FROM favorite
        WHERE departure_code = :departureCode
          AND destination_code = :destinationCode
        """
    )
    suspend fun getSingleFavorite(departureCode: String, destinationCode: String): Favorite


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteFlight(flight: Favorite)
    @Delete
    suspend fun deleteFavoriteFlight(flight: Favorite)



    @Query(
        """
        Select * from airport 
        ORDER BY id ASC 
        """
    )
    fun getAllAirportsFlow(): Flow<List<Airport>>
    @Query(
        """
        Select * from airport 
        ORDER BY id ASC 
        """
    )

    suspend fun getAllAirports(): List<Airport>


    @Query(
        """
    Select * from airport
    WHERE iata_code = :query OR name LIKE '%' || :query || '%'        
    ORDER BY name ASC
        """
    )
    fun getAllAirportsFlow(query: String): Flow<List<Airport>>

    @Query(
        """
    Select * from airport
    WHERE iata_code = :query OR name LIKE '%' || :query || '%'        
    ORDER BY name ASC
        """
    )

    suspend fun getAllAirports(query: String): List<Airport>

    @Query(
        """
    Select * from airport
    WHERE iata_code = :code
        """
    )

    suspend fun getAirportByCode(code: String): Airport
    @Query(
        """
    Select * from airport
    WHERE iata_code = :code
        """
    )
    fun getAirportByCodeFlow(code: String): Flow<Airport>

    @Query(
        """
    Select * from airport
    WHERE id = :id
        """
    )
    suspend fun getAirportById(id: Int): Airport
}