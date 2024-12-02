package com.healthmanagerservice.healthmanagerservice.infrastructure.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class IBGEStateClient {
    private static final String IBGE_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/estados";

    @Autowired
    private RestTemplate restTemplate;

    public List<String> getBrazilianStates() {
        Estado[] estados = restTemplate.getForObject(IBGE_URL, Estado[].class);
        return estados != null ?
                Stream.of(estados).map(Estado::getSigla).collect(Collectors.toList()) :
                List.of();
    }
}
