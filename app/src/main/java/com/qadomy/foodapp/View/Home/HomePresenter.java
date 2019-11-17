package com.qadomy.foodapp.View.Home;

import androidx.annotation.NonNull;

import com.qadomy.foodapp.Model.Categories;
import com.qadomy.foodapp.Model.Meals;
import com.qadomy.foodapp.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class HomePresenter {

    // interface HomeView
    private HomeView view;

    // constructor
    public HomePresenter(HomeView view) {
        this.view = view;
    }

    void getMeals() {

        view.showLoading();

        Call<Meals> mealsCall = Utils.getApi().getMeal();

        mealsCall.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(@NonNull Call<Meals> call, @NonNull Response<Meals> response) {
                // close loading when received a response from server
                view.hideLoading();

                if (response.isSuccessful() && response.body() != null) {
                    view.setMeal(response.body().getMeals());

                } else {
                    //show error message
                    view.onErrorLodaing(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Meals> call, @NonNull Throwable t) {

                // close loading
                view.hideLoading();

                // show error message
                view.onErrorLodaing(t.getLocalizedMessage());
            }
        });
    }


    void getCategories() {

        view.showLoading();

        Call<Categories> categoriesCall = Utils.getApi().getCategories();

        categoriesCall.enqueue(new Callback<Categories>() {


            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {

                view.hideLoading();

                if (response.isSuccessful() && response.body() != null) {
                    view.setCategory(response.body().getCategories());

                } else {
                    //show error message
                    view.onErrorLodaing(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Categories> call, @NonNull Throwable t) {
                // close loading
                view.hideLoading();

                // display error message
                view.onErrorLodaing(t.getLocalizedMessage());

            }
        });
    }
}
