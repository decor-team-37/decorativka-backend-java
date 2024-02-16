package teamproject.decorativka.secutiry;

import teamproject.decorativka.dto.login.LoginRequestDto;
import teamproject.decorativka.dto.login.LoginResponseDto;

public interface AuthenticationService {
    LoginResponseDto authentication(LoginRequestDto requestDto);
}
