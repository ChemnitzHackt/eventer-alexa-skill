package guru.drako.eventer.alexa.data

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class EventerResponse(
  val events: Array<Event>
) {
  data class Event(
    val id: String,
    val title: String,
    val description: String,
    @field:JsonProperty("google_place_id")
    val googlePlaceId: String,
    @field:JsonProperty("starts_at")
    val startsAt: String
  )

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as EventerResponse

    if (!Arrays.equals(events, other.events)) return false

    return true
  }

  override fun hashCode(): Int {
    return Arrays.hashCode(events)
  }
}
