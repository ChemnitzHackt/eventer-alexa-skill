package guru.drako.eventer.alexa.controllers

import guru.drako.eventer.alexa.data.AlexaRequest
import guru.drako.eventer.alexa.data.AlexaRequest.Request.Intent
import guru.drako.eventer.alexa.data.AlexaRequest.Request.Intent.KeyValue
import guru.drako.eventer.alexa.data.AlexaRequest.Request.Type.IntentRequest
import guru.drako.eventer.alexa.data.AlexaResponse
import guru.drako.eventer.alexa.data.EventerResponse
import guru.drako.eventer.alexa.errors.UnknownIntent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import java.time.*
import java.time.format.DateTimeFormatter

@RestController
class AlexaController {
  companion object {
    private val logger: Logger = LoggerFactory.getLogger(AlexaController::class.java)
  }

  @RequestMapping("api/alexa/eventer", method = [RequestMethod.POST])
  fun eventerSkill(@RequestBody alexaRequest: AlexaRequest): AlexaResponse {
    val response = when (alexaRequest.request.type) {
      IntentRequest -> onIntent(alexaRequest.request.intent!!)
    }

    logger.info("Handled request: {}\nResponded with: {}", alexaRequest, response)

    return response
  }

  private fun onIntent(intent: Intent): AlexaResponse = when (intent.name) {
    "ListIntent" -> onListIntent(intent.slots)
    else -> throw UnknownIntent("Intent ${intent.name} is not known.")
  }

  private fun onListIntent(slots: Map<String, KeyValue>?): AlexaResponse {
    val location: String = slots?.get("where")?.value ?: "chemnitz"
    val moment: ZonedDateTime = slots?.get("when")?.value?.let {
      LocalDate.parse(it).atTime(0, 0).atZone(ZoneId.of("UTC"))
    } ?: ZonedDateTime.now(ZoneId.of("UTC"))

    logger.info("Handling ListIntent for location: {} date: {}", location, moment)

    if (location != "chemnitz") {
      return AlexaResponse.plainText("Aktuelle können nur Veranstaltungen in Chemnitz gesucht werden.")
    }

    val start: Long = moment.toInstant().toEpochMilli()
    val end: Long = moment.toLocalDateTime()
      .withHour(23)
      .withMinute(59)
      .withSecond(59)
      .toInstant(ZoneOffset.UTC).toEpochMilli()

    val requestString = "https://api.my-eventer.de/v1/events?starts_at_min=$start&starts_at_max=$end"
    logger.info("Sending eventer request: {}", requestString)

    val rest = RestTemplate()
    val response: EventerResponse? =
      rest.getForObject(
        requestString,
        EventerResponse::class.java
      )

    logger.info("Received eventer response: {}", response)

    if (response == null || response.events.size == 0) {
      return AlexaResponse.plainText("Es ist nichts los.")
    }

    val events = response.events.distinctBy { it.title }
    val eventString = events.asSequence().map { "<s>${it.title}</s>" }.joinToString(", ")

    val prefix: String =
      if (events.size == 1) "Es wurde eine Veranstaltung"
      else "Es wurden ${events.size} Veranstaltungen"

    return AlexaResponse.speak(
      cardText = "$prefix gefunden.",
      ssml = "<s>$prefix:</s> $eventString"
    )
  }
}
