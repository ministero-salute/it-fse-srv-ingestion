/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import it.finanze.sanita.fse2.ms.srvingestion.config.Constants;
import it.finanze.sanita.fse2.ms.srvingestion.dto.response.LogTraceInfoDTO;
import it.finanze.sanita.fse2.ms.srvingestion.utility.JsonUtility;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(Constants.Profile.TEST)
class JsonUtilityTest {
    @Test
    @DisplayName("jsonToObject malformedInput")
    void testJsonToObjectMalformedInput() {
        String malformedInput = "{\"traceId\":}";
        assertEquals(null, JsonUtility.jsonToObject(malformedInput, LogTraceInfoDTO.class));
    }

    @Test
    @DisplayName("jsonToObject OK")
    void testJsonToObjectOk() {
        String input = "{\"traceId\":\"traceIdTest\",\"spanId\":\"spanIdTest\"}";
        Map<String, Object> map = new HashMap<>();
        map.put("traceId", "traceIdTest");
        map.put("spanId", "spanIdTest");
        assertEquals(map, JsonUtility.jsonToObject(input, Map.class));
    }

    @Test
    @DisplayName("objectToJson malformedInput")
    void testObjectToJsonMalformedInput() {
        Map<String, Object> input = new HashMap<>();
        input.put(null, null);
        assertEquals("", JsonUtility.objectToJson(input));
    }

    @Test
    @DisplayName("objectToJson OK")
    void testObjectToJsonOK() {
        Map<String, Object> input = new HashMap<>();
        input.put("key", "value");
        assertEquals("{\"key\":\"value\"}", JsonUtility.objectToJson(input));
    }
}
