package net.bwei.shan.download;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import net.bwei.shan.download.adapter.ModelItemAdapter;
import net.bwei.shan.download.model.Data;
import net.bwei.shan.download.model.ModelItem;
import net.bwei.shan.download.model.MyData;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION_CODES.M;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    ListView listView;
    private String url = "http://ic.snssdk.com/2/article/v25/stream/?category=news_car&count=20&bd_city=北京市&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455522784&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000";
    private List<ModelItem> list;
    ModelItemAdapter adapter;
    Button button;
    ImageView image;

    List<String> imageUrls;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                boolean isCheck = list.get(i).isCheck();
                list.get(i).setCheck(!isCheck);
                adapter.notifyDataSetChanged();
            }
        });
        getData();


        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadFile1(imageUrls.get(0));
            }
        });
        image = (ImageView) findViewById(R.id.image);
    }

    private void getData() {
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                MyData mydata = gson.fromJson(result, MyData.class);
                List<MyData.DataBean> databean =mydata.getData();

                imageUrls = new ArrayList<String>();

                for (MyData.DataBean data:databean){

                    for (MyData.DataBean.ImageListBean image:data.getImage_list()){
                        if (image!=null){
                            String url = image.getUrl();
                            imageUrls.add(url);
                            Log.d(TAG,url);
                        }
                    }




                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(MainActivity.this, ex.toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });


    }

    //xutils下载单张图片

    private void downloadFile1(String url) {
        RequestParams params = new RequestParams(url);
        int pos = url.lastIndexOf("/");
        String name = url.substring(pos + 1, url.length());

//        File file = new File(Environment.getExternalStorageDirectory().getPath()+"/download");
//        if (!file.exists()){
//            file.mkdirs();
//        }
//

        params.setSaveFilePath(Environment.getExternalStorageDirectory() + "/" + name+".jpg");
        x.http().get(params, new Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
                Bitmap bitmap = BitmapFactory.decodeFile(result.getPath());
                image.setImageBitmap(bitmap);
                index++;
                if (index<imageUrls.size()){
                    downloadFile1(imageUrls.get(index));
                }




            }

            @Override
            public void onFinished() {



            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d(TAG,"ex onerror "+ex);

            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d(TAG,"cex ");

            }
        });

    }



}
