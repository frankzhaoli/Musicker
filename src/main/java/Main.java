import static java.lang.System.out;
import GUI.GUI;
import Reddit.KpopDates;
import Reddit.KpopReleases;
import Reddit.RedditAPICredentials;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.IOException;
import java.util.*;

//user stories
//gui
//displays info OR allows you to download info in html format (or maybe both)
//from reddit

//gui ideas
//dropdown menu for month and year, set to kpop object

public class Main {
    public static <A> void main(String[] args)
    {
        Set<A> list=new HashSet<A>();
        //gui
        new GUI();

        //reddit stuff
        KpopDates dates=new KpopDates("january", "2022");
        RedditAPICredentials credentials=new RedditAPICredentials("", "");
        KpopReleases releases=new KpopReleases(dates, credentials);

        try
        {
            //get access token, get releases json, parse json
            String accessToken=releases.getAccessToken();
            String jsonString=releases.getReleases(accessToken);
            releases.parseJsonandWriteToFile(jsonString);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
