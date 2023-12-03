package fr.ishtamar.security.jwt.service;

import fr.ishtamar.security.jwt.entity.UserInfo;
import fr.ishtamar.security.jwt.exceptionhandler.EmailAlreadyUsedException;
import fr.ishtamar.security.jwt.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.security.jwt.exceptionhandler.InvalidPasswordException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserInfoService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username) throws EntityNotFoundException;

    String modifyUser(UserInfo userInfo,boolean treatPassword) throws EmailAlreadyUsedException, InvalidPasswordException;

    String createUser(UserInfo userInfo) throws EmailAlreadyUsedException, InvalidPasswordException;

    UserInfo getUserByUsername(String username) throws EntityNotFoundException;

    UserInfo getUserById(Long id) throws EntityNotFoundException;
}
