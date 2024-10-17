package org.sid.saranApp.controller;


import org.sid.saranApp.config.security.JwtTokenUtil;
import org.sid.saranApp.dto.JwtRequest;
import org.sid.saranApp.dto.JwtResponse;
import org.sid.saranApp.serviceImpl.JwtUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;





@RestController
@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "*")
public class JwtAuthenticationController {
    Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;
            
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        logger.info("atuehticating user user");
        
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        logger.info("user authenticated ");

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        logger.info("user loaded ");

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }
    


    private void authenticate(String username, String password) throws Exception {
        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            logger.info("USER_DISABLED");
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            logger.error("INVALID_CREDENTIALS",e);
            throw new Exception("INVALID_CREDENTIALS", e);
        } catch (Exception e) {
            logger.error("Exception",e);
            throw new Exception("Exception", e);
        }
    }

}
