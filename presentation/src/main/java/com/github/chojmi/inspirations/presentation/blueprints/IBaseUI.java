package com.github.chojmi.inspirations.presentation.blueprints;

import com.github.chojmi.inspirations.presentation.ApplicationComponent;
import com.github.chojmi.inspirations.presentation.navigation.Navigator;

interface IBaseUI {
    Navigator getNavigator();

    ApplicationComponent getApplicationComponent();
}
