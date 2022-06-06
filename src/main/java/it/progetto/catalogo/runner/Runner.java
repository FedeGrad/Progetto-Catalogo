package it.progetto.catalogo.runner;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import it.progetto.catalogo.impl.ERole;
import it.progetto.catalogo.impl.Role;
import it.progetto.catalogo.impl.RoleRepository;
import it.progetto.catalogo.impl.User;
import it.progetto.catalogo.impl.UserRepository;
import it.progetto.catalogo.model.Libro;
import it.progetto.catalogo.repository.AutoreRepository;
import it.progetto.catalogo.repository.CategoriaRepository;
import it.progetto.catalogo.repository.LibroRepository;

@Component
public class Runner implements ApplicationRunner {
	@Autowired
	LibroRepository libRepo;
	@Autowired
	AutoreRepository autoRepo;
	@Autowired
	CategoriaRepository catRepo;
	@Autowired
	UserRepository ur;
	@Autowired
	PasswordEncoder pe;
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	@Qualifier("divinaCommedia") Libro libro1;
	@Autowired
	@Qualifier("misery") Libro libro2;
	@Autowired
	@Qualifier("malavoglia") Libro libro3;
	

	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		libRepo.save(libro1);
		libRepo.save(libro2);
		libRepo.save(libro3);
		
		Role admin = new Role();
		admin.setRoleName(ERole.ROLE_ADMIN);
		Role user = new Role();
		user.setRoleName(ERole.ROLE_USER);
		
		User userAdmin = new User();
		User userDefault = new User();
		Set<Role> ruoli = new HashSet();
		ruoli.add(admin);
		userDefault.setUsername("user");
		userDefault.setPassword(BCrypt.hashpw("123", BCrypt.gensalt()));
		userDefault.setEmail("user@libero.it");
		userDefault.getRoles().add(user);
		userDefault.setAccountAttivo(true);
		
		userAdmin.setRoles(ruoli);
		userAdmin.setUsername("federico");
		userAdmin.setPassword(pe.encode("fox"));
		userAdmin.setEmail("admin@libero.it");
		userAdmin.setAccountAttivo(true);
		ur.save(userAdmin);
		ur.save(userDefault);
		
		
		
		
//		HashSet<Role> ruoli = new HashSet();
//		Role admin = new Role().builder().roleName(ERole.ROLE_ADMIN).build();
//		roleRepo.save(admin);
//		admin = roleRepo.findByRoleName(ERole.ROLE_ADMIN);
//		Role user = new Role().builder().roleName(ERole.ROLE_USER).build();
//		roleRepo.save(user);
//		User u = User.builder().username("federico").password(BCrypt.hashpw("fox", BCrypt.gensalt())).roles(ruoli).build();
//		User u2 = User.builder().username("luca").password(pe.encode("fox")).roles(ruoli).build();
//		ur.save(u);
		
	}

}
