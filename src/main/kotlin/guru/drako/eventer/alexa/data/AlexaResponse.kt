package guru.drako.eventer.alexa.data

import guru.drako.eventer.alexa.data.AlexaResponse.ResponseAttributes.CardAttributes
import guru.drako.eventer.alexa.data.AlexaResponse.ResponseAttributes.OutputSpeechAttributes

data class AlexaResponse(
  var version: String = "1.0",
  var session: SessionAttributes = SessionAttributes(),
  var response: ResponseAttributes = ResponseAttributes()
) {
  constructor(outputSpeechText: String, shouldEndSession: Boolean = true) : this(
    response = ResponseAttributes(
      shouldEndSession = shouldEndSession,
      outputSpeech = OutputSpeechAttributes(text = outputSpeechText),
      card = if (shouldEndSession) CardAttributes(content = outputSpeechText) else null
    )
  )

  constructor(outputSpeechText: String, cardContent: String) : this(
    response = ResponseAttributes(
      outputSpeech = OutputSpeechAttributes(text = outputSpeechText),
      card = CardAttributes(content = cardContent)
    )
  )

  data class SessionAttributes(
    var memberId: Int = 0
  )

  data class ResponseAttributes(
    var shouldEndSession: Boolean = true,
    var outputSpeech: OutputSpeechAttributes? = OutputSpeechAttributes(),
    var card: CardAttributes? = CardAttributes()
  ) {
    data class OutputSpeechAttributes(
      var type: String = "PlainText",
      var text: String? = null,
      var ssml: String? = null
    )

    data class CardAttributes(
      var type: String = "Simple",
      var title: String? = null,
      var content: String? = null
    )

    data class RepromptAttributes(
      var outputSpeech: OutputSpeechAttributes = OutputSpeechAttributes()
    )
  }
}
