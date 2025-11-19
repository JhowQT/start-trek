package fiap.com.br.start_trek.security;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import fiap.com.br.start_trek.config.RsaKeysProperties;

import org.springframework.security.oauth2.jwt.*;
import org.springframework.http.HttpMethod;

import com.nimbusds.jose.jwk.*;
import com.nimbusds.jose.jwk.source.*;
import com.nimbusds.jose.proc.SecurityContext;

import org.springframework.web.cors.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RsaKeysProperties rsaKeys;

    public SecurityConfig(RsaKeysProperties rsaKeys) {
        this.rsaKeys = rsaKeys;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(auth -> auth

                // ROTAS PÚBLICAS
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers("/usuarios").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                // LIBERAR GET comentários
                .requestMatchers(HttpMethod.GET, "/comentarios/**").permitAll()

                // BLOQUEAR o POST PARA LOGIN OBRIGATÓRIO
                .requestMatchers(HttpMethod.POST, "/comentarios").authenticated()

                // RESTANTE É PÚBLICO
                .anyRequest().permitAll()
            )
            .oauth2ResourceServer(oauth -> oauth.jwt());

        return http.build();
    }

    // Decodificador JWT
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    // Codificador JWT
    @Bean
    public JwtEncoder jwtEncoder() {

        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey())
                .privateKey(rsaKeys.privateKey())
                .build();

        JWKSource<SecurityContext> jwks =
                new ImmutableJWKSet<>(new JWKSet(jwk));

        return new NimbusJwtEncoder(jwks);
    }

    // 🔥 AGORA SENHA TEXTO → SENHA TEXTO (SEM CRIPTOGRAFIA)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    // CORS → resolve todos os "blocked by CORS"
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
