package com.example.usersapi.feign;

import com.example.usersapi.bean.CommentBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name="comment")
public interface CommentClient {
    @GetMapping(value="/list")
    List<CommentBean> getAllComments();

    @GetMapping(value="/user/comment")
    List<CommentBean> getAllCommentsByUser();
}
