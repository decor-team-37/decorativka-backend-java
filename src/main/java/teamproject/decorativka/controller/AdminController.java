package teamproject.decorativka.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.decorativka.dto.login.LoginRequestDto;
import teamproject.decorativka.dto.login.LoginResponseDto;
import teamproject.decorativka.secutiry.AuthenticationService;

@Tag(name = "Controller for admin panel", description = "Endpoints for administrator")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "login for admin, open endpoints")
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody @Valid LoginRequestDto requestDto) {
        return authenticationService.authentication(requestDto);
    }
}
