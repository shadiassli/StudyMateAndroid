package studymate.mstechnologies.com.studymateandroid.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.valdesekamdem.library.mdtoast.MDToast;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import studymate.mstechnologies.com.studymateandroid.Adapters.QuestionsAdapter;
import studymate.mstechnologies.com.studymateandroid.Models.MainQuestions;
import studymate.mstechnologies.com.studymateandroid.R;
import studymate.mstechnologies.com.studymateandroid.Recycler_Tools.DividerItemDecoration;
import studymate.mstechnologies.com.studymateandroid.Retrofit.APIClientRetrofit;
import studymate.mstechnologies.com.studymateandroid.Retrofit.APIinterfaceRetrofit;
import studymate.mstechnologies.com.studymateandroid.Utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

  private static View view;
  EditText searchET;
  RecyclerView recyclerView;
  ImageView searchbtn;
  ProgressBar progressBar;
  Context ctx;
  ArrayList<MainQuestions> questionsList = new ArrayList<>();
  ArrayList<MainQuestions> filteredList=new ArrayList<>(); ;
  public SearchFragment() {
    ctx=getContext();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view= inflater.inflate(R.layout.fragment_search, container, false);
    progressBar=(ProgressBar)view.findViewById(R.id.search_spin_kit_pb);
    getQuestions();
    WidgetsInit(view);
    ClickListeners();
    return view;
  }

  private void ClickListeners()
  {

    FoldingCube fc = new FoldingCube();
    progressBar.setIndeterminateDrawable(fc);
    progressBar.setVisibility(View.VISIBLE);
    searchbtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        String query = searchET.getText().toString();
        for (MainQuestions m: questionsList
        ) {
          if(m.getQuestion().getText().contains(query))
          {
            filteredList.add(m);
          }

        }
        QuestionsAdapter adapter = new QuestionsAdapter(filteredList , getContext());
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.INVISIBLE);
        //ClearList(filteredList);
      }
    });
  }

  private void ClearList(ArrayList<MainQuestions> filteredList)
  {

      for (MainQuestions m: filteredList
          ) {
        filteredList.remove(m);
        QuestionsAdapter adapter = new QuestionsAdapter(filteredList , getContext());
        recyclerView.setAdapter(adapter);
        //notifyAll();
      }

  }

  private void WidgetsInit(View view)
  {
    searchbtn=(ImageView)view.findViewById(R.id.search_srch_btn);
    recyclerView=(RecyclerView)view.findViewById(R.id.search_recycler);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(layoutManager);
    searchET = (EditText)view.findViewById(R.id.seach_et);


  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

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
          /*QuestionsAdapter adapter = new QuestionsAdapter(questionsList , getContext());
          recyclerView.setAdapter(adapter);*/
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
}
