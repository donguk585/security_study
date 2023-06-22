package com.study.security_donguk.web.controller.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.security_donguk.service.auth.AuthService;
import com.study.security_donguk.service.auth.PrincipalDetailsService;
import com.study.security_donguk.web.dto.CMRespDto;
import com.study.security_donguk.web.dto.auth.SignupReqDto;
import com.study.security_donguk.web.dto.auth.UsernameCheckReqDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	private final PrincipalDetailsService principalDetailsService;
	private final AuthService authService;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody @Valid SignupReqDto signupReqDto, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			
			Map<String, String> errorMessage = new HashMap<String, String>();
			
			bindingResult.getFieldErrors().forEach(error -> {
				errorMessage.put(error.getField(), error.getDefaultMessage());
			});
			return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "사용 불가능", errorMessage));
		}
		
		return ResponseEntity.ok(principalDetailsService.addUser());
	}
	
	
	
	@GetMapping("/signup/validation/username")
	public ResponseEntity<?> checkUsername(@Valid UsernameCheckReqDto usernameCheckReqDto, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			
			Map<String, String> errorMessage = new HashMap<String, String>();
			
			bindingResult.getFieldErrors().forEach(error -> {
				
				errorMessage.put(error.getField(), error.getDefaultMessage());
				
//				System.out.println("오류발생 필드명: " + error.getField());
//				System.out.println("오류발생 상세메시지: " + error.getDefaultMessage());
			});
			return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "사용 불가능", errorMessage));
		}
		
		boolean status = false;
		try {
			status = authService.checkUsername(usernameCheckReqDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok().body(new CMRespDto<>(-1, "서버 오류", status));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "회원가입 가능 여부", status));
	}
	
	
}
