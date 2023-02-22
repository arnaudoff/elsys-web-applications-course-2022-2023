package com.instagramapi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    public static void setErrorOrSuccessMessage(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JsonObject obj = new JsonObject();
        obj.addProperty("message", message);
        response.getWriter().write(new Gson().toJson(obj));
    }

    public static boolean initRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getSession(false) == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            Utils.setErrorOrSuccessMessage(response, "Please log in.");
            return true;
        }
        return false;
    }

    public static String hash(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        return String.format("%064x", new BigInteger(1, digest));

    }

    public static JsonObject readJson(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        }
        return new Gson().fromJson(sb.toString(), JsonObject.class);
    }

    public static final String database_user = "root";
    public static final String database_pass = "rootkotka";
}
