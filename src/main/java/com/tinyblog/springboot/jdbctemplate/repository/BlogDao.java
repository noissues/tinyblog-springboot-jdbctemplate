package com.tinyblog.springboot.jdbctemplate.repository;

import java.util.List;

import com.tinyblog.springboot.jdbctemplate.model.Blog;

/**
 * @see ;
 */
public interface BlogDao {
    
    List<Blog> getAll();
    
    int deleteById(Long id);
    
    Blog findById(Long id);
    
    List<Blog> findByTitle(String title);
    
    int save(Blog blog);
    
    int update(Blog blog);
}
