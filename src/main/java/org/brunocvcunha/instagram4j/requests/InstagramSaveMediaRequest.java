package org.brunocvcunha.instagram4j.requests;

import java.util.LinkedHashMap;
import java.util.Map;

import org.brunocvcunha.instagram4j.requests.payload.StatusResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class InstagramSaveMediaRequest extends InstagramPostRequest<StatusResult> {

    protected long mediaId;

    @Override
    public String getUrl() {
        return "media/" + mediaId +"/save/";
    }

    @Override
    @SneakyThrows
    public String getPayload() {
        
        Map<String, Object> likeMap = new LinkedHashMap<>();
        likeMap.put("_uuid", api.getUuid());
        likeMap.put("_uid", api.getUserId());
        likeMap.put("_csrftoken", api.getOrFetchCsrf());
        likeMap.put("media_id", mediaId);
        
        ObjectMapper mapper = new ObjectMapper();
        String payloadJson = mapper.writeValueAsString(likeMap);

        return payloadJson;
    }

    @Override
    @SneakyThrows
    public StatusResult parseResult(int statusCode, String content) {
        return parseJson(statusCode, content, StatusResult.class);
    }

}
