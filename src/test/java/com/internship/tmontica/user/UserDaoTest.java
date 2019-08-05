package com.internship.tmontica.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@SpringBootTest
public class UserDaoTest {

    private UserDao userDao = new UserDao() {
        @Override
        public int addUser(User user) {
            return 0;
        }

        @Override
        public User getUserByUserId(String id) {
            return null;
        }

        @Override
        public List<User> getUserByEmail(String email) {
            return null;
        }

        @Override
        public String getUserIdByUserId(String id) {
            return null;
        }

        @Override
        public int getUserPointByUserId(String id) {
            return 0;
        }

        @Override
        public int updateUserPassword(User user) {
            return 0;
        }

        @Override
        public int updateActivateStatus(int isActive) {
            return 0;
        }

        @Override
        public int updateUserPoint(int point, String id) {
            return 0;
        }

        @Override
        public int deleteUser(String id) {
            return 0;
        }
    };

    @Test
    public void 유저_가져오기(){
        User user = userDao.getUserByUserId("samkko");
        assertEquals(user.getName(), "hyung seok kim");
    }
}
