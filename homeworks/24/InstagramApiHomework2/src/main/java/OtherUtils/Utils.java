package OtherUtils;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class Utils {
    public static void sendErrorMessage(HttpServletResponse response, String message, int responseCode){
        String messageInJson = new Gson().toJson(new Message(message));
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(messageInJson);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
