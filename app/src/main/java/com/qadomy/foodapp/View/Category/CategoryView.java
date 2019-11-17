package com.qadomy.foodapp.View.Category;

import com.qadomy.foodapp.Model.Meals;

import java.util.List;

public interface CategoryView {
    void showLoading();

    void hideLoading();

    void setMeals(List<Meals.Meal> meals);

    void onErrorLoading(String message);
}
