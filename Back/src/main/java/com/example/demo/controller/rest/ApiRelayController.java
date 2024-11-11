package com.example.demo.controller.rest;

import com.example.demo.model.ContractRequest;
import com.example.demo.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ApiRelayController {

    private final JwtUtil jwtUtil;
    private final RestTemplate restTemplate;

    @PostMapping("/contract")
    public ResponseEntity<String> callExternalApi(@RequestBody ContractRequest contractRequest) {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        try {
            // Genera el token usando el email
            String token = jwtUtil.generateToken(contractRequest.getEmail());
            logger.info(token);
            // Configura los headers, incluyendo el token JWT
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            // Crea la entidad HTTP con los headers
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Hace la llamada POST a la API externa
            ResponseEntity<String> response = restTemplate.exchange(
                    contractRequest.getUrl(), HttpMethod.GET, entity, String.class);

            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            logger.error("Error al llamar a la API externa: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la solicitud");
        }
    }


}
