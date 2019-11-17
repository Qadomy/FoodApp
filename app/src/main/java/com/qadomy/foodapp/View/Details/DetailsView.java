package com.qadomy.foodapp.View.Details;

import com.qadomy.foodapp.Model.Meals;

public interface DetailsView {

    void showLoading();

    void hideLoading();

    void setMeal(Meals.Meal meal);

    void onErrorLoading(String message);
}
