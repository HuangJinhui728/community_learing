package com.hjhtest.community.data_transfer_object;

import lombok.Data;

/**
 * @ClassName: GiteeUserDTO
 * @Description:
 * @author: hjh
 * @date: 2022/3/17 22:45
 */
@Data
public class GiteeUserDTO {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;
}
