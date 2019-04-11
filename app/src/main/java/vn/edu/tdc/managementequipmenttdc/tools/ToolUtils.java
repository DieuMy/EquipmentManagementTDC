package vn.edu.tdc.managementequipmenttdc.tools;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ToolUtils {

    private Timestamp getCurrentTime() {
        Date now = new Date();
        return new Timestamp(now.getTime());
    }

    private String convertTimestampToString(Timestamp timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return dateFormat.format(timestamp);
    }

    public String getCurrentTimeString() {
        return this.convertTimestampToString(this.getCurrentTime());
    }

    public String linkUserImageDefault() {
        return "https://firebasestorage.googleapis.com/v0/b/dbfb-tdc-social-2019.appspot.com/o/user-default.png?alt=media&token=0bf526f2-485d-49d2-9954-76106da5ee7b";
    }
}
