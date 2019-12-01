package com.example.usersapi.bean;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PostBeanTest {
    private PostBean newPost;

    @InjectMocks
    private PostBean post;

    @Before
    public void initPost() {
        newPost = new PostBean(1,"title", "description", 1);
    }

    @Test
    public void getTitle() {
        assertEquals("title", newPost.getTitle());
    }

    @Test
    public void setTitle() {
        newPost.setTitle("new Title");
        assertEquals("new Title", newPost.getTitle());
    }

    @Test
    public void setId() {
        newPost.setId(2);
        assertEquals(2, newPost.getId());
    }
    @Test
    public void getDescription() {
        assertEquals("description", newPost.getDescription());
    }

    @Test
    public void setDescription() {
        newPost.setDescription("new Description");
        assertEquals("new Description", newPost.getDescription());
    }

    @Test
    public void setUserId() {
        newPost.setUserId(2);
        assertEquals(2, newPost.getUserId());
    }

    @Test
    public void getUserId() {
        assertEquals(1, newPost.getUserId());
    }
}
