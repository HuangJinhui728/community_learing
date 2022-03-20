package com.hjhtest.community.data_transfer_object;

import com.hjhtest.community.model.User;
import lombok.Data;

/**
 * @ClassName: QuestionDTO
 * @Description:
 * @author: hjh
 * @date: 2022/2/20 21:47
 */
@Data
public class QuestionDTO {
    private Long id;
    private String title ;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator ;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private User user;
}
