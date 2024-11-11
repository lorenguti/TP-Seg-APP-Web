package com.example.demo.controller.rest;

import com.example.demo.model.ContractRequest;
import com.example.demo.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ApiRelayController {

    private final JwtUtil jwtUtil;
    private final RestTemplate restTemplate;

    @PostMapping("/contract")
    public ResponseEntity<String> callExternalApi(@RequestBody ContractRequest contractRequest ) {
        // Genera el token usando el email
        String token = jwtUtil.generateToken(contractRequest.getEmail());

        // Configura los headers, incluyendo el token JWT
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        // Crea la entidad HTTP con los headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Hace la llamada GET a la API externa
        ResponseEntity<String> response = restTemplate.exchange(contractRequest.getUrl(), HttpMethod.POST, entity, String.class);

        return ResponseEntity.ok(response.getBody());
    }


}
