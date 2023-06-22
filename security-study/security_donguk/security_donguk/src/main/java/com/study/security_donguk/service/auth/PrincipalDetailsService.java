package com.study.security_donguk.service.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.study.security_donguk.domain.User.User;
import com.study.security_donguk.domain.User.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userEntity = null;
		try {
			userEntity = userRepository.findUserByUsername(username);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException(username);
		}
		
		if(userEntity == null) {
			throw new UsernameNotFoundException(username + "사용자이름을 사용할 수 없습니다.");
		}
		
		

		
//		UserDetails userDetails = new UserDetails() {
//			
//			@Override
//			public boolean isEnabled() {
//				return true;
//			}
//			
//			@Override
//			public boolean isCredentialsNonExpired() {
//				return true;
//			}
//			
//			@Override
//			public boolean isAccountNonLocked() {
//				return true;
//			}
//			
//			@Override
//			public boolean isAccountNonExpired() {
//				return true;
//			}
//			
//			@Override
//			public String getUsername() {
//				return "test";
//			}
//			
//			@Override
//			public String getPassword() {
//				return new BCryptPasswordEncoder().encode("1234");
//			}
//			
//			@Override
//			public Collection<? extends GrantedAuthority> getAuthorities() {
//				return null;
//			}
//		};
		
//		if(!username.equals("test")) {
//		throw new UsernameNotFoundException(username + "사용자이름을 사용할 수 없습니다.");
//	}
		
//		return userDetails;
		return new PrincipalDetails(userEntity);
	}

	public boolean addUser() {
		User user = User.builder()
				.user_name("홍길동")
				.user_email("hong@gmail.com")
				.user_id("abcd")
				.user_password(new BCryptPasswordEncoder().encode("1234"))
				.user_roles("ROLE_USER, ROLE_MANAGER")
				.build();
		try {
			userRepository.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
