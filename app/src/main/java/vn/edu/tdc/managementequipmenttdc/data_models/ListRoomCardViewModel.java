package vn.edu.tdc.managementequipmenttdc.data_models;

public class ListRoomCardViewModel {
    private String roomName;

    public ListRoomCardViewModel(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
