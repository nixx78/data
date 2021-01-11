package lv.nixx.samples.json;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import lv.nixx.samples.json.domain.Person;

import org.junit.Test;

import com.google.gson.Gson;

public class GsonTest {

	@Test
	public void createJson() throws ParseException{
		
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		
		Person p = new Person();
		p.setId(100);
		p.setName("name");
		p.setDateOfBirth(df.parse("01.12.1978"));
		p.setSalary(BigDecimal.valueOf(100));
		
		Gson gson = JsonHelper.gson();
		String json = gson.toJson(p);
		System.out.println(json);
		
		assertNotNull(json);
		assertEquals("{\"id\":100,\"name\":\"name\",\"dateOfBirth\":\"01.12.1978\",\"salary\":100.00}", json);
	}
	
	@Test
	public void parseJson1(){
		String s = "{\"id\":100,\"name\":\"name\",\"dateOfBirth\":\"06.12.1978\",\"salary\":100.45}";
		Gson gson = JsonHelper.gson();
		Person p = gson.fromJson(s, Person.class);
		System.out.println(p);
		System.out.println(p.getSalary());
		assertEquals("100.45", p.getSalary().toString());
	}
	
	@Test
	public void parseJson2(){
		String s = "{\"id\":100,\"name\":\"name\",\"dateOfBirth\":\"06.12.1978\",\"salary\":100}";
		Gson gson = JsonHelper.gson();
		Person p = gson.fromJson(s, Person.class);
		System.out.println(p);
		System.out.println(p.getSalary());
		assertEquals("100.00", p.getSalary().toString());
	}

	
}
