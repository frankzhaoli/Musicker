package Reddit;

public class KpopDates
{
    private String month;
    private String year;

    public KpopDates(String month, String year)
    {
        this.month=month;
        this.year=year;
    }

    public void setMonth(String newMonth)
    {
        this.month=newMonth;
    }

    public void setYear(String newYear)
    {
        this.year=newYear;
    }

    public String getMonth()
    {
        return month;
    }

    public String getYear()
    {
        return year;
    }
}
