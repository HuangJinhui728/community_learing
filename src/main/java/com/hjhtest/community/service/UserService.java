package com.hjhtest.community.service;

import com.hjhtest.community.mapper.QuestionExtMapper;
import com.hjhtest.community.mapper.UserMapper;
import com.hjhtest.community.model.User;
import com.hjhtest.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: UserServicee
 * @Description:
 * @author: hjh
 * @date: 2022/2/24 11:16
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    public void createOrUpDate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                    .andAccountIdEqualTo(user.getAccountId())
                    .andLognetEqualTo(user.getLognet());

        List<User> dbusers = userMapper.selectByExample(userExample);



        if(dbusers.size() == 0){
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }
        else{
            User dbUser = dbusers.get(0);


            //更新
            User updateUser =new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());


            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser,example);

        }




    }
}
