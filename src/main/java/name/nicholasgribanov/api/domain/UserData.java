package name.nicholasgribanov.api.domain;

import java.util.ArrayList;
import java.util.List;

public class UserData {
    private List<User> data = new ArrayList<>();

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}
