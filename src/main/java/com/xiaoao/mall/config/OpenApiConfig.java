package com.xiaoao.mall.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * spring doc配置
 */
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI restfulOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("笑傲科技商城系统")
                        .description("内部Api,版权归笑傲科技所有")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringDoc维基链接")
                        .url("https://springdoc.org/v2"))
                .servers(Collections.singletonList(new Server()
                        .url("http://http://localhost:8080/")
                        .description("笑傲科技对外端口"))
                );
    }

}
