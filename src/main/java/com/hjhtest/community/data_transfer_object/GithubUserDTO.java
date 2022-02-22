package com.hjhtest.community.data_transfer_object;

import lombok.Data;

/**
 * @ClassName: GithubUserDTO
 * @Description:
 * @author: hjh
 * @date: 2022/2/19 17:13
 */
@Data
public class GithubUserDTO {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;
}
