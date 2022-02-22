package com.hjhtest.community.data_transfer_object;

import lombok.Data;

/**
 * @ClassName: AccessTokenDTO
 * @Description:
 * @author: hjh
 * @date: 2022/2/19 16:34
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
}
