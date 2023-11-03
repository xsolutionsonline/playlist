package com.quipux.playlist.services;

import com.quipux.playlist.config.exception.UserException;
import com.quipux.playlist.mappers.PlaylistMapper;
import com.quipux.playlist.mappers.UserMapper;
import com.quipux.playlist.models.dto.UserDTO;
import com.quipux.playlist.models.entities.User;
import com.quipux.playlist.repositories.PlaylistRepository;
import com.quipux.playlist.repositories.UserRepository;
import com.quipux.playlist.security.CustomerDetailsService;
import com.quipux.playlist.security.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;


@Slf4j
@Service
public class
UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerDetailsService customerDetailsService;


    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;


    @Autowired
    private JwtUtil jwtUtil;



    public User registerUser(User user) {
        return userRepository.save(user);
    }


    public ResponseEntity<String> login(User user) {
        log.info("dentro login");

        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword())
            );

            if(authentication.isAuthenticated()){
                if(customerDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token\":\""
                            +jwtUtil.
                                    generateToken(customerDetailsService.getUserDetail().getEmail(),
                                                  customerDetailsService.getUserDetail().getRole())+"\"}"
                            , HttpStatus.OK);
                }else {
                    return new ResponseEntity<String>("{\"mensaje\":\""+"Espere la aprobacion del administrador"+"\"}", HttpStatus.BAD_REQUEST);
                }
            }
        }catch(Exception exception){
            log.error("{}",exception);
        }

        return new ResponseEntity<String>("{\"mensaje\":\""+"Credenciales Incorrectas"+"\"}", HttpStatus.BAD_REQUEST);
    }


    private boolean validateSignUpMap(Map<String,String> requestMap){
        if(requestMap.containsKey("nombre") && requestMap.containsKey("numeroContacto") && requestMap.containsKey("email")){
            return true;
        }

        return false;
    }

    private User getUserFromMap(Map<String,String> requestMap){

        return User.builder()
                .name(requestMap.get("nombre"))
                .contactNumber(requestMap.get("numeroContacto"))
                .email(requestMap.get("email"))
                .password(requestMap.get("password"))
                .status(requestMap.get("false"))
                .role("user")
                .build();
    }
}
