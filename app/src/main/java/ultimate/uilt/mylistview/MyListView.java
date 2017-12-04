package ultimate.uilt.mylistview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by user on 2017/3/18.
 * 重写ListView以实现高度自适应
 */

public class MyListView extends ListView {
    public MyListView(Context context){
        super(context);
    }
    public MyListView(Context context, AttributeSet attrs){
        super(context,attrs);
    }
    public MyListView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
