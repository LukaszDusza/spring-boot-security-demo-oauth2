package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.example.service.UserDetailsServiceImpl;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter {


	//@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;
	private UserDetailsService userDetailsService;

	public OAuthConfiguration(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, DataSource dataSource) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;

	}

	@Override
	public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

//		clients.
//				.withClient("fooClientId").secret("secret")
//				.authorizedGrantTypes("password", "authorization_code", "refresh_token").scopes("read","write")
//				.autoApprove(true);


		clients.inMemory()
		.withClient("client").secret("secret")
		.authorizedGrantTypes("password", "authorization_code", "refresh_token").scopes("read","write")
		.autoApprove(true);
	}

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    	endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager).accessTokenConverter(defaultAccessTokenConverter())
    	.userDetailsService(userDetailsService);
    }

	@Bean
	public TokenStore tokenStore(){

		return new JwtTokenStore(defaultAccessTokenConverter());	
	}

	@Bean
	public JwtAccessTokenConverter defaultAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("123");
		return converter;
	}
}
