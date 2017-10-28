package guru.drako.eventer.alexa.controllers

import guru.drako.eventer.alexa.data.AlexaRequest
import guru.drako.eventer.alexa.data.AlexaResponse
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class AlexaController {
  @RequestMapping("api/alexa/eventer", method = [RequestMethod.POST])
  fun eventerSkill(@RequestBody alexaRequest: AlexaRequest) : AlexaResponse {
    return AlexaResponse("Hello world")
  }
}
