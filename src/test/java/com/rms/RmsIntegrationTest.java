/**
 * 
 */
package com.rms;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rms.model.EmployeeModel;
import com.rms.repository.EmployeeRepository;
/**
 * @author Kanitta Moonlapong
 *
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RmsIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private static final ObjectMapper om = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeRepository mockRepository;

	@Test
	public void test_retrive_by_id_success() throws Exception {
	    // given : initialize mock data
		EmployeeModel bp1 = new EmployeeModel("001", "Smith", "L", "004424548", new BigDecimal("1452474.41"));
		when(mockRepository.findById("001")).thenReturn(
				Optional.of(bp1));
		String accessToken = obtainAccessToken("user", "user");
		mockMvc.perform(get("/employee-api/id/001").header("Authorization", "Bearer " + accessToken)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value("001"))
				.andExpect(jsonPath("$.firstName").value("Smith"))
				.andExpect(jsonPath("$.lastName").value("L"))
				.andExpect(jsonPath("$.contactNo").value("004424548"))
				.andExpect(jsonPath("$.salary").value(1452474.41));
	}
	
	@WithMockUser("user")
	@Test
	public void test_retrive_all() throws Exception {
		// given : initialize mock data
				EmployeeModel bp1 = new EmployeeModel("001", "Smith", "L", "004424548", new BigDecimal("1452474.41"));
				EmployeeModel bp2 = new EmployeeModel("002", "John", "L", "004424548", new BigDecimal("1452474.41"));
				List<EmployeeModel> employeeList = new ArrayList<>();
				employeeList.add(bp1);
				employeeList.add(bp2);
				
				when(mockRepository.findAll()).thenReturn(employeeList);
				
		String accessToken = obtainAccessToken("user", "user");
		mockMvc.perform(get("/employee-api/all")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].id").value("001"))
				.andExpect(jsonPath("$[0].firstName").value("Smith"))
				.andExpect(jsonPath("$[1].id").value("002"))
				.andExpect(jsonPath("$[1].firstName").value("John"))
				;
	}
	

	@Test
	public void test_save_success() throws Exception {
		String accessToken = obtainAccessToken("user", "user");
		String employeeInJson = "{\"id\":\"004\",\"firstName\":\"Marry\",\"lastName\":\"L\",\"contactNo\":\"004424548\",\"salary\":1452474.41}";

		mockMvc.perform(post("/employee-api/create").content(employeeInJson)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + accessToken)).andDo(print()).andExpect(status().isCreated());

		verify(mockRepository, times(0)).save(new EmployeeModel());

	}

	@Test
	public void test_save_invalid_emptyEmployee() throws Exception {
		String accessToken = obtainAccessToken("user", "user");
		String employeeInJson = "{\"id\":\"004\"}";

		mockMvc.perform(post("/employee-api/create").content(employeeInJson)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isBadRequest());

		verify(mockRepository, times(0)).save(new EmployeeModel());

	}


	@Test
	public void test_invalid_login_401() throws Exception {
		mockMvc.perform(get("/employee-api/id/001")).andDo(print()).andExpect(status().isUnauthorized());
	}

	private String obtainAccessToken(String username, String password) throws Exception {

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "password");
		params.add("username", username);
		params.add("password", password);
		params.add("scope", "openid");

		String base64ClientCredentials = new String(Base64.getEncoder().encode("clientId:clientSecret".getBytes()));

		ResultActions result = mockMvc.perform(post("/oauth/token").params(params)
				.header("Authorization", "Basic " + base64ClientCredentials).accept("application/json;charset=UTF-8"))
				.andExpect(status().isOk());

		String resultString = result.andReturn().getResponse().getContentAsString();

		JacksonJsonParser jsonParser = new JacksonJsonParser();
		return jsonParser.parseMap(resultString).get("access_token").toString();
	}
}
