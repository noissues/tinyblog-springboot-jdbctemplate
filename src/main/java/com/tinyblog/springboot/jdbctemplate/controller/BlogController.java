package com.tinyblog.springboot.jdbctemplate.controller;

import java.util.HashMap;
import java.util.List;

import com.tinyblog.springboot.jdbctemplate.common.util.BeanUtils;
import com.tinyblog.springboot.jdbctemplate.model.Blog;
import com.tinyblog.springboot.jdbctemplate.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class BlogController {
    
    @Autowired
    BlogService blogService;
    
    @GetMapping("/blog")
    public ResponseEntity<List<Blog>> getBlogs(@RequestParam(required = false) String title) {
        HashMap<String, Object> param = new HashMap<>();
        if (title != null)
            param.put("title", title);
        
        List<Blog> blogs = blogService.getBlogsByCondition(param);
        
        if (blogs == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }
    
    @GetMapping("/blog/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable("id") Long id) {
        Blog blog = blogService.getBlogById(id);
        
        if (blog == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        return new ResponseEntity<>(blog, HttpStatus.OK);
    }

    @PostMapping("/blog")
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog) {
        Blog newBlog = blogService.create(blog);
        return new ResponseEntity<>(newBlog, HttpStatus.CREATED);
    }

    @PutMapping("/blog/{id}")
    public ResponseEntity<String> updateBlog(@PathVariable("id") long id, @RequestBody Blog blog) {
        Blog oldBlog = blogService.getBlogById(id);

        if (oldBlog == null)
            return new ResponseEntity<>("Cannot find Blog with id=" + id, HttpStatus.NOT_FOUND);

        // 否则进行更新
        // BeanUtils.copyProperties(blog, oldBlog);
        BeanUtils.copyNonNullProperties(blog, oldBlog);

        blogService.update(oldBlog);
        return new ResponseEntity<>("Blog was updated successfully.", HttpStatus.OK);
    }


    @DeleteMapping("/blog/{id}")
    public ResponseEntity<String> deleteBlog(@PathVariable("id") long id) {
        try {
            int result = blogService.deleteById(id);
            if (result == 0)
                return new ResponseEntity<>("Cannot find Blog with id=" + id, HttpStatus.OK);
            
            return new ResponseEntity<>("Blog was deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete blog.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
