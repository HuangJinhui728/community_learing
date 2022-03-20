package com.hjhtest.community.controller;

import com.hjhtest.community.data_transfer_object.CommentCreateDTO;
import com.hjhtest.community.data_transfer_object.CommentDTO;
import com.hjhtest.community.data_transfer_object.ResultDTO;
import com.hjhtest.community.enums.CommentTypeEnum;
import com.hjhtest.community.exception.CustomizeErrorCode;
import com.hjhtest.community.model.Comment;
import com.hjhtest.community.model.User;
import com.hjhtest.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName: CommentController
 * @Description:
 * @author: hjh
 * @date: 2022/3/18 18:54
 */
@Controller
public class CommentController {



    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){
        User user =(User)request.getSession().getAttribute("user");
        if(user==null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGION);
        }

        if(commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())){
            return  ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }


        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(1L);
        comment.setLikeCount(0L);
        comment.setCommentCount(0);
        commentService.insert(comment);

        return ResultDTO.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDTO<List> comments(@PathVariable(name = "id") Long id){
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }
}
