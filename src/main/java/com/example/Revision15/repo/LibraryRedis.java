package com.example.Revision15.repo;

import com.example.Revision15.Constants;
import com.example.Revision15.model.Books;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;


@Repository
public class LibraryRedis {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public List<Books> findAll(){
        return redisTemplate.opsForHash().entries(Constants.LIBRARY).values().stream()
                .map(Books.class::cast)
                .collect(Collectors.toList());
    }



    public void save(String id, Books book){
        redisTemplate.opsForHash().put(Constants.LIBRARY, book.getId(), book);
    }
}
