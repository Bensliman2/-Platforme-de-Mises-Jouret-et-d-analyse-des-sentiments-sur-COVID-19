package ma.fstt.model;

import java.io.Serializable;

public class AuthResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private final String token;
	
	public AuthResponse(String jwt) {
		this.token = jwt;
	}

	public String getToken() {
		return token;
	}

}
