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

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ntou.cs.YTComment.entity.Video;

public class YTSearcher {
	public ArrayList<Video> videos = new ArrayList<Video>();
	public String out;
	public YTSearcher(String query) throws IOException {
		initialize(query);
	}

	private void initialize(String query) throws IOException {
		String data = searchVideo(query);
		String element = produceDataJson(data);
		videos.addAll(convertToCommentObjects(element));
	}
	
	private String produceDataJson(String data) throws IOException{
			
		Map<String,String> map = constructFieldNameTranslationMap(); 
		JsonFactory factory = new JsonFactory();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		JSONObject obj = new JSONObject(data);

		JSONArray iterator = (JSONArray) obj.get("items");	
		try (JsonGenerator jsonGenerator = factory.createGenerator(new PrintStream(output))){
			jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
			jsonGenerator.writeStartArray();
			for (int i=0;i<iterator.length();i++) {
				JSONObject x = (JSONObject)((JSONObject)((JSONObject)iterator.get(i)).get("snippet"));
				JSONObject info = (JSONObject)((JSONObject)iterator.get(i)).get("id");
				jsonGenerator.writeStartObject();
				jsonGenerator.writeFieldName(map.get("videoId"));
				jsonGenerator.writeString(info.getString("videoId"));
				jsonGenerator.writeFieldName(map.get("title"));
				jsonGenerator.writeString(x.getString("title"));
				jsonGenerator.writeFieldName(map.get("description"));
				jsonGenerator.writeString(x.getString("description"));
				jsonGenerator.writeFieldName(map.get("thumbnail"));
				jsonGenerator.writeString(((JSONObject)((JSONObject)x.get("thumbnails")).get("high")).getString("url"));
				jsonGenerator.writeEndObject();
			}
			jsonGenerator.writeEndArray();
		}
		return output.toString();
	}
	
	private ArrayList<Video> convertToCommentObjects(String jsonData) {
		ArrayList<Video> video = new ArrayList<Video>();
		Type listType = new TypeToken<List<Video>>(){}.getType();
		video = new Gson().fromJson(jsonData, listType);
		return video;
	}
	
	private Map<String, String> constructFieldNameTranslationMap() {
		Map<String, String> fieldNameTranslationMap = new HashMap<String, String>();
		fieldNameTranslationMap.put("videoId", "videoId");
		fieldNameTranslationMap.put("title", "title");
		fieldNameTranslationMap.put("description", "description");
		fieldNameTranslationMap.put("thumbnail", "thumbnail");
		return fieldNameTranslationMap;
	}

	public static String searchVideo(String keyword) {
//		List<String> apiKeys = Arrays.asList("AIzaSyAJ6xW00QXj2RWwlk7sOFRZVGDKapp5nkE","AIzaSyA28AtN_t_C_8PmMZNN_To7Ffpss8Bilbo","AIzaSyA6glVOA702Cx23k3O_8jZ_ETiIYecCYjM","AIzaSyBCLxUjVz4OEWDcgqrFGR2qBXqoso6pp-Y")
		
		String myurl = "https://www.googleapis.com/youtube/v3/search?part=snippet&key=AIzaSyA6glVOA702Cx23k3O_8jZ_ETiIYecCYjM&maxResults=10&type=video&q=" + keyword;
		String temp = "";
		try {
			URL url = new URL(myurl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			System.out.println(myurl);
			System.out.println("Response Code : " + responseCode);
//			System.out.println(con.getErrorStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            while ((temp = br.readLine()) != null) {
                sb.append(temp+"\n");
            }
            br.close();
            temp = sb.toString();
		} 
		catch(Exception e){		
		}
		return temp;
	}
	
}
