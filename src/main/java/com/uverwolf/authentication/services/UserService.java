package com.uverwolf.authentication.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uverwolf.authentication.models.User;
import com.uverwolf.authentication.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository repoUser;
	//Crear usuario
	public User registro(User usuario) {
		String hashed = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());
		usuario.setPassword(hashed);
		return repoUser.save(usuario);
	}
	
	//Buscar por email
	public User buscarXEmail(String email) {
		return repoUser.findByEmail(email);
	}
	//Buscar por su id 
	public User buscarXId(Long id) {
		Optional<User> u = repoUser.findById(id);
		if(u.isPresent()) {
			return u.get();
		}else {
			return null;
		}
	}
	//Autenticar Usuario
	public boolean autenticarUsuario(String email, String password) {
		User user = repoUser.findByEmail(email);
		if(user == null) {
			return false;
		}else {
			if(BCrypt.checkpw(password, user.getPassword())) {
				return true;
			}else {
				return false;
			}
		}
	}
	
}
