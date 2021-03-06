package com.jakehschwartz.finatra.swagger

import com.twitter.finagle.http.{Response, Status}
import com.twitter.finatra.http.EmbeddedHttpServer

class DocsControllerTest extends SampleAppBaseTest {

  override lazy val server: EmbeddedHttpServer = makeServer(
    serverName = "docsControllerServer")

  private val swaggerUrl: String =
    s"/docs/swagger-ui/${BuildInfo.swaggerUIVersion}/index.html?url=/swagger.json"

  test("sampleController: docs endpoint should return 307") {
    val expectedLocation: String =
      s"http://${server.externalHttpHostAndPort}$swaggerUrl"
    val response: Response =
      server.httpGet("/docs", andExpect = Status.TemporaryRedirect)
    response.headerMap("Location") shouldBe expectedLocation
  }

  test("sampleController: docs endpoint should return 200 from full URL") {
    server.httpGet(swaggerUrl, andExpect = Status.Ok)
  }

}
