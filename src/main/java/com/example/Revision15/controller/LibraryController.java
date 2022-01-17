package com.example.Revision15.controller;


import com.example.Revision15.model.Author;
import com.example.Revision15.model.Books;
import com.example.Revision15.service.LibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class LibraryController {

    private static final Logger logger = LoggerFactory.getLogger(LibraryController.class);

    @Autowired
    LibraryService libsvc;


    @GetMapping("savebooks")
    public String saveBooks(Model model){
        Books book = new Books();
        Author author = new Author();
        model.addAttribute("author", author);
        model.addAttribute("book",book );


        return  "savebook";
    }

    @PostMapping("saved")
    public String showSavedBooks(Model model, @ModelAttribute Books book, @ModelAttribute Author author){
        logger.info("Book title: "+ book.getTitle());
        logger.info("Book id: "+ book.getId());
        logger.info("Author object: "+ author.name);
        book.setAuthor(author);
        logger.info("Author: "+ book.getAuthor().getName());
        libsvc.save(book.getId(),book);
        logger.info("book has been saved");
        return "savebook";
    }


    @PostMapping("results")
    public String findBooks(@RequestBody MultiValueMap<String, String> form, Model model){

        String searchTerm = form.getFirst("searchTerm");
        List<Books> searchResults = new ArrayList<>(libsvc.findBySearchTerm(searchTerm));
        if(!searchResults.isEmpty()){
            model.addAttribute("searchResults",searchResults);
        } else{
        model.addAttribute("message","No search results");}

        return  "index";
    }

    @PostMapping("filtered")
    public String findBooks(Model model, @ModelAttribute List<Books> searchResults,
                            @RequestBody MultiValueMap<String, String> form) {
        Optional<String> pageLimit = Optional.ofNullable(form.getFirst("pageLimit"));
        if(pageLimit.isPresent()){
            searchResults = libsvc.limitSearchResults(
                    searchResults, Integer.parseInt(pageLimit.get()));
            model.addAttribute("searchResults", searchResults);
            return  "index";
        }

        return  "index";

    }




}
