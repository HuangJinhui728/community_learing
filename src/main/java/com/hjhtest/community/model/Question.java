package com.hjhtest.community.model;

import lombok.Data;

/**
 * @ClassName: Question
 * @Description:
 * @author: hjh
 * @date: 2022/2/20 17:05
 */
@Data
public class Question {
    private Integer id;
    private String title ;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator ;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
}
