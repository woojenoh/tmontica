package com.internship.tmontica.user;

import com.internship.tmontica.user.exception.InvalidUserIdException;
import com.internship.tmontica.user.exception.PasswordMismatchException;
import com.internship.tmontica.user.exception.UserIdNotFoundException;
import com.internship.tmontica.user.model.UserDTO;
import com.internship.tmontica.user.model.request.UserInfoReqDTO;
import com.internship.tmontica.user.model.request.UserPasswordCheckReqDTO;
import com.internship.tmontica.user.model.request.UserSignInReqDTO;
import com.internship.tmontica.user.model.request.UserSignUpReqDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService { //implements UserDetail

    private final UserDao userDao;
    //private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public boolean isDuplicate(String id){
        User user = userDao.getUserByUserId(id);

        return !(user == null);
    }
    @Transactional
    public void signUp(UserSignUpReqDTO userSignUpReqDTO){
        //userSignUpReqDTO.setPassword(passwordEncoder.encode(user.getPassword())); need passwordEnconding
        if(isDuplicate(userSignUpReqDTO.getId())){
            throw new InvalidUserIdException();
        }

        User user = modelMapper.map(userSignUpReqDTO, User.class);

        System.out.println(user.toString());
       int resultValue = userDao.addUser(user);
    }

    @Transactional(readOnly = true)
    public void signIn(UserSignInReqDTO userSignInReqDTO) {
        User user = userDao.getUserByUserId(userSignInReqDTO.getId());

        if(!isExistUser(userSignInReqDTO, user)){
            throw new UserIdNotFoundException();
        }

        if(!isSamePassword(userSignInReqDTO, user)){
            throw new PasswordMismatchException();
        }
    }

    @Transactional(readOnly = true)
    public boolean checkPassword(UserPasswordCheckReqDTO userPasswordCheckReqDTO){
        User user = userDao.getUserByUserId(userPasswordCheckReqDTO.getId());

        return isSamePassword(userPasswordCheckReqDTO, user);
    }

    //public User getUserInfo(UserInfoReqDTO)

    private boolean isSamePassword(UserDTO userDTO, User user){

        return userDTO.getPassword().equals(user.getPassword());
    }

    private boolean isExistUser(UserDTO userDTO, User user){

        return userDTO.getId().equals(user.getId());
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

}
