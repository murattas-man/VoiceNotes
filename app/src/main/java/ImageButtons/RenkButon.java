package ImageButtons;

import android.widget.ImageView;

import com.voicenote.voicenotes.R;


public class RenkButon {


    public static ImageView img1,img2,img3,img4,img5,img6,img7,img8,img9,img10,img11,img12;
    public static   int[] btnRenkler={
            R.id.img1,
            R.id.img2,
            R.id.img3,
            R.id.img4,
            R.id.img5,
            R.id.img6,
            R.id.img7,
            R.id.img8,
            R.id.img9,
            R.id.img10,
            R.id.img11,
            R.id.img12
    };

    public static  ImageView[] imageButonlar(){
        ImageView[] imgBtn=new ImageView[12];
        imgBtn[0]=img1;
        imgBtn[1]=img2;
        imgBtn[2]=img3;
        imgBtn[3]=img4;
        imgBtn[4]=img5;
        imgBtn[5]=img6;
        imgBtn[6]=img7;
        imgBtn[7]=img8;
        imgBtn[8]=img9;
        imgBtn[9]=img10;
        imgBtn[10]=img11;
        imgBtn[11]=img12;
        return imgBtn;
    }
}
