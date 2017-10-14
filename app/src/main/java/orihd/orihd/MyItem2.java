package orihd.orihd;

/**
 * Created by Matthew on 8/22/2017.
 */

public class MyItem2 {
    // Declare MyItem2 Variables
    public String nam;
    public String loc;
    public String aqi;
    public String particulates;
    public String batterylevel;
    public String harmfulgas;
    public String timestamp;

    public MyItem2(String username, String userloc, String useraqi, String userparticulates,
                   String userharmfulgas, String userbatterylevel, String usertimestamp ){

        nam= username;
        loc = userloc;
        aqi= useraqi;
        particulates = userparticulates;
        batterylevel=userbatterylevel;
        harmfulgas = userharmfulgas;
        timestamp=usertimestamp;
    }

    public String getname(){
        return nam;
    }
    public String getloc(){
        return loc;
    }
    public String getaqi(){
        return aqi;
    }
    public String getparticulates(){
        return particulates;
    }
    public String getbatterylevel(){
        return batterylevel;
    }
    public String getharmfulgas(){
        return harmfulgas;
    }
    public String getTimestamp(){
        return timestamp;
    }


}
