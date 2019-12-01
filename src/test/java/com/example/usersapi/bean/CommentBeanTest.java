package com.example.usersapi.bean;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CommentBeanTest {
    private CommentBean newComment;

    @InjectMocks
    private CommentBean comment;

    @Before
    public void initComment() {
        newComment = new CommentBean(1,"text", 1, 1);
    }

    @Test
    public void getTitle() {
        assertEquals("text", newComment.getText());
    }

    @Test
    public void setTitle() {
        newComment.setText("new text");
        assertEquals("new text", newComment.getText());
    }

    @Test
    public void setId() {
        newComment.setId(2);
        assertEquals(2, newComment.getId());
    }

    @Test
    public void getPostId() {
        assertEquals(1, newComment.getPostId());
    }

    @Test
    public void setPostId() {
        newComment.setPostId(2);
        assertEquals(2, newComment.getPostId());
    }

    @Test
    public void getUserId() {
        assertEquals(1, newComment.getUserId());
    }

    @Test
    public void setUserId() {
        newComment.setUserId(2);
        assertEquals(2, newComment.getUserId());
    }
}
