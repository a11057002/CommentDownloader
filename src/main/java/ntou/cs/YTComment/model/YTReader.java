package ntou.cs.YTComment.model;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ntou.cs.YTComment.entity.Comment;
import ntou.cs.YTComment.entity.KeysConfig;

public class YTReader {

	public ArrayList<Comment> comment = new ArrayList<Comment>();
	public String nextPageToken = "";
	public String id;
	private String line = "";
	private String keyword;
	
	public YTReader(String id,String keyword) throws Exception {
		this.id=id;
		this.keyword = keyword;
		initialize(id);
	}
	
	private void initialize(String id) throws Exception{
		while(this.nextPageToken!=null) {
			String data = parseVideo(id);
			String dataJson = produceDataJson(data);
			comment.addAll(convertToCommentObjects(dataJson));		
		}
	}
	

	private String produceDataJson(String data) throws IOException{
		
		Map<String,String> map = constructFieldNameTranslationMap(); 
		JsonFactory factory = new JsonFactory();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		JSONObject obj = new JSONObject(data);
		if(obj.has("nextPageToken"))
			this.nextPageToken = obj.get("nextPageToken").toString();
		else
			this.nextPageToken = null;
//		
		JSONArray iterator = (JSONArray) obj.get("items");
//		System.out.println(((JSONObject)((JSONObject)((JSONObject)iterator.get(0)).get("snippet")).get("topLevelComment")).get("snippet"));
		
		try (JsonGenerator jsonGenerator = factory.createGenerator(new PrintStream(output))){
			jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
			jsonGenerator.writeStartArray();
			for (int i=0;i<iterator.length();i++) {
				JSONObject x = (JSONObject) (((JSONObject)((JSONObject)((JSONObject)iterator.get(i)).get("snippet")).get("topLevelComment")).get("snippet"));
				
				jsonGenerator.writeStartObject();
				jsonGenerator.writeFieldName(map.get("name"));
				jsonGenerator.writeString(x.getString("authorDisplayName"));
				jsonGenerator.writeFieldName(map.get("text"));
				jsonGenerator.writeString(x.getString("textOriginal"));
				jsonGenerator.writeFieldName(map.get("img"));
				jsonGenerator.writeString(x.getString("authorProfileImageUrl"));
				jsonGenerator.writeFieldName(map.get("time"));
				jsonGenerator.writeString(x.getString("publishedAt"));
				jsonGenerator.writeEndObject();
			}
			jsonGenerator.writeEndArray();
		}
	
		return output.toString();
	}
	
	private ArrayList<Comment> convertToCommentObjects(String jsonData) {
		ArrayList<Comment> comment = new ArrayList<Comment>();
		Type listType = new TypeToken<List<Comment>>(){}.getType();
		comment = new Gson().fromJson(jsonData, listType);
		return comment;
	}
	
	private Map<String, String> constructFieldNameTranslationMap() {
		Map<String, String> fieldNameTranslationMap = new HashMap<String, String>();
		fieldNameTranslationMap.put("name", "name");
		fieldNameTranslationMap.put("text", "text");
		fieldNameTranslationMap.put("img", "img");
		fieldNameTranslationMap.put("time", "time");
		return fieldNameTranslationMap;
	}
	
	private String parseVideo(String id) throws Exception{
		
		List<String> keys = KeysConfig.getKeys();
		int count = 0;
		while(true) {
			try {
				String myurl = "https://www.googleapis.com/youtube/v3/commentThreads?part=snippet&key="+ keys.get(count)
				+ "&maxResults=100&videoId=" + id + "&pageToken="+this.nextPageToken + "&searchTerms=" + this.keyword;
				URL url = new URL(myurl);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				int responseCode = con.getResponseCode();
				System.out.println(myurl);
				System.out.println("Response Code : " + responseCode);
		//			System.out.println(con.getErrorStream());
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		        StringBuilder sb = new StringBuilder();
		        while ((line = br.readLine()) != null) {
		            sb.append(line+"\n");
		        }
		        br.close();
		        line = sb.toString();
		        return line;
			} 
			catch(Exception e){		
				if(++count==keys.size()) throw e;
			}
		}
		
	}
	
}
