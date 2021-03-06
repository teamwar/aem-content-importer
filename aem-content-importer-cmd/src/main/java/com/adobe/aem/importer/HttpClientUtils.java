/*******************************************************************************
* Copyright (c) 2014 Adobe Systems Incorporated. All rights reserved.
*
* Licensed under the Apache License 2.0.
* http://www.apache.org/licenses/LICENSE-2.0
******************************************************************************/


package com.adobe.aem.importer;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.sling.commons.json.JSONObject;

import com.adobe.aem.importer.xml.Config;

public class HttpClientUtils {

	public static JSONObject post(String url, String username, String password, Config config, File file) throws Exception {
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY,
				new UsernamePasswordCredentials(username, password));
		HttpClient httpClient = HttpClientBuilder.create()
				.setDefaultCredentialsProvider(credentialsProvider).build();

		HttpPost request = new HttpPost(url);

		MultipartEntity entity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);
		
		if (config != null) {
			entity.addPart("src", new StringBody(config.getSrc()));
			entity.addPart("transformer", new StringBody(config.getTransformer()));
			entity.addPart("masterFile", new StringBody(config.getMasterFile()));
			entity.addPart("target", new StringBody(config.getTarget()));
			entity.addPart("customCommandProps", new StringBody(config.getCustomProps()));
		}
		
		
		if (file != null) {
			FileBody fb = new FileBody(file);
			entity.addPart("fileselect", fb);
		}

		request.setEntity(entity);

		// add request header
		HttpResponse response = httpClient.execute(request);

		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		
		JSONObject jsonResult = new JSONObject(result.toString());
		
		return jsonResult;
	}

}
