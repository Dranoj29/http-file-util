package com.example.demo.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.HttpResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class HttpFileUtil {
	
	public HttpResult post(String url, MultipartFile file) throws IOException{
		return this.request(url, multipartToFile(file), null);
	}
	
	public HttpResult post(String url, MultipartFile file, Map<String, Object> param) throws IOException{
		return this.request(url, multipartToFile(file), param);
	}
	
	public HttpResult post(String url, File file) throws IOException{
		return this.request(url, file, null);
	}
	
	public  HttpResult post(String url, File file, Map<String, Object> param) throws IOException{
		return this.request(url, file, param);
	}
	
	private HttpResult request(String url, File file, Map<String, Object> param) throws IOException{
		try (CloseableHttpClient client = HttpClients.createDefault()) {
		    HttpPost post = new HttpPost(url);
		    
		    HttpEntity entity = buildMultipart(file, param);
		    post.setEntity(entity);

		    try (CloseableHttpResponse response = client.execute(post)) {
		    	String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
	            
	            ObjectMapper mapper = new ObjectMapper();
	           // Map<?, ?> responseObject = mapper.readValue(responseString, Map.class);

	            //HttpResult result = new HttpResult(response.getStatusLine().getStatusCode(), responseObject);
	            HttpResult result = new HttpResult();
	            result.setStatusCode(response.getStatusLine().getStatusCode());
	            Map<String, Object> map = new HashMap<>();
	            map.put("message", responseString);
	            result.setBody(map);
	            
	            return result;

		    } catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				//delete tmp file
				if(file.exists()) file.delete();
			}
		}
		return null;
	}
	
	//build multipart/form-data payload
	private static HttpEntity buildMultipart(File file, Map<String, Object> param){
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		
		builder.addBinaryBody("part", file, ContentType.DEFAULT_BINARY, file.getName());
		if(param != null){
			for(Map.Entry<String, Object> map : param.entrySet()){
				if(map.getValue()!=null)
				builder.addPart(map.getKey(), new StringBody(map.getValue().toString(), ContentType.MULTIPART_FORM_DATA));
			}
		}
		return builder.build();
	}
	
	//convert multipart to file
	public static File multipartToFile(MultipartFile file) throws IllegalStateException, IOException{
		File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+file.getOriginalFilename());
	    file.transferTo(convFile);
	    return convFile;
	}

}
