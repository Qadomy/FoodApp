package com.qadomy.foodapp.View.Category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qadomy.foodapp.Adapter.RecyclerViewMealByCategory;
import com.qadomy.foodapp.Model.Meals;
import com.qadomy.foodapp.R;
import com.qadomy.foodapp.Utils;
import com.qadomy.foodapp.View.Details.DetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.qadomy.foodapp.View.Home.HomeActivity.EXTRA_DETAIL;

public class CategoryFragment extends Fragment implements CategoryView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.imageCategory)
    ImageView imageCategory;
    @BindView(R.id.imageCategoryBg)
    ImageView imageCategoryBg;
    @BindView(R.id.textCategory)
    TextView textCategory;

    AlertDialog.Builder descDialog;
    private Toast previousToast;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get Arguments with KEY
        if (getArguments() != null) {
            textCategory.setText(getArguments().getString("EXTRA_DATA_DESC"));
            Picasso.get().load(getArguments().getString("EXTRA_DATA_IMAGE")).into(imageCategory);
            Picasso.get().load(getArguments().getString("EXTRA_DATA_IMAGE")).into(imageCategoryBg);

            Context context;
            descDialog = new AlertDialog.Builder(getActivity())
                    .setTitle(getArguments().getString("EXTRA_DATA_NAME"))
                    .setMessage(getArguments().getString("EXTRA_DATA_DESC"));

            // declare presenter
            CategoryPresenter presenter = new CategoryPresenter(this);
            presenter.getMealByCategory(getArguments().getString("EXTRA_DATA_NAME"));

        }

    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setMeals(List<Meals.Meal> meals) {
        // set RecyclerViewMealByCategory adapter
        RecyclerViewMealByCategory adapter = new RecyclerViewMealByCategory(getActivity(), meals);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setClipToPadding(false);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener((view, position) -> {
            //showMessage("meal: " + meals.get(position).getStrMeal());

            TextView mealName = view.findViewById(R.id.mealName);
            // make intent of meal name from edit text view and send it to Details Activity
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra(EXTRA_DETAIL, mealName.getText().toString());
            startActivity(intent);

        });
    }

    @Override
    public void onErrorLoading(String message) {
        Utils.showDialogMessage(getActivity(), "Error ", message);
    }

    @OnClick(R.id.cardCategory)
    public void onClick() {
        descDialog.setPositiveButton("Close", (dialogInterface, i) -> dialogInterface.dismiss());
        descDialog.show();
    }

    // method for display Toast message
    public void showMessage(String msg) {
        Toast toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        toast.show();
        //Hide the previous toast
        if (previousToast != null)
            previousToast.cancel();
        previousToast = toast;
    }
}
