package com.example.Revision15.controller;


import com.example.Revision15.model.Books;
import com.example.Revision15.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Library")
public class LibraryController {

    @Autowired
    LibraryService libsvc;

    @GetMapping("/savebooks")
    public String saveBooks(Model model){
        Books book = new Books();
        model.addAttribute("book",book );
        libsvc.save(book.getId(),book);
        return  "savebook";
    }


    @PostMapping
    public String findBooks(@RequestBody MultiValueMap<String, String> form, Model model){

        String searchTerm = form.getFirst("searchTerm");
        List<Books> searchResults = new ArrayList<>(libsvc.findBySearchTerm(searchTerm));
        model.addAttribute("searchResults",searchResults);
        return  "index";
    }

    @PostMapping
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
