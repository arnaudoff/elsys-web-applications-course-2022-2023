package com.bulgarland.politics;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Parliament {

    @RequestMapping("/")
    public String welcome() {
        String backgroundCSS = "style=\"background-image: url('https://eu2018bg.bg/upload/429/__KR6395.JPG');" +
                "background-size: 100% 100%;\"";
        String render = "<body " + backgroundCSS + " >" +
                "<h1 align=\"center\">Welcome to BULGARLAND<h1>" +
                "</body>";
        return render;
    }

    private String speech(String src, String message) {
        String backgroundCSS = "style=\"background-image: url('" + src + "');" +
                "background-size: 100% 100%;\"";
        String render = "<body " + backgroundCSS + " >" +
                "<h1 align=\"center\">" + message + " <h1>" +
                "</body>";
        return render;
    }

    @RequestMapping("/left")
    public String leftSpeech() {
        String src = "https://img.haskovo.net//images/news_images/2022/04/29/orig-51816024561939008.jpg";
        String message = "Десните откраднаха кодовете!";
        return speech(src, message);

    }

    @RequestMapping("/right")
    public String rightSpeech() {
        String src = "https://7dnibulgaria.bg/wp-content/uploads/2021/09/%D0%90%D1%81%D0%B5%D0%BD-%D0%92%D0%B0%D1%81%D0%B8%D0%BB%D0%B5%D0%B2-scaled.jpeg";
        String message = "КРИЗА";
        return speech(src, message);
    }


}
