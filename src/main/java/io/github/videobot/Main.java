package io.github.videobot;

import com.google.common.util.concurrent.FutureCallback;
import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static final String DISCORD_API_KEY = "UR_TOKEN_HERE";

    public static void main(String[] argsArray) {
        ArrayList<String> args = new ArrayList<>(Arrays.asList(argsArray));

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
