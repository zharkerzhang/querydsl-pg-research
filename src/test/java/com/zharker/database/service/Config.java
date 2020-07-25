package com.zharker.database.service;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zharker.database.data.customized.CustomRepositoryFactoryBean;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.config.store.HttpProxyFactory;
import de.flapdoodle.embed.process.io.directories.FixedPath;
import de.flapdoodle.embed.process.runtime.Network;
import de.flapdoodle.embed.process.store.PostgresArtifactStoreBuilder;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.yandex.qatools.embed.postgresql.*;
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig;
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig;
import ru.yandex.qatools.embed.postgresql.config.PostgresDownloadConfigBuilder;
import ru.yandex.qatools.embed.postgresql.config.RuntimeConfigBuilder;
import ru.yandex.qatools.embed.postgresql.distribution.Version;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
@ComponentScan(basePackages = {"com.zharker.database"})
@Configurable
@EnableJpaRepositories(basePackages = {"com.zharker.database.data"}, repositoryFactoryBeanClass = CustomRepositoryFactoryBean.class)
@EntityScan(basePackages = {"com.zharker.database.domain"})
@Slf4j
public class Config {

    @Value("${spring.flyway.drop-before-migrate}")
    private boolean dropBeforeMigrate;

    @Bean
    public FlywayMigrationStrategy cleanMigrateStrategy() {

        return flyway -> {
            if (dropBeforeMigrate) {
                flyway.clean();
            }
            flyway.migrate();
        };
    }

    public IRuntimeConfig cachedRuntimeConfigWithProxy(Path cachedPath, String embeddedPGDownLoadProxy, int embeddedPGDownLoadPort) {
        final Command cmd = Command.Postgres;
        final FixedPath cachedDir = new FixedPath(cachedPath.toString());
        return new RuntimeConfigBuilder()
                .defaults(cmd)
                .artifactStore(new PostgresArtifactStoreBuilder()
                        .defaults(cmd)
                        .tempDir(cachedDir)
                        .download(new PostgresDownloadConfigBuilder()
                                .defaultsForCommand(cmd)
                                .packageResolver(new PackagePaths(cmd, cachedDir))
                                .proxyFactory(new HttpProxyFactory(embeddedPGDownLoadProxy, embeddedPGDownLoadPort))
                                .build()))
//                .commandLinePostProcessor(privilegedWindowsRunasPostprocessor())
                .build();
    }


    @Bean(destroyMethod = "stop")
    @ConditionalOnProperty(value = "test.embedded.postgresql", havingValue = "true")
    public PostgresProcess postgresProcess(@Value("${spring.datasource.username}")
                                                   String embeddedPGUsername,
                                           @Value("${spring.datasource.password}")
                                                   String embeddedPGPassword) throws IOException {
        log.info("Starting embedded Postgres...");

        String tempDir = System.getProperty("java.io.tmpdir");
        String dataDir = tempDir + "/database_for_tests";
        String binariesDir = tempDir + "/pg_bin";

        PostgresConfig postgresConfig = new PostgresConfig(
                Version.V9_6_11,
                new AbstractPostgresConfig.Net("localhost", Network.getFreeServerPort()),
                new AbstractPostgresConfig.Storage("test"),
                new AbstractPostgresConfig.Timeout(60_000),
                new AbstractPostgresConfig.Credentials(embeddedPGUsername, embeddedPGPassword)
        );

        IRuntimeConfig iRuntimeConfig = EmbeddedPostgres.cachedRuntimeConfig(Paths.get(binariesDir));


        PostgresStarter<PostgresExecutable, PostgresProcess> runtime =
                PostgresStarter
                        .getInstance(iRuntimeConfig);
        PostgresExecutable exec = runtime.prepare(postgresConfig);
        PostgresProcess process = exec.start();

        return process;
    }

    @Bean(destroyMethod = "close")
    @ConditionalOnProperty(value = "test.embedded.postgresql", havingValue = "true")
    @DependsOn("postgresProcess")
    DataSource dataSource(PostgresProcess postgresProcess) {
        PostgresConfig postgresConfig = postgresProcess.getConfig();

        val config = new HikariConfig();
        config.setUsername(postgresConfig.credentials().username());
        config.setPassword(postgresConfig.credentials().password());
        config.setJdbcUrl("jdbc:postgresql://localhost:" + postgresConfig.net().port() + "/" + postgresConfig.storage().dbName() + "?" + "&characterEncoding=UTF-8");

        return new HikariDataSource(config);
    }

}
