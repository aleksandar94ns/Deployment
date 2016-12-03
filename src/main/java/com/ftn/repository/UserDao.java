package com.ftn.repository;

import com.ftn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Alex on 10/28/16.
 */
public interface UserDao extends JpaRepository<User, Long> {

    <E extends User> E findByEmail(String email);

    <E extends User> E findById(long id);

    <E extends User> E findByConfirmationCode(String email);

    <E extends User> List<E> findByRole(User.Role role);
}
