package studymate.mstechnologies.com.studymateandroid.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import studymate.mstechnologies.com.studymateandroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

  private static View view;
  XRecyclerView recyclerView;

  public HomeFragment() {
    // Required empty public constructor
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {

    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_home, container, false);
    WidgetsInit(view);
    return view;

  }

  private void WidgetsInit(View view)
  {
    recyclerView = (XRecyclerView)view.findViewById(R.id.home_Recycler);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(layoutManager);
    //recyclerView.setAdapter(mAdapter);
  }
}
