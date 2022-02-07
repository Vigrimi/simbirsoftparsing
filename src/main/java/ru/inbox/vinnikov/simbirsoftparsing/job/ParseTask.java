package ru.inbox.vinnikov.simbirsoftparsing.job;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.*;

@Component
@Slf4j
public class ParseTask {
    Scanner scanner = new Scanner(System.in);
    private final int SECONDS_5_INT = 5_000;
    private final String urlGoogle = "https://google.com";
    private String textFmWeb;

    public void parseNewNews()
    { // https://vc.ru/   https://www.simbirsoft.com/
//        System.out.println("---/╲/\\[☉﹏☉]/\\╱\\ <-- Паук------public void parseNewNews() started");
        System.out.println("*** Введи ссылку произвольной HTML-страницы:");
        String parsingUrl = scanner.nextLine();
        parsingUrl = parsingUrl.trim();
        if (parsingUrl.startsWith("w") || !parsingUrl.contains("https://"))
            parsingUrl = "https://" + parsingUrl;
        log.info("------parsingUrl:" + parsingUrl);

        try {
            Document doc = Jsoup.connect(parsingUrl).userAgent("Chrome").timeout(SECONDS_5_INT)//SECONDS_5_INT)
                    .referrer(urlGoogle).get();
            textFmWeb = doc.toString();
        } catch (IOException e) {
            log.error(e.toString());
            parseNewNews();
        }

        textFmWeb = textFmWeb.replaceAll("\""," ");
        textFmWeb = textFmWeb.replaceAll("\n"," ").replaceAll("\r"," ")
                .replaceAll("\t"," ");
        textFmWeb = textFmWeb.replaceAll(",", " ").replaceAll("\\.", " ")
                .replaceAll("! ", " ").replaceAll("\\?", " ")
                .replaceAll(":", " ").replaceAll("\\[", " ")
                .replaceAll("\\(", " ").replaceAll("\\)"," ")
                .replaceAll("!"," ").replaceAll(";", " ")
                .replaceAll("]", " ").trim();

        String[] wordsFmWebsiteArr = textFmWeb.split(" ");
        log.info("--- splited in Array.");
        HashMap<String,Integer> wordsFmWebsiteHMap = new HashMap<>();
        log.info("--- begin to fill in HashMap<String,Integer>.");
        for (String word : wordsFmWebsiteArr) {
            word = word.trim();
            if (word.equals(null)) word = "";
//            System.out.println("-------------------word:" + word);
            if (!word.isEmpty() || !word.isBlank())
                //wordsFmWebsiteHMap.put(word, wordsFmWebsiteHMap.get(word) + 1);
                wordsFmWebsiteHMap.put(word, wordsFmWebsiteHMap.getOrDefault(word, 0) + 1);
        }
        log.info("--- finished to fill in HashMap<String,Integer>.");
        System.out.println("Статистика по количеству уникальных слов в тексте->");
        for (Map.Entry<String, Integer> entry : wordsFmWebsiteHMap.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }
}
