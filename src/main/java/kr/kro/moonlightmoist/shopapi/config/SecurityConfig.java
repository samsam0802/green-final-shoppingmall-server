package kr.kro.moonlightmoist.shopapi.config;

import kr.kro.moonlightmoist.shopapi.security.handler.CustomAccessDeniedHandler;
import kr.kro.moonlightmoist.shopapi.security.handler.CustomAuthenticationEntryPoint;
import kr.kro.moonlightmoist.shopapi.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration // 스프링부트가 시작할 때 해당 클래스를 스캔 그안의 설정들을 읽어서 적용
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean // "이 메소드가 반환하는 객체를 스프링이 관리해줘"
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 공격 방지 설정 disable() 끄는 메서드
                .csrf(csrf -> csrf.disable())

                // CORS 설정
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 세션 설정
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 무상태, 세션 생성 안함.
                )

                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                )

                // 만든 JWT 필터를 Spring Secuirity에 추가.
                // 특정 필터를 등록하는 역할
                // 첫 번째 인자로 등록 필터를 전달하고, 두 번째 인자로 등록할 위치 전달
                // UsernamePasswordAuthenticationFilter는 Spring Security에서 기본적으로 제공하는 폼 인증 처리 필터
                .addFilterBefore(jwtAuthenticationFilter, // 직접 만든 필터
                        UsernamePasswordAuthenticationFilter.class) // 이 필터보다 먼저 실행

                // 어떤 요청을 허용하고, 막을지를 설정하는 메서드
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("api/user/refresh").permitAll()
                        .anyRequest().permitAll());
//            anyRequest() 모든요청을 의미, premitAll() 모든요청 허용
        return http.build(); // 해당 http를 만들어서 반환
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173",
                "http://green-final-shoppingmall-react-ikg8.vercel.app",
                "https://green-final-shoppingmall-react-ikg8.vercel.app",
                "http://green-final-shoppingmall-react.vercel.app",
                "https://green-final-shoppingmall-react.vercel.app",
                "http://moisture-village.kro.kr",
                "https://moisture-village.kro.kr"
                ));
//        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        configuration.setAllowedHeaders(List.of("*")); // 수정 BY 병국
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS")); // 허용 메서드
        configuration.setAllowCredentials(true); // 세션 쿠키 설정

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);

        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
