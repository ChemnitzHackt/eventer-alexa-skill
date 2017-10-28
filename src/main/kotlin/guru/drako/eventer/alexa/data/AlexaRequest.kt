package guru.drako.eventer.alexa.data

import com.fasterxml.jackson.annotation.JsonProperty

data class AlexaRequest(
  val version: String,
  val session: SessionAttributes,
  val request: RequestAttributes
) {
  data class SessionAttributes(
    val sessionId: String,
    val application: ApplicationAttributes,
    val attributes: SessionCustomAttributes,
    val user: UserAttributes,

    @field:JsonProperty("new")
    val isNew: Boolean
  ) {
    data class ApplicationAttributes(
      val applicationId: String
    )

    data class SessionCustomAttributes(
      val memberId: Int? = null
    )

    data class UserAttributes(
      val userId: String,
      val accessToken: String
    )
  }

  data class RequestAttributes(
    val type: String,
    val requestId: String,
    val timestamp: String,
    val intent: IntentAttributes,
    val reason: String
  ) {
    data class IntentAttributes(
      val name: String,
      val slots: Array<Any?>
    )
  }
}
