package za.co.solinta.gatewayserver;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class GatewayApplicationLiveTest {

    @Test
    public void testAccess() {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        String testUrl = "http://localhost:8080";

        // validate that we can access our unprotected /organisation-service/books resource
        ResponseEntity<String> response = testRestTemplate
                .getForEntity(testUrl + "/organisation-service/books", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        // Now let's test that our users will be redirected to log in when visiting a protected resource
        // as an unauthenticated user by appending this code to the end of the test method
        response = testRestTemplate
                .getForEntity(testUrl + "/organisation-service/books/1", String.class);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals("http://localhost:8080/login", response.getHeaders()
                .get("Location").get(0));

        // Next, let's actually log in and then use our session to access the user protected result
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("username", "admin");
        form.add("password", "admin");
        response = testRestTemplate
                .postForEntity(testUrl + "/login", form, String.class);

        // now, let us extract the session from the cookie and propagate it to the following request
        String sessionCookie = response.getHeaders().get("Set-Cookie")
                .get(0).split(";")[0];
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionCookie);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        // and request the protected resource:
        response = testRestTemplate.exchange(testUrl + "/organisation-service/books/1",
                HttpMethod.GET, httpEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        // Now, let's try to access the admin section with the same session:
        // TODO there's no admin section atm
//        response = testRestTemplate.exchange(testUrl + "/rating-service/ratings/all",
//                HttpMethod.GET, httpEntity, String.class);
//        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

        // The next test will validate that we can log in as the admin and access the admin protected resource:
//        form.clear();
//        form.add("username", "admin");
//        form.add("password", "admin");
//        response = testRestTemplate
//                .postForEntity(testUrl + "/login", form, String.class);
//
//        sessionCookie = response.getHeaders().get("Set-Cookie").get(0).split(";")[0];
//        headers = new HttpHeaders();
//        headers.add("Cookie", sessionCookie);
//        httpEntity = new HttpEntity<>(headers);
//
//        response = testRestTemplate.exchange(testUrl + "/rating-service/ratings/all",
//                HttpMethod.GET, httpEntity, String.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());

        // Our final test is accessing our discovery server through our gateway. To do this add this code to the end of our test:
        response = testRestTemplate.exchange(testUrl + "/discovery",
                HttpMethod.GET, httpEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}