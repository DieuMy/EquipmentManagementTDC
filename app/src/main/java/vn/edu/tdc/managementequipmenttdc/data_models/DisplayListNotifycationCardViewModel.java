package vn.edu.tdc.managementequipmenttdc.data_models;

public class DisplayListNotifycationCardViewModel {
    private String notifycationCotent;
    private String nodtifycationDate;

    public DisplayListNotifycationCardViewModel(String notifycationCotent, String nodtifycationDate) {
        this.notifycationCotent = notifycationCotent;
        this.nodtifycationDate = nodtifycationDate;
    }

    public String getNotifycationCotent() {
        return notifycationCotent;
    }

    public void setNotifycationCotent(String notifycationCotent) {
        this.notifycationCotent = notifycationCotent;
    }

    public String getNodtifycationDate() {
        return nodtifycationDate;
    }

    public void setNodtifycationDate(String nodtifycationDate) {
        this.nodtifycationDate = nodtifycationDate;
    }
}
