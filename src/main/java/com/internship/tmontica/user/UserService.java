package com.internship.tmontica.user;

import com.internship.tmontica.user.model.request.UserLoginReqDTO;
import com.internship.tmontica.user.model.request.UserSignUpReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService { //implements UserDetail

    private final UserDao userDao;
    //private final PasswordEncoder passwordEncoder;

    @Transactional
    public int addUser(UserSignUpReqDTO userSignUpReqDTO){
        //userSignUpReqDTO.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.addUser(userSignUpReqDTO);
    }

    @Transactional(readOnly = true)
    public User getUserByUserId(String id){
        return userDao.getUserByUserId(id);
    }

    @Transactional(readOnly = true)
    public String getUserPasswordByUserId(String id){ return userDao.getPasswordByUserId(id);}

    @Transactional
    public int updateUserPassword(User user){
        return userDao.updateUserPassword(user);
    }

    @Transactional
    public int deleteUser(String id){
        return userDao.deleteUser(id);
    }

//    @Override
//    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
//
//        User user = userDao.getUserByUserId(userId);
//        List<GrantedAuthority> authorities = new ArrayList<>();
//               authorities.add(new SimpleGrantedAuthority("user"));
//        return new org.springframework.security.core.userdetails.User(user.getId(), user.getPassword(),authorities);
//
//    }

    public void checkPassword(UserLoginReqDTO userLoginReqDTO) {
        User user = getUserByUserId(userLoginReqDTO.getId());

        if(!user.getPassword().equals(userLoginReqDTO.getPassword())){
            //throw new Exception();
        }
    }

    public boolean isDuplicate(UserSignUpReqDTO userSignUpReqDTO){

        return true;
    }


}
