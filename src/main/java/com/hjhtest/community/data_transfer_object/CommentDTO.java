package com.hjhtest.community.data_transfer_object;

import com.hjhtest.community.model.User;
import lombok.Data;

/**
 * @ClassName: CommentDTO
 * @Description:
 * @author: hjh
 * @date: 2022/3/18 18:59
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private User user;
    private Integer commentCount;
}
