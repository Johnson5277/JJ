import java.util.List;

public class Player {
    private String name;
    private List<Brand> ubrands;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Brand> getUbrands() {
        return ubrands;
    }

    public void setUbrands(List<Brand> ubrands) {
        this.ubrands = ubrands;
    }
}
