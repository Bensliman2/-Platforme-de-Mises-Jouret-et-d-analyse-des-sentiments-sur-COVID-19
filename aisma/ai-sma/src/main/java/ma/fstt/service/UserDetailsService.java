package ma.fstt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ma.fstt.entity.User;
import ma.fstt.repository.UserRepository;

@Service
public class UserDetailsService implements BaseRestService<User, Long>, org.springframework.security.core.userdetails.UserDetailsService{
	
	@Autowired
	private UserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repo.findByUsername(username);
	}

	@Override
	public List<User> getAll() {
		List<User> users = new ArrayList<>();
		repo.findAll().forEach(users::add);
		return users;
	}

	@Override
	public User get(Long id) {
		return repo.findById(id).get();
	}

	@Override
	public User create(User resource) {
		return repo.save(resource);
	}

	@Override
	public User update(Long id, User resource) {
		resource.setId(id);
		return repo.save(resource);
	}

	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}

}
