package ru.otus.java_2017_04.golovnin.hw12.TestRigMonitor;


import java.util.HashMap;
import java.util.Map;

public class AuthenticationData implements IAuthenticationData {
    private static final Map<String, String> admins_auth_data;

    static {
        admins_auth_data = new HashMap<>();
        admins_auth_data.put("Peter", "qwert");
        admins_auth_data.put("Arthur", "iop");
    }

    @Override
    public boolean checkValid(String login, String pass) {
        boolean result = false;
        if(login != null && pass != null){
            String truePass = admins_auth_data.get(login);
            if(truePass != null){
                result = truePass.equals(pass);
            }
        }
        return result;
    }
}
