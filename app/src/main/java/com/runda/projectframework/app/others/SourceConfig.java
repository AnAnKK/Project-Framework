package com.runda.projectframework.app.others;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Pair;

import com.runda.projectframework.app.repository.bean.test.VideoInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vans Z on 2018/9/26.
 */

public class SourceConfig {

    /**
     * 缩略图
     * @return
     */
    public static List<String> getThumbSourceGroup() {
        List<String> thumbnailImageList = new ArrayList<>();
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1486263782969.png@233w_160h_20q");
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1485055822651.png@233w_160h_20q");
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1486194909983.png@233w_160h_20q");
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1486194996586.png@233w_160h_20q");
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1486195059137.png@233w_160h_20q");
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1486173497249.png@233w_160h_20q");
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1486173526402.png@233w_160h_20q");
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1486173639603.png@233w_160h_20q");
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1486172566083.png@233w_160h_20q");
        return thumbnailImageList;
    }

    /**
     * 原图
     * @return
     */
    public static List<String> getOriginalSourceGroup() {
        List<String> sourceImageList = new ArrayList<>();
        sourceImageList.add("http://static.fdc.com.cn/avatar/sns/1486263782969.png");
        sourceImageList.add("http://static.fdc.com.cn/avatar/sns/1485055822651.png");
        sourceImageList.add("http://static.fdc.com.cn/avatar/sns/1486194909983.png");
        sourceImageList.add("http://static.fdc.com.cn/avatar/sns/1486194996586.png");
        sourceImageList.add("http://static.fdc.com.cn/avatar/sns/1486195059137.png");
        sourceImageList.add("http://static.fdc.com.cn/avatar/sns/1486173497249.png");
        sourceImageList.add("http://static.fdc.com.cn/avatar/sns/1486173526402.png");
        sourceImageList.add("http://static.fdc.com.cn/avatar/sns/1486173639603.png");
        sourceImageList.add("http://static.fdc.com.cn/avatar/sns/1486172566083.png");
        return sourceImageList;
    }

    /**
     * 图片+gif+视频
     * @return
     */
    public static List<String> getMixingSourceGroup() {
        List<String> sourceList = new ArrayList<>();
        sourceList.add("http://img2.woyaogexing.com/2018/01/25/f5d815584c61d376!500x500.jpg");
        sourceList.add("http://img3.duitang.com/uploads/item/201605/13/20160513144041_Ze3a4.gif");
        sourceList.add("https://pic4.zhimg.com/80/v2-ab305465594807042787fb0dc06c423b_hd.jpg");
        sourceList.add("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4");
        sourceList.add("http://static.fdc.com.cn/avatar/sns/1486194996586.png");
        sourceList.add("http://static.fdc.com.cn/avatar/sns/1486195059137.png");
        sourceList.add("http://static.fdc.com.cn/avatar/sns/1486173497249.png");
        sourceList.add("http://static.fdc.com.cn/avatar/sns/1486173639603.png");
        sourceList.add("http://jzvd.nathen.cn/c494b340ff704015bb6682ffde3cd302/64929c369124497593205a4190d7d128-5287d2089db37e62345123a1be272f8b.mp4");
        return sourceList;
    }

    public static List<String> getSinglePicture() {
        List<String> sourceList = new ArrayList<>();
        sourceList.add("http://img2.woyaogexing.com/2018/01/25/f5d815584c61d376!500x500.jpg");
        return sourceList;
    }

