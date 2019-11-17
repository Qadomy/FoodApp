package com.qadomy.foodapp.View.Category;

import androidx.annotation.NonNull;

import com.qadomy.foodapp.Model.Meals;
import com.qadomy.foodapp.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryPresenter {
    private CategoryView view;

    public CategoryPresenter(CategoryView view) {
        this.view = view;
    }

    void getMealByCategory(String category) {

        // Make request meals by category
        view.showLoading();
        Call<Meals> mealsCall = Utils.getApi().getMealsByCategory(category);
        mealsCall.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(@NonNull Call<Meals> call, @NonNull Response<Meals> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    view.setMeals(response.body().getMeals());
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
