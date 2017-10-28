package guru.drako.eventer.alexa.controllers

import guru.drako.eventer.alexa.data.AlexaRequest
import guru.drako.eventer.alexa.data.AlexaResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class AlexaController {
  companion object {
    private val logger: Logger = LoggerFactory.getLogger(AlexaController::class.java)
  }

  @RequestMapping("api/alexa/eventer", method = [RequestMethod.POST])
  fun eventerSkill(@RequestBody alexaRequest: AlexaRequest) : AlexaResponse {
    logger.info("Got request: {}", alexaRequest)
    return AlexaResponse("Hello world")
  }
}
