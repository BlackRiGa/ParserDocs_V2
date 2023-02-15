package movies.spring.data.neo4j.controller;

import movies.spring.data.neo4j.dto.MovieDetailsDto;
import movies.spring.data.neo4j.dto.MovieResultDto;
import movies.spring.data.neo4j.service.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Michael J. Simons
 */
@RestController
class MovieController {

    private final MovieService movieService;

    MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    private static String stripWildcards(String title) {
        String result = title;
        if (result.startsWith("*")) {
            result = result.substring(1);
        }
        if (result.endsWith("*")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    @GetMapping("/movie/{title}")
    public MovieDetailsDto findByTitle(@PathVariable("title") String title) {
        return movieService.fetchDetailsByTitle(title);
    }

    @GetMapping("/search")
    List<MovieResultDto> search(@RequestParam("q") String title) {
        return movieService.searchMoviesByTitle(stripWildcards(title));
    }

    @GetMapping("/graph")
    public Map<String, List<Object>> getGraph() {
        return movieService.fetchMovieGraph();
    }


    // TODO: 07.02.2023
//СОЗДАТЬ НОВУЮ ЗАПИСЬ
//	@PostMapping
//	public ResultEvent<Movie> createEventByCalendars(@PathVariable int id) {
//		if (bindingResult.hasErrors()) returnErrorsToClient(bindingResult);
//		return new ResultEvent<Event>(eventService.save(event));
//	}
//
//
// УДАЛИТЬ ЗАПИСЬ
//	@PostMapping
//	public ResultEvent<Movie> createEventByCalendars(@PathVariable int id) {
//		if (bindingResult.hasErrors()) returnErrorsToClient(bindingResult);
//		return new ResultEvent<Event>(eventService.save(event));
//	}

}
