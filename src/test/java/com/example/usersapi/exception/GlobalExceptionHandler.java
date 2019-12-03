//package com.example.usersapi.exception;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import static junit.framework.TestCase.assertNotNull;
//
//@RunWith(MockitoJUnitRunner.class)
//public class GlobalExceptionHandler {
//    private Exception error1;
//    @InjectMocks
//    private GlobalExceptionHandler globalExceptionHandler;
//    @Mock
//    private ErrorResponse errorResponse;
//
//    @Before
//    public void initErrorResponse(){
//        error1 = new Exception("message");
//    }
//
//    @Test
//    public void handleGeneralException() {
//
//        ReponseEntity<> response = globalExceptionHandler.handleGeneralException(error1);
//
//        assertNotNull(response);
//    }
//}
