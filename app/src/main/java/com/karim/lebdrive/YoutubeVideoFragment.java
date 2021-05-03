package com.karim.lebdrive;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class YoutubeVideoFragment extends Fragment {

    View view;
    TextView textView;

    public YoutubeVideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_youtube_video, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        view = getView();
        textView = view.findViewById(R.id.property_text_view);

        TextView t2 = (TextView) view.findViewById(R.id.link_text_view);
        t2.setMovementMethod(LinkMovementMethod.getInstance());

        String property = " All videos are property of: <b>Onroad Driving School Sydney</b>\n Feel free to check their Youtube Channel:";
        textView.setText(Html.fromHtml(property));


    }
}