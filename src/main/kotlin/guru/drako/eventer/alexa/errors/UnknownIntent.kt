package guru.drako.eventer.alexa.errors

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "unknown intent")
class UnknownIntent(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)
