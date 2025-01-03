package com.andy.proiect_facultate.repository;

import com.andy.proiect_facultate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

//    Optional<User> findByUsername(String username);
//
//    @Query(nativeQuery = true, value =
//            """
//            select u.*
//                        from users u
//                                 join user_role ur on u.id = ur.user_id
//                                 join roles on ur.role_id = roles.id
//                        where roles.name = :roleName""")
//    Set<User> findAllByRoles(String roleName);
//
//    boolean existsByUsername(String username);
}
