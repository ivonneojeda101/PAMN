
import android.content.Context
import com.yulieth.flightsearch.data.FlightDatabase
import com.yulieth.flightsearch.data.FlightRepository
import com.yulieth.flightsearch.data.OfflineFlightRepository


interface AppContainer {
    val flightRepository: FlightRepository
}
class AppDataContainer(private val context: Context) : AppContainer {

    override val flightRepository: FlightRepository by lazy {
        OfflineFlightRepository(FlightDatabase.getDatabase(context).flightDao())
    }
}