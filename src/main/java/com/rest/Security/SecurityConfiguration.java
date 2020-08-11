package com.rest.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Configures spring security, doing the following:
 * <li>Bypass security checks for static resources,</li>
 * <li>Restrict access to the application, allowing only logged in users,</li>
 * <li>Set up the login form</li>
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String LOGIN_FAILURE_URL = "/login?error";
    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_SUCCESS_URL = "/login";

//	private final UserDetailsService userDetailsService;

//	@Autowired
//	private PasswordEncoder passwordEncoder;

//	@Autowired
//	public SecurityConfiguration(UserDetailsService userDetailsService) {
//		this.userDetailsService = userDetailsService;
//	}

    /**
     * The password encoder to use when encrypting passwords.
     */
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return NoOpPasswordEncoder.getInstance();
//	}
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception { //
        return super.authenticationManagerBean();
    }

    @Bean
    public CustomRequestCache requestCache() { //
        return new CustomRequestCache();
    }


    @Bean
    @Override
    public UserDetailsService userDetailsService() {

        // typical logged in user with some privileges
        UserDetails normalUser =
                User.withUsername("user")
                        .password("{noop}password")
                        .roles("USER")
                        .build();

        // admin user with all privileges
        UserDetails adminUser =
                User.withUsername("admin")
                        .password("{noop}password")
                        .roles("USER", "ADMIN")
                        .build();

        // admin user with all privileges
        UserDetails illiaUser =
                User.withUsername("illia")
                        .password("{noop}password")
                        .roles("USER")
                        .build();

        UserDetails herringUser =
                User.withUsername("herring@gmail.com")
                        .password("{noop}password")
                        .roles("USER")
                        .build();
        UserDetails malcolmUser =
                User.withUsername("malcolm@gmail.com")
                        .password("{noop}password")
                        .roles("USER")
                        .build();
        UserDetails jacksonUser =
                User.withUsername("jackson@gmail.com")
                        .password("{noop}password")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(normalUser, adminUser, illiaUser, herringUser, malcolmUser, jacksonUser);
    }

    /**
     * Require login to access internal pages and configure login form.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Not using Spring CSRF here to be able to use plain HTML for the login page
        http.csrf().disable()

                // Register our CustomRequestCache, that saves unauthorized access attempts, so
                // the user is redirected after login.
                .requestCache().requestCache(new CustomRequestCache())

                // Restrict access to our application.
                .and().authorizeRequests()

                // Allow all flow internal requests.
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

                // Allow all requests by logged in users.
                .anyRequest().authenticated()

                // Configure the login page.
                .and().formLogin().loginPage(LOGIN_URL).permitAll().loginProcessingUrl(LOGIN_PROCESSING_URL)
                .failureUrl(LOGIN_FAILURE_URL)

                // Configure logout
                .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL)

                // Configure remember me cookie
                .and().rememberMe().key("pssssst").alwaysRemember(true);
    }

    /**
     * Allows access to static resources, bypassing Spring security.
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                // Vaadin Flow static resources
                "/VAADIN/**",

                // the standard favicon URI
                "/favicon.ico",

                // the robots exclusion standard
                "/robots.txt",

                // web application manifest
                "/manifest.webmanifest",
                "/sw.js",
                "/offline-page.html",

                // icons and images
                "/icons/**",
                "/images/**",

                // (development mode) static resources
                "/frontend/**",

                // (development mode) webjars
                "/webjars/**",

                // (development mode) H2 debugging console
                "/h2-console/**",

                // (production mode) static resources
                "/frontend-es5/**", "/frontend-es6/**");
    }
}
