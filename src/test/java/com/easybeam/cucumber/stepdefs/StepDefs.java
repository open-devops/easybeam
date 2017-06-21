package com.easybeam.cucumber.stepdefs;

import com.easybeam.EasyBeamApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = EasyBeamApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
