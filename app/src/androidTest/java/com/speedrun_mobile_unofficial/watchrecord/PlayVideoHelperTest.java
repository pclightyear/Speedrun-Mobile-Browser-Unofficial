package com.speedrun_mobile_unofficial.watchrecord;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayVideoHelperTest {

    @Test
    public void IfFailToParseLinkNormal() {
        String link = "http://www.youtube.com/watch?v=dQw4w9WgXcZ";
        String expected = "dQw4w9WgXcZ";

        String actual = PlayVideoHelper.extractYoutubeVideoIdFromLink(link);

        assertEquals(actual, expected);
    }

    @Test
    public void IfFailToParseLinkWithMultipleParams1() {
        String link = "http://www.youtube.com/watch?v=dQw4w9WgXcZ&a=GxdCwVVULXctT2lYDEPllDR0LRTutYfW";
        String expected = "dQw4w9WgXcZ";

        String actual = PlayVideoHelper.extractYoutubeVideoIdFromLink(link);

        assertEquals(actual, expected);
    }

//    @Test
//    public void IfFailToParseLinkWithMultipleParams2() {
//        String link = "http://www.youtube.com/watch?feature=player_embedded&v=dQw4w9WgXcQ";
//        String expected = "dQw4w9WgXcQ";
//
//        String actual = PlayVideoHelper.extractYoutubeVideoIdFromLink(link);
//
//        assertEquals(actual, expected);
//    }

    @Test
    public void IfFailToParseSharedLink() {
        String link = "http://www.youtube.com/watch?v=dQw4w9WgXcZ&feature=share";
        String expected = "dQw4w9WgXcZ";

        String actual = PlayVideoHelper.extractYoutubeVideoIdFromLink(link);

        assertEquals(actual, expected);
    }

    @Test
    public void IfFailToParseAbbrLink() {
        String link = "http://youtu.be/dQw4w9WgXcZ";
        String expected = "dQw4w9WgXcZ";

        String actual = PlayVideoHelper.extractYoutubeVideoIdFromLink(link);

        assertEquals(actual, expected);
    }

    @Test
    public void IfFailToPureId() {
        String link = "dQw4w9WgXcZ";
        String expected = "dQw4w9WgXcZ";

        String actual = PlayVideoHelper.extractYoutubeVideoIdFromLink(link);

        assertEquals(actual, expected);
    }
}