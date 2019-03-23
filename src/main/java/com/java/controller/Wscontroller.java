package com.java.controller;

import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.java.request.Login;
import java.io.InputStream;

import javax.xml.bind.ValidationException;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

@Controller
@RestController
public class Wscontroller {

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	public String Login(@RequestBody Login request) {
		try {
			validator(request);
		} catch (ValidationException e) {
			e.printStackTrace();
		}
		//pagina para convertir json en squema
		  //https://www.liquid-technologies.com/online-json-to-schema-converter
		//pagina para validar squema
		  //https://www.jsonschemavalidator.net/
		//url para prueba en postman
		  //http://localhost:8080/ValidacionEsquemaJson/login
		//json de entrada
		  /*{
			"usuario":"user",
		    "password":"pass"
		  
		  }*/
		return "hola mundo";
	}

	@Test
	public void validator(Login request) throws ValidationException {
		if (request != null) {
			JSONObject jsonSubject = null;
			ObjectMapper mapper = new ObjectMapper();
			InputStream inputStream = getClass().getResourceAsStream("login.json");
			JSONObject jsonSchema = new JSONObject(new JSONTokener(inputStream));
			Schema schema = SchemaLoader.load(jsonSchema);
			try {
				jsonSubject = new JSONObject(new JSONTokener(mapper.writeValueAsString(request)));
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			try {
				System.out.println("todo correcto");
				schema.validate(jsonSubject);
			} catch (Exception ex) {
				System.out.println("Error" + ex);
			}
		} else {
		}
	}

}
