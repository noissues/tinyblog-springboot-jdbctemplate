package com.tinyblog.springboot.jdbctemplate.repository;

import java.util.List;

import com.tinyblog.springboot.jdbctemplate.model.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;


@Repository
public class JdbcBlogDao implements BlogDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    
    @Override
    public List<Blog> getAll() {
        return jdbcTemplate.query("SELECT * FROM blog", blogRowMapper);
    }

    // rowMapper 的方式
    private final RowMapper<Blog> blogRowMapper = (resultSet, rowNum) -> {
        Blog blog = new Blog();
        blog.setId(resultSet.getLong("id"));
        blog.setDescription(resultSet.getString("description"));
        blog.setStatus(resultSet.getInt("status"));
        blog.setTitle(resultSet.getString("title"));
        blog.setContent(resultSet.getString("content"));
        return blog;
    };
    
    
    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM blog WHERE id=?", id);
    }

    @Override
    public Blog findById(Long id) {
        try {
            // 使用 BeanPropertyRowMapper
            return jdbcTemplate.queryForObject("SELECT * FROM blog WHERE id=?",
                BeanPropertyRowMapper.newInstance(Blog.class), id);
        } catch (IncorrectResultSizeDataAccessException e) {
            // 注意这里需要进行异常处理，当查询不到记录的时候就会抛异常 EmptyResultDataAccessException，
            // 当查询到多条记录时候会抛出 IncorrectResultSizeDataAccessException
            return null;
        }
    }

    @Override
    public List<Blog> findByTitle(String title) {
        // SELECT * FROM blog WHERE title LIKE '%spring%';
        String sql = "SELECT * FROM blog WHERE title LIKE '%" + title +"%'";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Blog.class));
    }

    @Override
    public int save(Blog blog) {
        return jdbcTemplate.update("INSERT INTO blog (title, description, status, content) VALUES(?,?,?,?)",
            blog.getTitle(), blog.getDescription(), blog.getStatus(), blog.getContent());
    }

    @Override
    public int update(Blog blog) {
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(blog);
        return namedParameterJdbcTemplate.update(
            "UPDATE blog SET title=:title, description=:description, status=:status WHERE id=:id", namedParameters);
    }
}
