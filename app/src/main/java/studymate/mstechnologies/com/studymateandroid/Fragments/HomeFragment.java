package studymate.mstechnologies.com.studymateandroid.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.valdesekamdem.library.mdtoast.MDToast;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import studymate.mstechnologies.com.studymateandroid.Activities.HomeActivity;
import studymate.mstechnologies.com.studymateandroid.Activities.MainActivity;
import studymate.mstechnologies.com.studymateandroid.Activities.askQuestionActivity;
import studymate.mstechnologies.com.studymateandroid.Adapters.QuestionsAdapter;
import studymate.mstechnologies.com.studymateandroid.Models.MainQuestions;
import studymate.mstechnologies.com.studymateandroid.Models.Questions;
import studymate.mstechnologies.com.studymateandroid.R;
import studymate.mstechnologies.com.studymateandroid.Recycler_Tools.DividerItemDecoration;
import studymate.mstechnologies.com.studymateandroid.Retrofit.APIClientRetrofit;
import studymate.mstechnologies.com.studymateandroid.Retrofit.APIinterfaceRetrofit;
import studymate.mstechnologies.com.studymateandroid.Retrofit.Requests;
import studymate.mstechnologies.com.studymateandroid.Utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

  private static View view;
  RecyclerView recyclerView;
  FloatingActionButton addQ;
  ProgressBar progressBar;
  Context ctx;
  ArrayList<MainQuestions> questionsList = new ArrayList<>();
  public HomeFragment() {

    ctx = getContext();
    // Required empty public constructor
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {

    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_home, container, false);
    progressBar=view.findViewById(R.id.home_spin_kit_pb);
    getQuestions();
    WidgetsInit(view);
    ClickListener();

    return view;

  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    recyclerView = (RecyclerView) view.findViewById(R.id.rv_questions_home);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(layoutManager);
    Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.bg_gradient_two);
    RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
    recyclerView.addItemDecoration(dividerItemDecoration);


  }

  private void getQuestions()
  {
    FoldingCube fc = new FoldingCube();
    progressBar.setIndeterminateDrawable(fc);
    progressBar.setVisibility(View.VISIBLE);
    SharedPreferences prefs = getActivity().getSharedPreferences(Utils.Login_Preferences, Context.MODE_PRIVATE);
    int Id = prefs.getInt("Id", -1);
    Retrofit retrofit = APIClientRetrofit.getClient();
    APIinterfaceRetrofit getQues = retrofit.create(APIinterfaceRetrofit.class);
    Call<ArrayList<MainQuestions>> getQuesCall = getQues.getQuestions(Id);
    getQuesCall.enqueue(new Callback<ArrayList<MainQuestions>>() {
      @Override public void onResponse(Call<ArrayList<MainQuestions>> call,
          Response<ArrayList<MainQuestions>> response) {

        if (response.code()==200)
        {
          questionsList = response.body();
          // questionsList.add(response.body().get(1));       // MDToast.makeText(getContext(),mq.getQuestion().getUserId()+"",MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS).show();
          QuestionsAdapter adapter = new QuestionsAdapter(questionsList , getContext());
          recyclerView.setAdapter(adapter);
          progressBar.setVisibility(View.INVISIBLE);


        }
        else {
          MDToast.makeText(getContext(),getContext().getResources().getString(R.string.SomethingWentWrong),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
          progressBar.setVisibility(View.INVISIBLE);

        }
      }

      @Override public void onFailure(Call<ArrayList<MainQuestions>> call, Throwable t) {
        MDToast.makeText(getContext(),getContext().getResources().getString(R.string.NetworkConnectionFailure),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
        progressBar.setVisibility(View.INVISIBLE);
      }
    });
  }

  private void ClickListener()
  {
    addQ.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent intent = new Intent(getActivity(), askQuestionActivity.class);
        startActivity(intent);
          }
    });

  }

  private void WidgetsInit(View view)
  {

    addQ=(FloatingActionButton)view.findViewById(R.id.addQbtn);
    recyclerView = (RecyclerView) view.findViewById(R.id.rv_questions_home);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(layoutManager);
    Log.d("QUESTIONS_SIZE",questionsList.size()+"");

  }
}
