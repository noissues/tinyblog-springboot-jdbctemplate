package com.tinyblog.springboot.jdbctemplate.service.impl;

import java.util.List;
import java.util.Map;

import com.tinyblog.springboot.jdbctemplate.model.BlogStatus;
import com.tinyblog.springboot.jdbctemplate.model.Blog;
import com.tinyblog.springboot.jdbctemplate.repository.BlogDao;
import com.tinyblog.springboot.jdbctemplate.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl implements BlogService {
    
    @Autowired
    private BlogDao blogDao;

    @Override
    public Blog create(Blog blog) {
        if (blog.getStatus() == null)
            blog.setStatus(BlogStatus.DRAFT.ordinal());
        Long id = blogDao.save(blog);
        blog.setId(id);
        return blog;
    }

    @Override
    public List<Blog> getBlogsByCondition(Map<String, Object> param) {
        if (param.get("title") != null)
            return blogDao.findByTitle((String) param.get("title"));
        
        return blogDao.getAll();
    }
    
    public Blog getBlogById(Long id) {
        return blogDao.findById(id);
    }
    

    @Override
    public int update(Blog blog) {
        return blogDao.update(blog);
    }

    @Override
    public int deleteById(Long id) {
        return blogDao.deleteById(id);
    }


}
