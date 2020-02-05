package root.demo.services.jwt;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import root.demo.model.repo.MyUser;
import root.demo.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
    	
        if(userRepository.findByMail(username) != null) {
        	return UserPrinciple.build(userRepository.findBymail(username));
        } else {
        	return null;
        }
           

        //return UserPrinciple.build(user);
    }
    
    public MyUser getLoggedUser() {
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails) {
		   username = ((UserDetails)principal).getUsername();
		} else {
		   // username = principal.toString(); //anonimni user
			return null;
		}
		return userRepository.findByusername(username);
    }
}