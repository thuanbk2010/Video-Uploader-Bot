package io.github.videobot;

import com.google.common.util.concurrent.FutureCallback;
import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.entities.message.MessageAttachment;
import de.btobastian.javacord.listener.message.MessageCreateListener;
import de.btobastian.javacord.listener.message.MessageDeleteListener;
import de.btobastian.javacord.listener.message.MessageEditListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

public class MyMessageListener implements MessageCreateListener, MessageEditListener, MessageDeleteListener {

    @Override
    public void onMessageCreate(DiscordAPI api, final Message message) {
        System.out.println(message.getAuthor().getName() + ": " + message.getContent());

        ArrayList<MessageAttachment> attachments = new ArrayList<>(message.getAttachments());
        for (MessageAttachment attachment : attachments) {
            final String name = attachment.getFileName();
            if (name.matches(".*.(mp4|avi)")) {
                final URL url = attachment.getUrl();

                message.getChannelReceiver().sendMessage("Processing file: " + name, new FutureCallback<Message>() {
                    @Override
                    public void onSuccess(Message fileProcessMessage) {
                        System.out.println("Success!!");
                        message.delete();
                        try {
                            File tmp = File.createTempFile("botdwnld", name);
                            System.out.println("tmp file loc: "+tmp.getAbsolutePath());
                            tmp.deleteOnExit();

                            URLConnection urlConnection = url.openConnection();
                            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                            urlConnection.connect();
                            if (urlConnection.getInputStream().available() > 10000000) {
                                fileProcessMessage.edit("File is too big max file size = 10 MB");
                                throw new Exception("ignore");
                            }
                            ReadableByteChannel rbc = Channels.newChannel(urlConnection.getInputStream());
                            FileOutputStream fos = new FileOutputStream(tmp);
                            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

                            Process p = Runtime.getRuntime().exec("python video_upload.py --file \""+tmp.getAbsolutePath()+"\" --title \""+name+"\"");
                            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                            String ytUrl = br.readLine();
                            fileProcessMessage.edit(ytUrl);
                        } catch (Exception e) {
                            if (e.getMessage() == "ignore") {} else {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        }
    }

    @Override
    public void onMessageDelete(DiscordAPI api, Message message) {
        System.out.println("Message from " +  message.getAuthor().getName() + " was deleted!");
        if (message.getAuthor() == api.getYourself()) {} // TODO: remove the video from youtube in this case
    }

    @Override
    public void onMessageEdit(DiscordAPI api, Message message, String oldContent) {
        System.out.println("Message from " + message.getAuthor().getName() + " was edited!");
    }

}