package fiap.com.br.start_trek.security;


import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import fiap.com.br.start_trek.config.RsaKeysProperties;

import org.springframework.security.oauth2.jwt.*;
import com.nimbusds.jose.jwk.*;
import com.nimbusds.jose.jwk.source.*;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RsaKeysProperties rsaKeys;

    public SecurityConfig(RsaKeysProperties rsaKeys) {
        this.rsaKeys = rsaKeys;
    }

    // 🔐 1) Configuração da cadeia de segurança
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable()) // Desabilita CSRF para APIs REST
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login").permitAll()       // Rota pública
                .requestMatchers("/usuarios").permitAll()         // Cadastro público
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Swagger liberado
                .requestMatchers("/comentarios/**").authenticated() // Só logado comenta
                .anyRequest().permitAll()                          // Restante público
            )
            .oauth2ResourceServer(oauth -> oauth
                .jwt() // Habilita validação JWT pela public key
            );

        return http.build();
    }

    // 🔐 2) Decodificador JWT (usa PUBLIC KEY)
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    // 🔐 3) Codificador JWT (usa PRIVATE KEY + PUBLIC KEY)
    @Bean
    public JwtEncoder jwtEncoder() {

        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey())
                .privateKey(rsaKeys.privateKey())
                .build();

        JWKSource<SecurityContext> jwks =
                new ImmutableJWKSet<>(new JWKSet(jwk));

        return new NimbusJwtEncoder(jwks);
    }

    // 🔐 4) BCrypt (para senhas)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 🔐 5) AuthenticationManager (para autenticar o login)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
