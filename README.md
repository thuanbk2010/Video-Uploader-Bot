# Video-Uploader-Bot
A bot that uploads videos from discord to youtube so that you can easly view them!

# Dependencies
Python 2.5 or above
Java 8 or above
this python lib `pip install --upgrade google-api-python-client`

# How to self-host
1. Create a YT channel
2. While being logged in to that google account go to here: https://console.developers.google.com/apis/api/youtube.googleapis.com
3. Create a new project
4. Enable youtube data api
5. Create a file named client_secrets.json with this inside (fill in ur id and secret)
```json
{
  "web": {
    "client_id": "[UR ID]",
    "client_secret": "[UR SECRET]",
    "redirect_uris": [""],
    "auth_uri": "https://accounts.google.com/o/oauth2/auth",
    "token_uri": "https://accounts.google.com/o/oauth2/token"
  }
}
```
6. launch this command to auth to ur yt channel `python video_upload.py --file="anyfile.mp4"` (u have to specify some file to auth)
7. go to this page: https://discordapp.com/developers/applications/me/
8. Click create new app
9. Fill in the form
10. click add a bot user
11. Click reveal the Token link
12. edit src\main\java\io\github\videobot\Main.java and change the API_KEY to the token of your bot user
13. launch the bot on ur machine
14. Use this link to the bot to ur server: https://discordapp.com/oauth2/authorize?client_id=[UR CLIENT ID HERE]&scope=bot&permissions=93184
NOTE: Dont change the permissions thing at the end it will screw up the bot!

# How to add the hosted by me version to ur server?
Click this link: https://discordapp.com/oauth2/authorize?client_id=377203131391410178&scope=bot&permissions=93184
NOTE: Dont change the permissions thing at the end it will screw up the bot! (And dont change the permissions of a role that it gets by default it needs them to function properly!)
