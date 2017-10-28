package guru.drako.eventer.alexa

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import kotlin.test.assertTrue

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class ApplicationTest {
  @field:Autowired
  private lateinit var ctx: ApplicationContext

  @Test
  @DisplayName("DI container contains required beans")
  fun checkRequiredBeans() {
    assertTrue(ctx.containsBean("application"))
  }
}
