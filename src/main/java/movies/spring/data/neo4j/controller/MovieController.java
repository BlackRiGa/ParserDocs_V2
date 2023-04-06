package movies.spring.data.neo4j.controller;

import movies.spring.data.neo4j.models.Neo4jModel.MovieModel;
import movies.spring.data.neo4j.repositories.repositoryForNeo4j.MovieRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@RestController
@RequestMapping("/movies")
public class MovieController {
    @Resource
    private MovieRepository movieRepository;
    @PutMapping
    Mono<MovieModel> createOrUpdateMovie(@RequestBody MovieModel newMovie) {
        return movieRepository.save(newMovie);
    }

    @GetMapping(value = { "", "/" })
    Flux<MovieModel> getMovies() {
        return movieRepository.findAll();
    }

    @GetMapping("/by-title")
    Mono<MovieModel> byTitle(@RequestParam String title) {
        return movieRepository.findOneByTitle(title);
    }

    @DeleteMapping("/{id}")
    Mono<Void> delete(@PathVariable Long id) {
        return movieRepository.deleteById(id);
    }
}


