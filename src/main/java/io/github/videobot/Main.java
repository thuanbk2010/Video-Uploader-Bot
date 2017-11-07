package io.github.videobot;

import com.google.common.util.concurrent.FutureCallback;
import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static String DISCORD_API_KEY;

    public static void main(String[] argsArray) {
        ArrayList<String> args = new ArrayList<>(Arrays.asList(argsArray));

        try {
            File f = new File("discord_token.txt");
            if (!f.exists()) throw new IllegalStateException("File discord_token.txt is missing!");
            BufferedReader bf = new BufferedReader(new FileReader(f));
            DISCORD_API_KEY = bf.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

        DiscordAPI api = Javacord.getApi(DISCORD_API_KEY, true); // true means it's a bot

        api.connect(new FutureCallback<DiscordAPI>() {
            @Override
            public void onSuccess(final DiscordAPI api) {
                api.registerListener(new MyMessageListener());
            }

            @Override
            public void onFailure(Throwable t) {
                // login failed
                t.printStackTrace();
            }
        });
    }

}
