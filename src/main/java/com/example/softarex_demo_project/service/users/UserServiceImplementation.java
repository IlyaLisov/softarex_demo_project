package com.example.softarex_demo_project.service.users;

import com.example.softarex_demo_project.model.Status;
import com.example.softarex_demo_project.model.user.Role;
import com.example.softarex_demo_project.model.user.User;
import com.example.softarex_demo_project.repository.RoleRepository;
import com.example.softarex_demo_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * This class is an implementation of UserService.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean register(User user) {
        User userFromDatabase = userRepository.findByUsername(user.getUsername());
        if(userFromDatabase == null) {
            Role roleUser = roleRepository.findByName("ROLE_USER");
            List<Role> userRoles = new ArrayList<>();
            userRoles.add(roleUser);

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(userRoles);
            user.setStatus(Status.ACTIVE);
            user.setCreated(new Date());
            user.setUpdated(new Date());
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> getByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    public boolean update(User user) {
        Optional<User> userFromDatabase = userRepository.findById(user.getId());
        if (userFromDatabase.isPresent()) {
            userFromDatabase.get().setFirstName(user.getFirstName());
            userFromDatabase.get().setLastName(user.getLastName());
            userFromDatabase.get().setEmail(user.getEmail());
            userFromDatabase.get().setUsername(user.getEmail());
            userFromDatabase.get().setPhoneNumber(user.getPhoneNumber());
            //if password hasn`t changed OR CHANGED TO ITS HASH then it must be encoded
            if (!user.getPassword().equals(userFromDatabase.get().getPassword())) {
                userFromDatabase.get().setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userFromDatabase.get().setUpdated(new Date());
            userRepository.save(userFromDatabase.get());
            return true;
        }
        return false;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
