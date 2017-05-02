package com.github.chojmi.inspirations.data.entity.helpers;

import java.util.Locale;

public final class ImageLinkProvider {
    private ImageLinkProvider() {
        throw new AssertionError();
    }

    public static String provideLink(int farm, int server, String nsid) {
        if (server > 0) {
            return String.format(Locale.ENGLISH, "https://farm%d.staticflickr.com/%d/buddyicons/%s.jpg", farm, server, nsid);
        } else {
            return "https://www.flickr.com/images/buddyicon.gif";
        }
    }

    public static String provideLink(int farm, int server, String id, String secret) {
        return String.format(Locale.ENGLISH, "https://farm%d.staticflickr.com/%d/%s_%s.jpg", farm, server, id, secret);
    }
}
