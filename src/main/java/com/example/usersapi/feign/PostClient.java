package com.example.usersapi.feign;

import com.example.usersapi.bean.PostBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name="post")
public interface PostClient {
    @GetMapping(value="/list")
    List<PostBean> getAllPosts();

    @GetMapping(value="/user")
    List<PostBean> getAllPostsByUser();
}
