package com.example.instagramapihomework2;

        import java.io.*;
        import java.sql.SQLException;
        import java.util.stream.Collectors;

        import OtherUtils.Utils;
        import com.google.gson.Gson;
        import com.google.gson.JsonObject;
        import jakarta.servlet.http.*;
        import jakarta.servlet.annotation.*;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private Gson gson;

    public void init() {
        gson = new Gson();
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        String username;
        String password;
        try{
            String requestData = request.getReader().lines().collect(Collectors.joining());
            JsonObject convertedObject = new Gson().fromJson(requestData, JsonObject.class);

            username = convertedObject.get("username").getAsString();
            password = convertedObject.get("password").getAsString();
        }catch(Exception e){
            Utils.sendErrorMessage(response, "An error occurred when parsing your credentials", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            if(DAO.accountCredentialsExist(username, password)){
                HttpSession session=request.getSession();
                session.setAttribute("username",username);
            }else{
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void destroy() {
    }
}
