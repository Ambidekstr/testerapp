package com.ambidekstr.testerapp.repository;

import com.ambidekstr.testerapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by anatolii on 08.08.2016.
 */
public interface UserRepository extends JpaRepository<User,Long> {
}
