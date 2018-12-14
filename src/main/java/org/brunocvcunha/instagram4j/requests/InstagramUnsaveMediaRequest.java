package org.brunocvcunha.instagram4j.requests;

public class InstagramUnsaveMediaRequest extends InstagramSaveMediaRequest {

    public InstagramUnsaveMediaRequest(long mediaId) {
        super(mediaId);
    }

    @Override
    public String getUrl() {
        return "media/" + mediaId + "/unsave/";
    }
}
