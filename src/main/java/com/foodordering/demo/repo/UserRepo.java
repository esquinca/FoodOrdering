package com.foodordering.demo.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.foodordering.demo.dto.UserDetailsDTO;
import com.foodordering.demo.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
	
	Optional<User> findByUsernameAndPassword(String name, String pass);
	
	@Query("select new com.foodordering.demo.dto.UserDetailsDTO(u.username, u.email, u.phoneNo) FROM User u WHERE u.username = :username")
	UserDetailsDTO findUsersWithUsername(String username);

}
