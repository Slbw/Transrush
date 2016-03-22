package com.ifxme.transrush;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExpressPraser {

    private static ExpressPraser expressPraser;

    private ExpressPraser() {

    }

    public static ExpressPraser getInstance() {
        if (expressPraser == null) {
            expressPraser = new ExpressPraser();
        }
        return expressPraser;
    }

    /**
     * 解析
     *
     * @param url
     * @throws IOException
     */
    public List<ExpressModel> prase(String url) {
        List<ExpressModel> list = new ArrayList<ExpressModel>();
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            return list;
        }
        Elements timeElements = doc.select(".track-list").select(".track-of-time");
        Elements titleElements = doc.select(".track-list").select(".track-text");
        if (timeElements.size() == titleElements.size())//信息合法
        {
            for (int i = 0; i < timeElements.size(); i++) {
                ExpressModel model = new ExpressModel();
                String time = timeElements.get(i).ownText();
                String title = titleElements.get(i).ownText();
                model.setTime(time);
                model.setTitle(title);
                list.add(model);
            }
        }
        return list;
    }

}
