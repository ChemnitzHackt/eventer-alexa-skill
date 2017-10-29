package guru.drako.eventer.alexa.data

import com.fasterxml.jackson.annotation.JsonProperty

data class AlexaRequest(
  val version: String,
  val session: Session,
  val request: Request
) {
  data class Session(
    @field:JsonProperty("new")
    val isNew: Boolean,
    val sessionId: String,
    val application: Application,
    val user: User
  ) {
    data class Application(
      val applicationId: String
    )

    data class User(
      val userId: String
    )
  }

  data class Request(
    val type: Type,
    val requestId: String,
    val intent: Intent? = null,
    val locale: String,
    val timestamp: String,
    val reason: Reason? = null,
    val error: Error? = null
  ) {
    enum class Type {
      IntentRequest,
      SessionEndedRequest
    }

    enum class Reason {
      USER_INITIATED,
      ERROR,
      EXCEEDED_MAX_REPROMPTS
    }

    data class Error(
      val type: Type,
      val message: String
    ) {
      enum class Type {
        INVALID_RESPONSE,
        DEVICE_COMMUNICATION_ERROR,
        INTERNAL_ERROR
      }
    }

    data class Intent(
      val name: String,
      val slots: Map<String, KeyValue>? = null
    ) {
      data class KeyValue(
        val name: String,
        val value: String? = null
      )
    }
  }
}
