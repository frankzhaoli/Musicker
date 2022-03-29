package Reddit;

public class RedditAPICredentials {
    private final String clientID;
    private final String clientSecret;

    public RedditAPICredentials(String clientID, String clientSecret)
    {
        this.clientID=clientID;
        this.clientSecret=clientSecret;
    }

    public String getClientID()
    {
        return clientID;
    }

    public String getClientSecret()
    {
        return clientSecret;
    }
}
