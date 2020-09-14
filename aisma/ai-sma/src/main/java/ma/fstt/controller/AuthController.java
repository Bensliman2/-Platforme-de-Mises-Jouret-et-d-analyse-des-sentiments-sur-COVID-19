package ma.fstt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ma.fstt.entity.User;
import ma.fstt.model.AuthRequest;
import ma.fstt.model.AuthResponse;
import ma.fstt.service.UserDetailsService;
import ma.fstt.util.JwtUtil;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/auth")
public class AuthController {
	
	@Autowired
	private PasswordEncoder passEncoder;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;

	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<User> register(@RequestBody AuthRequest request) {
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(passEncoder.encode(request.getPassword()));
		user = userDetailsService.create(user);
		return ResponseEntity.ok(user);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (BadCredentialsException e) {
			//throw new Exception("!! wrong email or password !!");
			return new ResponseEntity<AuthResponse>(new AuthResponse(""), HttpStatus.UNAUTHORIZED);
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		final String token = jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthResponse(token));
	}
}
