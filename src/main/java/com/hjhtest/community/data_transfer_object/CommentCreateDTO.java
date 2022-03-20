package com.hjhtest.community.data_transfer_object;

import lombok.Data;

/**
 * @ClassName: CommentDTO
 * @Description:
 * @author: hjh
 * @date: 2022/3/18 18:59
 */
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
