package ntou.cs.YTComment.controller;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ntou.cs.YTComment.entity.Comment;
import ntou.cs.YTComment.model.YTReader;

@Controller
@CrossOrigin
public class YTController {
	YTReader read;
	@GetMapping("/video/{id}")
	public ResponseEntity<ArrayList<Comment>> getVideo(@PathVariable("id") String id,Model model) throws IOException{
		read = new YTReader(id);
		return ResponseEntity.ok().body(read.comment);
	}
}
