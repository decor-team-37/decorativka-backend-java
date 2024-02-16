package teamproject.decorativka.secutiry;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import teamproject.decorativka.dto.login.LoginRequestDto;
import teamproject.decorativka.dto.login.LoginResponseDto;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager manager;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponseDto authentication(LoginRequestDto requestDto) {
        final Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.email(), requestDto.password())
        );
        String token = jwtUtil.generateToken(requestDto.email());
        return new LoginResponseDto(token);
    }
}
