package com.hjhtest.community.model;

import lombok.Data;

/**
 * @ClassName: User
 * @Description:
 * @author: hjh
 * @date: 2022/2/19 22:23
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
