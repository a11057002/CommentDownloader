package ntou.cs.YTComment.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ntou.cs.YTComment.entity.Comment;
import ntou.cs.YTComment.model.YTReader;

@Controller
@CrossOrigin
public class YTController {
	YTReader read;
	@GetMapping("/video/{id}")
	public ResponseEntity<ArrayList<Comment>> getVideo(@PathVariable("id") String id,Model model) throws IOException{
		read = new YTReader(id);
	
		model.addAttribute("data",read.comment);
		model.addAttribute("id",read.id);
//		model.addAttribute("next",read.nextPageToken);
//		model.addAttribute("id",read.id);
		return ResponseEntity.ok().body(read.comment);
	}
}