    public static List<VideoInfo> getVideoList() {
        List<VideoInfo> videoList = new ArrayList<>();
        videoList.add(new VideoInfo("预告片1",
                "https://cms-bucket.nosdn.127.net/eb411c2810f04ffa8aaafc42052b233820180418095416.jpeg",
                "http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4"));

        videoList.add(new VideoInfo("预告片2",
                "https://cms-bucket.nosdn.127.net/cb37178af1584c1588f4a01e5ecf323120180418133127.jpeg",
                "http://vfx.mtime.cn/Video/2019/03/21/mp4/190321153853126488.mp4"));

        videoList.add(new VideoInfo("预告片3",
                "https://cms-bucket.nosdn.127.net/eb411c2810f04ffa8aaafc42052b233820180418095416.jpeg",
                "http://vfx.mtime.cn/Video/2019/03/19/mp4/190319222227698228.mp4"));

        videoList.add(new VideoInfo("预告片4",
                "https://cms-bucket.nosdn.127.net/cb37178af1584c1588f4a01e5ecf323120180418133127.jpeg",
                "http://vfx.mtime.cn/Video/2019/03/19/mp4/190319212559089721.mp4"));

        videoList.add(new VideoInfo("预告片5",
                "https://cms-bucket.nosdn.127.net/eb411c2810f04ffa8aaafc42052b233820180418095416.jpeg",
                "http://vfx.mtime.cn/Video/2019/03/18/mp4/190318231014076505.mp4"));

        videoList.add(new VideoInfo("预告片6",
                "https://cms-bucket.nosdn.127.net/cb37178af1584c1588f4a01e5ecf323120180418133127.jpeg",
                "http://vfx.mtime.cn/Video/2019/03/18/mp4/190318214226685784.mp4"));

        videoList.add(new VideoInfo("预告片7",
                "https://cms-bucket.nosdn.127.net/eb411c2810f04ffa8aaafc42052b233820180418095416.jpeg",
                "http://vfx.mtime.cn/Video/2019/03/19/mp4/190319104618910544.mp4"));

        videoList.add(new VideoInfo("预告片8",
                "https://cms-bucket.nosdn.127.net/cb37178af1584c1588f4a01e5ecf323120180418133127.jpeg",
                "http://vfx.mtime.cn/Video/2019/03/19/mp4/190319125415785691.mp4"));

        videoList.add(new VideoInfo("预告片9",
                "https://cms-bucket.nosdn.127.net/eb411c2810f04ffa8aaafc42052b233820180418095416.jpeg",
                "http://vfx.mtime.cn/Video/2019/03/17/mp4/190317150237409904.mp4"));

        videoList.add(new VideoInfo("预告片10",
                "https://cms-bucket.nosdn.127.net/cb37178af1584c1588f4a01e5ecf323120180418133127.jpeg",
                "http://vfx.mtime.cn/Video/2019/03/14/mp4/190314223540373995.mp4"));

        videoList.add(new VideoInfo("预告片11",
                "https://cms-bucket.nosdn.127.net/eb411c2810f04ffa8aaafc42052b233820180418095416.jpeg",
                "http://vfx.mtime.cn/Video/2019/03/14/mp4/190314102306987969.mp4"));

        videoList.add(new VideoInfo("预告片12",
                "https://cms-bucket.nosdn.127.net/cb37178af1584c1588f4a01e5ecf323120180418133127.jpeg",
                "http://vfx.mtime.cn/Video/2019/03/13/mp4/190313094901111138.mp4"));

        videoList.add(new VideoInfo("预告片13",
                "https://cms-bucket.nosdn.127.net/eb411c2810f04ffa8aaafc42052b233820180418095416.jpeg",
                "http://vfx.mtime.cn/Video/2019/03/12/mp4/190312143927981075.mp4"));

        videoList.add(new VideoInfo("预告片14",
                "https://cms-bucket.nosdn.127.net/cb37178af1584c1588f4a01e5ecf323120180418133127.jpeg",
                "http://vfx.mtime.cn/Video/2019/03/12/mp4/190312083533415853.mp4"));

        return videoList;
    }

    /**
     * <item>Android</item>
     *         <item>Javascript</item>
     *         <item>React Native</item>
     *         <item>Kotlin</item>
     *         <item>C++</item>
     *         <item>C#</item>
     *         <item>Object-C</item>
     * @return
     */
    public static List<String> getFlexboxString(){
        List<String>list = new ArrayList<>();
        list.add("Android");
        list.add("Javascript");
        list.add("React Native");
        list.add("Kotlin");
        list.add("C++");
        list.add("C");
        list.add("Object-C");
        return list;
    }

}
