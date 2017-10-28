package guru.drako.eventer.alexa.controllers

import guru.drako.eventer.alexa.data.AlexaRequest
import guru.drako.eventer.alexa.data.AlexaRequest.Request.Intent
import guru.drako.eventer.alexa.data.AlexaRequest.Request.Intent.KeyValue
import guru.drako.eventer.alexa.data.AlexaRequest.Request.Type.IntentRequest
import guru.drako.eventer.alexa.data.AlexaResponse
import guru.drako.eventer.alexa.data.AlexaResponse.Response
import guru.drako.eventer.alexa.data.AlexaResponse.Response.OutputSpeech
import guru.drako.eventer.alexa.errors.UnknownIntent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.format.datetime.standard.DateTimeContextHolder
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RestController
class AlexaController {
  companion object {
    private val logger: Logger = LoggerFactory.getLogger(AlexaController::class.java)
  }

  @RequestMapping("api/alexa/eventer", method = [RequestMethod.POST])
  fun eventerSkill(@RequestBody alexaRequest: AlexaRequest): AlexaResponse = when (alexaRequest.request.type) {
    IntentRequest -> onIntent(alexaRequest.request.intent!!)
  }

  private fun onIntent(intent: Intent): AlexaResponse = when (intent.name) {
    "ListIntent" -> onListIntent(intent.slots)
    else -> throw UnknownIntent("Intent ${intent.name} is not known.")
  }

  private fun onListIntent(slots: Map<String, KeyValue>?): AlexaResponse {
    val location: String = slots?.get("where")?.value ?: "chemnitz"
    val moment: String = slots?.get("when")?.value ?: LocalTime.now().format(DateTimeFormatter.ISO_DATE)

    logger.info("Handling ListIntent for location: {} date: {}", location, moment)

    return AlexaResponse(response = Response(outputSpeech = OutputSpeech(text = "Es ist nichts los.")))
  }
}
