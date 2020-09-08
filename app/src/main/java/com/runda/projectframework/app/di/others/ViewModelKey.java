package com.runda.projectframework.app.di.others;


import androidx.lifecycle.ViewModel;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import dagger.MapKey;

/**
 * Created by Kongdq
 * @date 2019/10/31
 * Description: viewModel的注入scope
 */

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@MapKey
public @interface ViewModelKey {
    Class<? extends ViewModel> value();
}
