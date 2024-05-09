import java.util.List;

public class Player {
    private String name;//姓名
    private int level;//牌型
    private List<Integer> kick;//踢脚牌，最多高牌和同花比5次大小，顺子比1次,葫芦比2次
    private List<Integer> levelkick;//踢脚牌，应该分牌型大小和踢脚大小
    private List<Brand> ubrands;//牌库（7选5）

    public List<Integer> getLevelkick() {
        return levelkick;
    }

    public void setLevelkick(List<Integer> levelkick) {
        this.levelkick = levelkick;
    }



    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Integer> getKick() {
        return kick;
    }

    public void setKick(List<Integer> kick) {
        this.kick = kick;
    }

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
