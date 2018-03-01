package funny.mybatis.demo.type;

/**
 * @Author:hanchengke
 * @Description:
 * @Date:Created in 16:52 2018/2/27
 */
public enum Enabled {
    disabled(0),//禁用
    enabled(1);//启用
    private final int value;

    private Enabled(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
}
