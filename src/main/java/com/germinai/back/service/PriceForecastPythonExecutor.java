package com.germinai.back.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

@Service
public class PriceForecastPythonExecutor {
    private static final String PYTHON_EXE = "python";
    private static final String SCRIPT_PATH = "C:\\Github\\projeto_integrador\\machine_learning\\producao\\predict.py";

    public List<Double> prever(List<Double> prices, int days) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String inputJson = mapper.writeValueAsString(
                    Map.of("prices", prices, "days", days)
            );

            Process process = new ProcessBuilder(PYTHON_EXE, SCRIPT_PATH)
                    .redirectErrorStream(true)
                    .start();

            // ENVIA JSON PARA STDIN DO PYTHON (sem InputStreamWriter)
            try (OutputStream os = process.getOutputStream()) {
                os.write(inputJson.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }


            // LÊ STDOUT DO PYTHON
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            process.waitFor();
            String output = sb.toString().trim();

            System.out.println("PYTHON OUTPUT => " + output); // diagnóstico

            Map<String, Object> result = mapper.readValue(output, Map.class);

            if (result.containsKey("error")) {
                throw new RuntimeException("Erro retornado pelo modelo: " + result.get("error"));
            }

            return (List<Double>) result.get("forecast");

        } catch (Exception e) {
            throw new RuntimeException("Erro ao executar previsão", e);
        }
    }

}
