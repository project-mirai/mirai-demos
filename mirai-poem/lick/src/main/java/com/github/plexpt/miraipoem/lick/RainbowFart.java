package com.github.plexpt.miraipoem.lick;

import net.dreamlu.mica.http.DomMapper;
import net.dreamlu.mica.http.HttpRequest;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import lombok.experimental.UtilityClass;

/**
 * @author pt
 * @email plexpt@gmail.com
 * @date 2020-04-14 16:02
 */

@UtilityClass
public class RainbowFart {

    /**
     * 获取彩虹屁
     *
     * @return
     */
    public static String getChp() {
        String url = "https://chp.shadiao.app/api.php";


        return HttpRequest.get(url)
                .execute()
                .asString();
    }


    /**
     * 获取对线用语
     *
     * @return
     */
    public static String getDuixian() {
        return getDuixian(2);

    }

    /**
     * 获取对线用语
     * * 1: 火力全开 2：🙂口吐莲花
     *
     * @return
     */
    public static String getDuixian(int level) {
        String url = "https://nmsl.shadiao.app/api.php";

        if (level == 2) {
            url = url + "?level=min";
        }
        return HttpRequest.get(url)
                .execute()
                .asString();
    }

    /**
     * 获取 马丁路德骂人生成器，收集了他作品里所有的脏话，连出处都有…
     *
     * @return
     */
    public static String getMartinLuther() {

        String s = HttpRequest.get("http://ergofabulous.org/luther/")
                .execute()
                .asString();

        Document document = DomMapper.readDocument(s);

        Element element = document.selectFirst("body > main > p.larger");

        String trim = element.toString().trim();

        String result = Util.cleanHtmlTag(trim);

        return result;
    }


}
