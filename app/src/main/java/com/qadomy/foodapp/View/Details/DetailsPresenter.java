package com.qadomy.foodapp.View.Details;

import androidx.annotation.NonNull;

import com.qadomy.foodapp.Model.Meals;
import com.qadomy.foodapp.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsPresenter {

    private DetailsView view;

    // constructor
    public DetailsPresenter(DetailsView view) {
        this.view = view;
    }

    void getMealById(String mealName) {
        view.showLoading();
        Utils.getApi().getMealByName(mealName).enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(@NonNull Call<Meals> call, @NonNull Response<Meals> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    view.setMeal(response.body().getMeals().get(0));
                } else {
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Meals> call, @NonNull Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

}
