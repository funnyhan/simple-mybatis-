package funny.mybatis.demo.plugin;

import org.apache.ibatis.session.RowBounds;

/**
 * @Author:hanchengke
 * @Description:
 * @Date:Created in 17:02 2018/2/28
 */
public class PageRowBounds extends RowBounds {
    private long total;
    public PageRowBounds(){
        super();
    }
    public PageRowBounds(int offset,int limit){
        super(offset,limit);
    }
    public long getTotal(){
        return total;
    }
    public void setTotal(long total){
        this.total = total;
    }
}
