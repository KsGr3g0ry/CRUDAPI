package com.example.demo;
import static org.hamcrest.Matchers.is;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import com.example.demo.dao.UserRepository;
import com.example.demo.model.User;
import javax.print.attribute.standard.Media;
import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;
import java.beans.Transient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
public class TestUser {
    @Autowired
    UserRepository repository;
    @Autowired
    MockMvc mvc;

    @Test
    @Transactional
    @Rollback //get All
    public void getInfo() throws Exception{
        User grabInfo = new User();
        grabInfo.setEmail("jimmyja@yahoo.com");
        grabInfo.setPassword("741028dfd");
        repository.save(grabInfo);

        MockHttpServletRequestBuilder request = get("/users")
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email", is("jimmyja@yahoo.com")))
                .andExpect(jsonPath("$[0].password", is("741028dfd")));
    }

    @Test
    @Transactional
    @Rollback //get specificUserId
    public void getUserId() throws Exception{
        User userId = new User();
        userId.setEmail("wayne@icq.com");
        userId.setPassword("3698");
        User searchUserById = repository.save(userId);
       // String json1 = ("{\"email\":\"wayne@icq.com\", \"password\":\"3698\"}");

        MockHttpServletRequestBuilder request = get("/users/" + searchUserById.getId());
                //.contentType(MediaType.APPLICATION_JSON)
               // .content(json1);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("wayne@icq.com")));
    }



    @Test
    @Transactional
    @Rollback //post
    public void postUser() throws Exception{
        String json = ("{\"email\":\"jimmyja@yahoo.com\", \"password\":\"741028dfd\"}");

        MockHttpServletRequestBuilder request = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email",is("jimmyja@yahoo.com")))
                .andExpect(jsonPath("$.password").doesNotExist());

    }

    @Test
    @Transactional
    @Rollback //patch
    public void updateUser() throws Exception{
        User user2 = new User();
        user2.setEmail("wayne@aol.com");
        user2.setPassword("1234");
        User updateUser = repository.save(user2);

        updateUser.getId();
        String json1 = ("{\"email\":\"wayne@aol.com\", \"password\":\"1234\"}");

        MockHttpServletRequestBuilder request = patch("/users/" + updateUser.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json1);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email",is("wayne@aol.com")));
    }

    @Test
    @Transactional
    @Rollback //deleteById
    public void deleteUser() throws Exception{
        User deleteUserId = new User();
        deleteUserId.setEmail("yahoo.com");
        deleteUserId.setPassword("7896");
        User newDeletedUserId = repository.save(deleteUserId);

        MockHttpServletRequestBuilder request = delete("/users/" + newDeletedUserId.getId());

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count", is(0)));

    }






}
