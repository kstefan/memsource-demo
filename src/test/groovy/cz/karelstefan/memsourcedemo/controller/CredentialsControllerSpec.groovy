package cz.karelstefan.memsourcedemo.controller

import com.jayway.jsonpath.JsonPath
import cz.karelstefan.memsourcedemo.domain.api.CredentialsPayload
import cz.karelstefan.memsourcedemo.domain.entity.CredentialsEntity
import cz.karelstefan.memsourcedemo.domain.memsource.LoginRequest
import cz.karelstefan.memsourcedemo.repository.CredentialsRepository
import cz.karelstefan.memsourcedemo.service.MemsourceRestClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import spock.lang.Specification
import spock.lang.Stepwise

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@Stepwise
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import([IntegrationTestConfiguration])
class CredentialsControllerSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    MemsourceRestClient memsourceRestClient

    @Autowired
    CredentialsRepository credentialsRepository

    def "GET /api/v1/credentials should return credentials"() {
        given:
        def credentials = new CredentialsEntity(username: "u1", password: "p1", token: "t1");
        credentialsRepository.saveAndFlush(credentials)

        when:
        def entity = restTemplate.getForEntity('/api/v1/credentials', String)

        then:
        entity.statusCode == HttpStatus.OK
        def json = JsonPath.parse(entity.body)
        json.read('$.username') == "u1"
        json.read('$.password') == "p1"

        cleanup:
        credentialsRepository.deleteAll()
    }

    def "GET /api/v1/credentials shouldn't find credentials"() {
        when:
        def entity = restTemplate.getForEntity('/api/v1/credentials', String)

        then:
        entity.statusCode == HttpStatus.OK
        def json = JsonPath.parse(entity.body)
        json.read('$.username') == null
        json.read('$.password') == null
    }

    def "POST /api/v1/credentials should update credentials"() {
        given:
        def loginRequest = new LoginRequest(userName: "u1", password: "p1")
        1 * memsourceRestClient.getToken(loginRequest) >> "t1"

        when:
        def entity = restTemplate.postForEntity(
                '/api/v1/credentials',
                new CredentialsPayload(username: loginRequest.userName, password: loginRequest.password),
                String)

        then:
        entity.statusCode == HttpStatus.OK
        def json = JsonPath.parse(entity.body)
        json.read('$.username') == "u1"
        json.read('$.password') == "p1"
        def credentials = credentialsRepository.findAll()
        credentials.size() == 1
        credentials[0].username == "u1"

        cleanup:
        credentialsRepository.deleteAll()
    }

}
