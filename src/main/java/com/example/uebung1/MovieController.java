package com.example.uebung1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MovieController {

    private AtomicLong counter = new AtomicLong();
    private List<Movie> movieList = new ArrayList<>();

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getMovies() {
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    @PostMapping("/movies")
    public ResponseEntity<Void> postMovie(@RequestBody Movie newMovie) {
        if (newMovie.getName() == null || newMovie.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            newMovie.setId(counter.getAndIncrement());
            movieList.add(newMovie);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }
}
