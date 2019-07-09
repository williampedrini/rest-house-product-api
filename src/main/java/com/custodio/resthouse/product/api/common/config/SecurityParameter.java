package com.custodio.resthouse.product.api.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class which holds all the security properties used to configure the resource server.
 *
 * @author williamcustodio
 */
@Component
public class SecurityParameter
{
    @Getter
    @Value("${spring.security.oauth2.resource-server.check-token-url}")
    private String checkTokenUrl;

    @Getter
    @Value("${spring.security.oauth2.resource-server.id}")
    private String resourceId;

    @Getter
    @Value("${spring.security.oauth2.client.id}")
    private String clientId;

    @Getter
    @Value("${spring.security.oauth2.client.secret}")
    private String clientSecret;
}
