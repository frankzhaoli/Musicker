package Reddit;

import static java.lang.System.out;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class KpopReleases {
    private final KpopDates date;
    private final RedditAPICredentials credentials;

    String userAgent="discord:version 1.0 (by u/xShiki69)";

    public KpopReleases(KpopDates date, RedditAPICredentials credentials)
    {
        this.date=date;
        this.credentials=credentials;
    }

    public String getAccessToken() throws IOException
    {
        //setting up strings
        String accessTokenURL="https://www.reddit.com/api/v1/access_token"+"?grant_type=client_credentials";
        String authString=credentials.getClientID()+":"+credentials.getClientSecret();
        String encodedAuthString=Base64.getEncoder().encodeToString(authString.getBytes(StandardCharsets.UTF_8));

        //first web call to reddit
        URL redditURL=new URL(accessTokenURL);
        HttpURLConnection redditURLConnection=(HttpURLConnection) redditURL.openConnection();
        redditURLConnection.setRequestMethod("POST");
        redditURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        redditURLConnection.setRequestProperty("Authorization", "Basic "+encodedAuthString);
        redditURLConnection.setRequestProperty("grant_type", "client_credentials");
        redditURLConnection.setRequestProperty("User-Agent", userAgent);

        //get input and read into string
        BufferedReader reader=new BufferedReader(new InputStreamReader(redditURLConnection.getInputStream()));
        String input;
        String accessToken = null;

        while((input=reader.readLine())!=null)
        {
            accessToken=input;
        }
        reader.close();

        //extract access token
        JsonObject jsonObject=new Gson().fromJson(accessToken, JsonObject.class);
        accessToken=jsonObject.get("access_token").getAsString();

        return accessToken;
    }

    public String getReleases(String accessToken) throws IOException
    {
        String groupURL="https://oauth.reddit.com/r/kpop/wiki/upcoming-releases/"+date.getYear()+"/"+date.getMonth()+".json";

        URL url=new URL(groupURL);
        HttpURLConnection redditPostURLConnection=(HttpURLConnection) url.openConnection();
        redditPostURLConnection.setRequestMethod("GET");
        redditPostURLConnection.setRequestProperty("Authorization", "Bearer "+accessToken);
        redditPostURLConnection.setRequestProperty("User-Agent", userAgent);
        redditPostURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        //get input and read into string
        BufferedReader reader=new BufferedReader(new InputStreamReader(redditPostURLConnection.getInputStream(), StandardCharsets.UTF_8));
        String input;
        String redditJsonString=null;

        while((input=reader.readLine())!=null)
        {
            redditJsonString=input;
        }
        reader.close();
        return redditJsonString;
    }

    public void parseJsonandWriteToFile(String jsonString)
    {
        JsonObject obj=new Gson().fromJson(jsonString, JsonObject.class);
        JsonObject data=obj.getAsJsonObject("data");

        String contentmd=data.get("content_md").getAsString().replaceAll("\\r|\\n", "");
        String[] contentmdArray=contentmd.split("\\|");
        List<String> contentmdList= Arrays.asList(contentmdArray);

        try
        {
            PrintWriter writer=new PrintWriter(new File("KpopReleases_"+date.getMonth()+"_"+date.getYear()+".html"), StandardCharsets.UTF_8);
            writer.println("--KPOP RELEASES IN "+date.getMonth().toUpperCase()+" "+date.getYear()+"--\n\n");
            //writer.println(contentmdList.get(0)+"\n");
            writer.println("Order:\n<br>Artist\n<br>Album Title\n<br>Album Type\n<br>Title Track\n<br>Streaming Link\n<br><br>");

            for(int i=0; i<contentmdList.size(); i++)
            {
                String current=contentmdList.get(i);

                //spotify only items, maybe change in future
                if(current.contains("spotify.com"))
                {
                    String artist=contentmdList.get(i-4);
                    String albumTitle=contentmdList.get(i-3);
                    String albumType=contentmdList.get(i-2);
                    String titleTrack=contentmdList.get(i-1);
                    String streamingLink=contentmdList.get(i);

                    writer.println("<b>"+artist+"</b><br>");
                    writer.println(albumTitle+"<br>");
                    writer.println(albumType+"<br>");
                    writer.println("<em>"+titleTrack+"</em><br>");

                    //fix link
                    String spotifyLink=streamingLink.substring(streamingLink.indexOf("(")+1, streamingLink.indexOf(")"));

                    writer.println("Spotify: <a href=\""+spotifyLink+"\" target=\"_blank\">"+"Link"+"</a>");
                    writer.println("<br><br>");
                    writer.println();
                }
            }
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
