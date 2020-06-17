package ntou.cs.YTComment.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ntou.cs.YTComment.entity.Comment;
import ntou.cs.YTComment.entity.KeysConfig;
import ntou.cs.YTComment.entity.Video;
import ntou.cs.YTComment.model.YTReader;
import ntou.cs.YTComment.model.YTSearcher;

@Controller
@CrossOrigin
public class YTController {
	YTReader read;
	YTSearcher search;
	
	@GetMapping("/video/{id}")
	public ResponseEntity<ArrayList<Comment>> getVideo(@PathVariable("id") String id,@RequestParam(required = false) String keyword) throws Exception{
		if(keyword==null)keyword = "";
		System.out.println(keyword);
		read = new YTReader(id,URLEncoder.encode(keyword,"UTF-8"));
		return ResponseEntity.ok().body(read.comment);
	} 
	
//	@PostMapping("/video/{id}")
//	public ResponseEntity<ArrayList<Comment>> getVideo(@PathVariable("id") String id,@RequestBody String keyword) throws IOException{
//		if(keyword==null)keyword = "";
//		read = new YTReader(id,keyword);
//		return ResponseEntity.ok().body(read.comment);
//	}
	
	@GetMapping("/search")
	public ResponseEntity<ArrayList<Video>> searchVideo(@RequestParam String keyword) throws Exception{
		search = new YTSearcher(keyword);
		return ResponseEntity.ok().body(search.videos);
	}
}
