package com.example.ngertiit;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.example.ngertiit.Adapter.DictionaryAdapter;
import com.example.ngertiit.Adapter.LifeHackAdapterMenu;
import com.example.ngertiit.Adapter.SolutionAdapterMenu;
import com.example.ngertiit.Data.API.APIClient;
import com.example.ngertiit.Data.API.RestService;
import com.example.ngertiit.Data.JSON.DataCarousels;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.squareup.picasso.Cache;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentHome extends Fragment {

    @BindView(R.id.carouselView)
    CarouselView carouselView;
    @BindView(R.id.rv_solution)
    RecyclerView rv_solution;
    @BindView(R.id.rv_lifehack)
    RecyclerView rv_lifehack;
    @BindView(R.id.rv_dictionary)
    RecyclerView rv_dictionary;
    @BindView(R.id.tv_all_solution)
    TextView tvAllSolution;
    @BindView(R.id.tv_all_lifehack)
    TextView tvAllLifehack;
    @BindView(R.id.tv_all_dictionary)
    TextView tvAllDiction;
    @BindView(R.id.scroller)
    NestedScrollView scroller;
//    int[] sampleImages = {R.drawable.carousel_1, R.drawable.carousel_2, R.drawable.carousel_3};


    // Array list for recycler view data source
    ArrayList<String> source;
    ArrayList<String> kamuz;

    RecyclerView.LayoutManager SolutionRecyclerViewLayoutManager;
    RecyclerView.LayoutManager LifeHackRecyclerViewLayoutManager;
    RecyclerView.LayoutManager KamusRecyclerViewLayoutManager;

    // adapter class object
    SolutionAdapterMenu solutionAdapterMenu;
    LifeHackAdapterMenu lifeHackAdapterMenu;
    DictionaryAdapter dictionaryAdapter;

    // Linear Layout Manager
    LinearLayoutManager SolutionHorizontalLayout;
    LinearLayoutManager LifeHackHorizontalLayout;
    LinearLayoutManager KamusHorizontalLayout;

    Context context;


    public FragmentHome() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        final View toolbar = getActivity().findViewById(R.id.toolbar);

        /*toolbar.setVisibility(View.GONE);
        scroller.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > oldScrollY) {
                    toolbar.setVisibility(View.VISIBLE);
                }
                if (scrollY == 0) {
                    toolbar.setVisibility(View.GONE);
                }
            }
        });*/

      /*  carouselView.setPageCount(3);

        carouselView.setImageListener(imageListeners);*/

        showImageCarousel();
        
        
        initView();
        initEvent();
        return view;
    }

    List<String> imageUrls = new ArrayList<>();


    private void showImageCarousel() {
        RestService apiService = APIClient.getAPI().create(RestService.class);
        Call<List<DataCarousels>> call = apiService.getDataCarousels();

        call.enqueue(new Callback<List<DataCarousels>>() {
            @Override
            public void onResponse(Call<List<DataCarousels>> call, Response<List<DataCarousels>> dataCarousel) {
//                DataCarousels[] dataCarousels = new DataCarousels[dataCarousel.body().size()];
//                dataCarousels = dataCarousel.toArray

                    for (int i = 0; i < dataCarousel.body().size(); i++){
                        String bannerUrl = dataCarousel.body().get(i).getImage();
                        imageUrls.add(bannerUrl);
                        System.out.println("banner = " + bannerUrl);
                    }

                    ImageListener imageListener = (position, imageView) -> {
                        String link1 = dataCarousel.body().get(0).getLink();

                        String link2 = dataCarousel.body().get(2).getLink();

                        String link3 = dataCarousel.body().get(1).getLink();

                        /*SvgLoader.pluck()
                                .with(getActivity())
                                .setPlaceHolder(R.drawable.loading, R.drawable.loading)
                                .load(imageUrls.get(position), imageView);*/


                        GlideToVectorYou.justLoadImage(
                                (Activity) context,
                                Uri.parse(imageUrls.get(position)),
                                imageView
                        );

                        if (position == 0) {
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Uri uri = Uri.parse(link1);
                                    Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                                    likeIng.setPackage("com.instagram.android");

                                    try {
                                        startActivity(likeIng);
                                    } catch (ActivityNotFoundException e) {
                                        startActivity(new Intent(Intent.ACTION_VIEW,
                                                Uri.parse("http://instagram.com/famfir_/")));
                                    }
                                }
                            });
                        } else if (position == 2) {
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(context, SolutionAct.class);
                                    startActivity(i);
                                }
                            });

                        } else if (position ==1 ) {
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent sendIntent = new Intent();
                                    sendIntent.setAction(Intent.ACTION_SEND);
                                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Ayo jadi pinter komputer biar ga bego2 banget, download sekarang di " + link3);
                                    sendIntent.setType("text/plain");
                                    startActivity(sendIntent);
                                }
                            });
                        }

                    };
                    carouselView.setImageListener(imageListener);
                    carouselView.setPageCount(dataCarousel.body().size());
            }

            @Override
            public void onFailure(Call<List<DataCarousels>> call, Throwable t) {

            }
        });
    }

    private void initEvent() {

        Animation animScalein = AnimationUtils.loadAnimation(context, R.anim.anim_scale_in);


        tvAllSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CategorySolution.class);
                startActivity(i);
            }
        });

        tvAllLifehack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CategoryLifehack.class);
                startActivity(i);
            }
        });

        tvAllDiction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                v.startAnimation(animScalein);
                Intent i = new Intent(context, KamusAct.class);
                startActivity(i);
            }
        });
    }

    private void addNotification() {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("New Life-Hack Updates!")
                .setContentText("PSM Swara Darmagita diundang sebagai Pengisi Acara dalam acara Wisuda Gelar Profesional Asuransi XXVIII Ahli dan Ajun Ahli Asuransi Indonesia, Asosiasi Ahli Manajemen Asuransi Indonesia (AAMAI) yang bertempat di Birawa Assembly hall, Hotel Bidakara Jakarta");

        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0 , notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        manager.notify(0,mBuilder.build());
    }


    private void initView() {
        SolutionRecyclerViewLayoutManager = new LinearLayoutManager(context);
        LifeHackRecyclerViewLayoutManager = new LinearLayoutManager(context);
        KamusRecyclerViewLayoutManager = new LinearLayoutManager(context);

        // Set LayoutManager on Recycler View
        rv_solution.setLayoutManager(SolutionRecyclerViewLayoutManager);

        rv_lifehack.setLayoutManager(LifeHackRecyclerViewLayoutManager);
        rv_dictionary.setLayoutManager(KamusRecyclerViewLayoutManager);

        // Adding items to RecyclerView.
        AddItemsToRecyclerViewArrayList();
        Kamus();

        // calling constructor of adapter
        // with source list as a parameter
        solutionAdapterMenu = new SolutionAdapterMenu(source);
        lifeHackAdapterMenu = new LifeHackAdapterMenu(source);
        dictionaryAdapter = new DictionaryAdapter(kamuz);

        // Set Horizontal Layout Manager
        // for Recycler view
        SolutionHorizontalLayout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        LifeHackHorizontalLayout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        KamusHorizontalLayout = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        rv_solution.setLayoutManager(SolutionHorizontalLayout);
        rv_lifehack.setLayoutManager(LifeHackHorizontalLayout);
        rv_dictionary.setLayoutManager(KamusHorizontalLayout);

        // Set adapter on recycler view
        rv_solution.setAdapter(solutionAdapterMenu);
        rv_lifehack.setAdapter(lifeHackAdapterMenu);
        rv_dictionary.setAdapter(dictionaryAdapter);
    }

    public void AddItemsToRecyclerViewArrayList()
    {
        // Adding items to ArrayList
        source = new ArrayList<>();
        source.add("Cara install Ms. Office pada Windows (..terusan judul)");
        source.add("Google Chrome terasa lemot? Begini caranya (..terusan judul)");
        source.add("Wifi mati. Ga bisa konek ke internet. (..terusan judul)");
        source.add("Tombol FN gak bisa berfungsi. (..terusan judul)");
        source.add("Mau beli laptop tapi bingung seperti apa? (..terusan judul)");
    }

    public void Kamus(){
        kamuz = new ArrayList<>();
        kamuz.add("Lorem ipsum dolor sit amet lorem ipsum dolor sit amet Lorem ipsum dolor sit amet lorem ipsum dolor sit amet");
        kamuz.add("Google Chrome terasa lemot? Begini caranya (..terusan judul)");
        kamuz.add("Wifi mati. Ga bisa konek ke internet. (..terusan judul)");
    }



    /*ImageListener imageListeners = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);




        }
    };*/

    private void sharePlayStore() {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Ayo jadi pinter komputer biar ga bego2 banget, download sekarang di pornhub ");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

    }
}
