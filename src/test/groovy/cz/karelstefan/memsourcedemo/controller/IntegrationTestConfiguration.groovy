package cz.karelstefan.memsourcedemo.controller

import cz.karelstefan.memsourcedemo.service.MemsourceRestClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import spock.mock.DetachedMockFactory

import javax.sql.DataSource

@Configuration
class IntegrationTestConfiguration {

    private final detachedMockFactory = new DetachedMockFactory()

    @Bean
    MemsourceRestClient memsourceRestClient() {
        detachedMockFactory.Mock(MemsourceRestClient)
    }

    @Bean
    public DataSource dataSource() {
        new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }
}
