package ntou.cs.YTComment.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class KeysConfig {
	
	private static String key1;
	private static String key2;	
	private static String key3;
	private static String key4;
	private static String key5;
	private static String key6;
//	@Autowired //for local testing
//	public KeysConfig(@Value("${my.key1}") String key1,@Value("${my.key2}") String key2,@Value("${my.key3}") String key3,@Value("${my.key4}") String key4,@Value("${my.key5}") String key5) {
//		this.key1 = key1;
//		this.key2 = key2;
//		this.key3 = key3;
//		this.key4 = key4;
//		this.key5 = key5;
//		this.key6 = key6;
//	}
	
	@Autowired
	public KeysConfig() {
		this.key1 = System.getenv().get("my.key1");
		this.key2 = System.getenv().get("my.key2");
		this.key3 = System.getenv().get("my.key3");
		this.key4 = System.getenv().get("my.key4");
		this.key5 = System.getenv().get("my.key5");
		this.key6 = System.getenv().get("my.key6");
	}
	
	public static List<String> getKeys(){
		List<String> keys = new ArrayList<String>();
		keys.add(key1);
		keys.add(key2);
		keys.add(key3);
		keys.add(key4);
		keys.add(key5);
		keys.add(key6);
		return keys;
	}
	
}
