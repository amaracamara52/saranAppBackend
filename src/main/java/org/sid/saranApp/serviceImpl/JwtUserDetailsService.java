package org.sid.saranApp.serviceImpl;


import java.util.Optional;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;





@Service
public class JwtUserDetailsService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);

    @Autowired
    UtilisateurRepository utilisateurRepository;
    
    

//    @Autowired
//    AgentRepository agentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utilisateur> findByEmail = utilisateurRepository.findByEmail(username);
        if (findByEmail.isPresent()) {
            logger.info("user found {}", findByEmail.get().getPassword());
            return (Mapper.toUserDetails(findByEmail.get()));
        }
//
//        Optional<UserPartenaire> findByMatricule = userPartenaireRepository.findByEmail(username);
//        if (findByMatricule.isPresent()) {
//            return Mapper.toUserDetails(findByMatricule.get().getUsername());
//        }

        //        if ("javainuse".equals(username)) {
        //            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        //            authorities.add(new SimpleGrantedAuthority("USER"));
        //            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        //            return new User("javainuse", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6", authorities);
        //        } else {
        throw new UsernameNotFoundException("User not found with username: " + username);
        //        }
    }
    
}
