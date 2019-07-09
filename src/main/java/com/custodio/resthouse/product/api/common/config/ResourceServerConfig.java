package com.custodio.resthouse.product.api.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

/**
 * Class responsible for defining the configuration used to retrieve the authorization token and validation.
 *
 * @author williamcustodio
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter
{
    private final SecurityParameter securityParameter;

    @Autowired
    public ResourceServerConfig(final SecurityParameter securityParameter)
    {
        this.securityParameter = securityParameter;
    }

    @Override
    public void configure(final ResourceServerSecurityConfigurer resources)
    {
        resources.resourceId(this.securityParameter.getResourceId());
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
                .anyRequest()
                .authenticated();
    }

    @Bean
    public RemoteTokenServices LocalTokenService()
    {
        final RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setCheckTokenEndpointUrl(this.securityParameter.getCheckTokenUrl());
        tokenService.setClientId(this.securityParameter.getClientId());
        tokenService.setClientSecret(this.securityParameter.getClientSecret());
        return tokenService;
    }
}