package com.example.ainshamsuniversity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.ainshamsuniversity.R;


public class SliderAdabter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdabter(Context context){
        this.context=context;
    }

    //Arrays
    int slideImage[]={
            R.drawable.logo_splash,
            R.drawable.message,
            R.drawable.news,
            R.drawable.security,
            R.drawable.gpa,
//            @android:color/transparent


    };
    String head[]={
            "رؤية الكلية",
            "رسالة الكلية",
            "الأخبار و الاعلامات",
            "ملاحظات خاصة",
            "المعدل الفصلي و التراكمي",


    };
    String slideDis[]={
            "تحقيق الإمتياز والريادة في التدريس والبحث العلمي في مجالات العلوم الأساسية وتطبيقاتها لخدمة إحتياجات المجتمع وتحسين الأحوال المعيشية للإنسان.",
            "تلتزم الكلية بتقديم برامج مهنية متخصصة تلبى الإحتياجات القومية والإقليمية وتمد الطلاب بالمتطلبات المعرفية والتدريب اللازم للإلتحاق بسوق العمل بكفاءة أو بالدراسات العليا بنجاح . كما تعمل الكلية بإصرار على تطوير وتحديث برامجها لتتواءم مع الإحتياجات المتغيرة للمجتمع ولأصحاب المصلحة المستفيدين من خدماتها .",
            "يتيح لك التطبيق معرفة أخبار الكلية عن طريق الموقع الرسمي للكلية مثل جدول الإمتحانات، مسابقات ، رحلات، آخر الأخبار ..إلخ.",
            "رابط الملحوظات الخاصة (الغير معلنة على موقع الكلية) ولكنها مرئية فى التطبيق" +
                    "والغرض منها هي اعلام فئة معينة من الطلاب (ربما يكون احدي البرامج)  باشياء ضرورية ولكن هذه الصفحة لم تكن ظاهرة على صفحة الكلية",
            "تستطيع من خلال التطبيق حساب التقدير الفصلي والتراكمي",



    };

    @Override
    public int getCount() {
        return head.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view= layoutInflater.inflate(R.layout.slide_layout,container,false);
        ImageView slideImageViwe = view.findViewById(R.id.slidImage);
        TextView slideHead = view.findViewById(R.id.slidHead);
        TextView slideText = view.findViewById(R.id.slideDis);

        slideImageViwe.setImageResource(slideImage[position]);
        slideHead.setText(head[position]);
        slideText.setText(slideDis[position]);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);

    }

}
