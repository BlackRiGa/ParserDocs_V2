package movies.spring.data.neo4j.controller;

import movies.spring.data.neo4j.service.ParsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ParserController {
    private final ParsersService parsersService ;

    @Autowired
    public ParserController(ParsersService parsersService) {
        this.parsersService = parsersService;
    }

    @PostMapping
    @RequestMapping("/parse")
    public String parseWithMp4(@RequestBody String text1){
        return parsersService.sendTextForParse(text1);
    }
}
