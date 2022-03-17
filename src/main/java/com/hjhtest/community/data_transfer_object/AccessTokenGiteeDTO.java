package com.hjhtest.community.data_transfer_object;

import lombok.Data;

/**
 * @ClassName: AccessTokenGiteeDTO
 * @Description:
 * @author: hjh
 * @date: 2022/3/17 22:26
 */
@Data
public class AccessTokenGiteeDTO {
    private String grant_type = "authorization_code";
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
}
