package cz.karelstefan.memsourcedemo.controller

import com.jayway.jsonpath.JsonPath
import cz.karelstefan.memsourcedemo.domain.entity.CredentialsEntity
import cz.karelstefan.memsourcedemo.domain.memsource.Project
import cz.karelstefan.memsourcedemo.domain.memsource.ProjectsResponse
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
class ProjectControllerSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    MemsourceRestClient memsourceRestClient

    @Autowired
    CredentialsRepository credentialsRepository

    def "GET /api/v1/projects should return projects"() {
        given:
        def credentials = new CredentialsEntity(username: "u1", password: "p1", token: "t1");
        credentialsRepository.saveAndFlush(credentials)
        1 * memsourceRestClient.getProjects(credentials.token) >> new ProjectsResponse(content: [
                new Project(uid: "1", name: "n1", status: "NEW", sourceLang: "en", targetLangs: ["es", "ru"]),
                new Project(uid: "2")
        ])
        when:
        def entity = restTemplate.getForEntity('/api/v1/projects', String)

        then:
        entity.statusCode == HttpStatus.OK
        def json = JsonPath.parse(entity.body)
        json.read('$[0].uid') == "1"
        json.read('$[0].name') == "n1"
        json.read('$[0].status') == "NEW"
        json.read('$[0].sourceLang') == "en"
        json.read('$[0].targetLangs[0]') == "es"
        json.read('$[0].targetLangs[1]') == "ru"
        json.read('$[1].uid') == "2"

        cleanup:
        credentialsRepository.deleteAll()
    }

    def "GET /api/v1/projects should return empty list"() {
        given:
        0 * memsourceRestClient.getProjects(_)

        when:
        def entity = restTemplate.getForEntity('/api/v1/projects', String)

        then:
        entity.statusCode == HttpStatus.OK
        def json = JsonPath.parse(entity.body)
        json.read('$') == []
    }

}
