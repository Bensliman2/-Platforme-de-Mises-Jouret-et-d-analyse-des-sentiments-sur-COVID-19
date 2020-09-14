package ma.fstt.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ma.fstt.entity.User;

public interface UserRepository extends CrudRepository<User, Long>{
	@Query(" select u from User u where u.username = ?1")
	public User findByUsername(String username);
}
