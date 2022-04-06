package me.evgfilim1.amodeus

import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*
import me.evgfilim1.amodeus.api.plugins.*

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ configureRouting() }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Hello World!", response.content)
            }
        }
    }
}
