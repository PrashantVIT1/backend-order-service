package com.prashant.backendorderservice.auth.support;

import com.prashant.backendorderservice.auth.config.WebSecurityConfig;
import com.prashant.backendorderservice.auth.exception.CustomAuthEntryPoint;
import com.prashant.backendorderservice.auth.repository.UserRepository;
import com.prashant.backendorderservice.auth.service.CustomUserDetailsService;
import com.prashant.backendorderservice.auth.util.AuthUtil;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;

@WebMvcTest
@Import(WebSecurityConfig.class)
@WithMockUser
public abstract class SecuredControllerTest {

    @MockBean
    protected UserRepository userRepository;

    @MockBean
    protected AuthUtil authUtil;

    @MockBean
    protected CustomUserDetailsService customUserDetailsService;

    @MockBean
    protected CustomAuthEntryPoint customAuthEntryPoint;
}
