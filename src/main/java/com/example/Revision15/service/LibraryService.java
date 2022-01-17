package com.example.Revision15.service;

import com.example.Revision15.Constants;
import com.example.Revision15.model.Books;
import com.example.Revision15.repo.LibraryRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class LibraryService {

    @Autowired
    LibraryRedis libRedis;

    public List<Books> findBySearchTerm(String searchTerm){
            List<Books> searchResults = libRedis.findAll().stream()
                    .filter(x -> searchTerm.equals(x.getAuthor().name) || searchTerm.equals(x.getTitle()))
                    .collect(Collectors.toList());
                    return searchResults;
    }

    public List<Books> limitSearchResults(List<Books> allResults, int resultLimit){
        return allResults.stream().limit(resultLimit).collect(Collectors.toList());
    }

    public List<Books> sortByAuthor(List<Books> books){
       return books.stream().sorted(Comparator.comparing(x-> x.getAuthor().getName())).collect(Collectors.toList());
    }

    public List<Books> sortByTitle(List<Books> books){
        return books.stream().sorted(Comparator.comparing(Books::getTitle)).collect(Collectors.toList());
    }


    public void save(String id, Books book){
        libRedis.save(id,book);
    }



}
