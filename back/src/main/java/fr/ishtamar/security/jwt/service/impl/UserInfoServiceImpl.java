package fr.ishtamar.security.jwt.service.impl;

import fr.ishtamar.security.jwt.entity.UserInfo;
import fr.ishtamar.security.jwt.exceptionhandler.EmailAlreadyUsedException;
import fr.ishtamar.security.jwt.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.security.jwt.exceptionhandler.InvalidPasswordException;
import fr.ishtamar.security.jwt.repository.UserInfoRepository;
import fr.ishtamar.security.jwt.service.JwtService;
import fr.ishtamar.security.jwt.service.UserInfoDetails;
import fr.ishtamar.security.jwt.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoRepository repository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws EntityNotFoundException {
        Optional<UserInfo> userDetail = repository.findByEmail(username);

        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new EntityNotFoundException(UserDetails.class,"username",username));
    }

    @Override
    public String modifyUser(UserInfo userInfo, boolean treatPassword) throws EmailAlreadyUsedException,InvalidPasswordException {
        Optional<UserInfo> userDetail = repository.findByEmail(userInfo.getEmail());

        //Throws Exception if Email is already registered AND it's not an update
        if (userDetail.isPresent()){
            if (Objects.equals(userDetail.get().getEmail(), userInfo.getEmail())) {
                if (!Objects.equals(userDetail.get().getId(), userInfo.getId())) {
                    throw new EmailAlreadyUsedException();
                }
            }
        }

        //Otherwise it keeps on
        if (treatPassword) {
            userInfo.setPassword(encoder.encode(userInfo.getPassword()));
            repository.save(userInfo);
            return "User Modified Successfully";
        } else {
            repository.save(userInfo);
            return "User Modified Successfully";
        }
    }

    @Override
    public String createUser(UserInfo userInfo) throws EmailAlreadyUsedException,InvalidPasswordException {
        Optional<UserInfo> userDetail = repository.findByEmail(userInfo.getEmail());

        //Throws Exception if Email is already registered
        if (userDetail.isPresent()){
            throw new EmailAlreadyUsedException();
        }

        //Otherwise it keeps on
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User Added Successfully";
    }

    @Override
    public UserInfo getUserByUsername(String username) throws EntityNotFoundException {
        return repository.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException(UserInfo.class,"email",username));
    }

    @Override
    public UserInfo getUserById(Long id) throws EntityNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(UserDetails.class,"id",id.toString()));
    }
}
