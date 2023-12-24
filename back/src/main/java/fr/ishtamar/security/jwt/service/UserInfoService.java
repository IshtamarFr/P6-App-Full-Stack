package fr.ishtamar.security.jwt.service;

import fr.ishtamar.security.jwt.entity.UserInfo;
import fr.ishtamar.security.jwt.exceptionhandler.EmailAlreadyUsedException;
import fr.ishtamar.security.jwt.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.security.jwt.exceptionhandler.InvalidPasswordException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserInfoService extends UserDetailsService {
    /**
     * Tries to find user corresponding to unique username
     * @param username Unique name or email
     * @return User corresponding to username
     * @throws UsernameNotFoundException Username not found
     */
    @Override
    UserDetails loadUserByUsername(String username) throws EntityNotFoundException;

    /**
     * Tries to modify data for user if password is OK and email is not used by someone else
     * @param userInfo User data to be modified
     * @param treatPassword Boolean to determine whether given password must be encrypted or not
     * @return message: all is OK
     * @throws EmailAlreadyUsedException this Email is already registered
     * @throws InvalidPasswordException this Password doesn't verify RegEx criteria
     */
    String modifyUser(UserInfo userInfo,boolean treatPassword) throws EmailAlreadyUsedException, InvalidPasswordException;

    /**
     * Tries to create a new user if password is OK and email is not used by someone else
     * @param userInfo User data to be created
     * @return message: all is OK
     * @throws EmailAlreadyUsedException this Email is already registered
     * @throws InvalidPasswordException this Password doesn't verify RegEx criteria
     */
    String createUser(UserInfo userInfo) throws EmailAlreadyUsedException, InvalidPasswordException;

    /**
     * Tries to find user by its username
     * @param username String for user
     * @return UserInfo corresponding
     * @throws EntityNotFoundException Username not found
     */
    UserInfo getUserByUsername(String username) throws EntityNotFoundException;

    /**
     * Tries to find User by long id
     * @param id Long id for user
     * @return UserInfo corresponding
     * @throws EntityNotFoundException User Id not found
     */
    UserInfo getUserById(Long id) throws EntityNotFoundException;
}
