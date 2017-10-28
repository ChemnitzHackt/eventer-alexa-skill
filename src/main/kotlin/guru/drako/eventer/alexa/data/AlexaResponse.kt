package guru.drako.eventer.alexa.data

data class AlexaResponse(
  val version: String = "1.0",
  val response: Response = Response()
) {
  data class Response(
    val shouldEndSession: Boolean = true,
    val outputSpeech: OutputSpeech? = OutputSpeech()
  ) {
    data class OutputSpeech(
      val type: Type = Type.PlainText,
      val text: String? = "",
      val ssml: String? = null
    ) {
      enum class Type {
        PlainText,
        Ssml
      }
    }
  }
}
