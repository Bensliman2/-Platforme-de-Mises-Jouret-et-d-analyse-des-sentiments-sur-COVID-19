package ma.fstt.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User implements Serializable, UserDetails {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String username;
	private String password;
	@Column(columnDefinition = "boolean default true")
	private boolean accountNonExpired = true;
	@Column(columnDefinition = "boolean default true")
	private boolean accountNonLocked = true;
	@Column(columnDefinition = "boolean default true")
	private boolean credentialsNonExpired = true;
	@Column(columnDefinition = "boolean default true")
	private boolean enabled = true;
	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	private List<Authority> authorities;

	public User() {
		authorities = new ArrayList<Authority>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getPassword() {
		return password;
	}
	
	public void setAccountNonExpired(boolean accountNonExpired){
		this.accountNonExpired = accountNonExpired;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked){
		this.accountNonLocked = accountNonLocked;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}
	
	public void setCredentialsNonExpired(boolean credentialsNonExpired){
		this.credentialsNonExpired = credentialsNonExpired;
	}
	
	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}
	
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return enabled;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

}
